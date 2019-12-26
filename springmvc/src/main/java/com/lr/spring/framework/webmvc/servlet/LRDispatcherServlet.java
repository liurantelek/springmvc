package com.lr.spring.framework.webmvc.servlet;/**
 * @Auther: 45417
 * @Date: 2019/12/20 09:12
 * @Description:
 */

import com.lr.spring.framework.context.LRApplicationContext;
import com.lr.spring.framework.webmvc.*;
import com.lr.spring.framework.webmvc.annotation.LRController;
import com.lr.spring.framework.webmvc.annotation.LRRequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ProjectName: springmvc
 * @ClassName: LRDispatcherServlet
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 45417
 * @Date: 2019/12/20 9:12
 * @version v1.0
 *
 */
public class LRDispatcherServlet extends HttpServlet {

    private final String LOCATION = "contextConfigLocation";
    //他直接干掉了Struts、WebWork等mvc框架
    private List<LRHandlerMapping> handlerMappings = new ArrayList<> ();

    private Map<LRHandlerMapping,LRHandlerAdapter> handlerAdapters = new HashMap<> ();

    private List<LRViewResolver> viewResolvers = new ArrayList<> ();

    private LRApplicationContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = new LRApplicationContext (config.getInitParameter (LOCATION));
        initStrategies(context);
    }

    private void initStrategies(LRApplicationContext context) {
        //有9中策略，
        //针对每种用户请求，都会经过一些处理策略处理，最终才会有结果输出
        //每种策略都可以自定义干预，但最终的结果都一致
        
        //解析文件上传解析，如果请求类型是mulpart,将通过MultipartResolver进行文件上传解析
        initMultipartResolver(context);
        
        initLocaleResolver(context);//本地化解析
        
        initThemeResolver(context);//主题解析
        //用来保存controller中配置的requestMapping和method的对应关系
        //通过handlerMappings将请求映射到处理器
        initHandlerMappings(context);
       
        //通过handlerAdapter进行多类型的参数动态匹配
        initHandlerAdapters(context);
        //如果执行过程中出现异常，将交给HandlerExceptionResolver来解析
        initHandlerExceptionResolver(context);

        //直接将请求解析到视图名
        initRequestToViewNameTranslator(context);
    //实现动态模板的解析,通过ViewResolver将逻辑视图解析到集体视图实现
        initViewResolvers(context);
        
        //Flash映射管理器
        initFlashMapManager(context);
    }

    private void initHandlerMappings(LRApplicationContext context) {
        //按照我们通常的理解应该是一个map
        String [] beanNames = context.getBeanDefinitionNames ();
        try {
            for(String beanName:beanNames){
                //到了mvc层，对外提供的方法只有一个getbean()方法
                Object controller = context.getBean (beanName);
                Class<?> clazz = controller.getClass ();
                if(!clazz.isAnnotationPresent (LRController.class)){
                    continue;
                }
                String baseUrl = "";
                if(clazz.isAnnotationPresent (LRRequestMapping.class)){
                    LRRequestMapping requestMapping = clazz.getAnnotation (LRRequestMapping.class);
                    baseUrl = requestMapping.value ();
                }

                //扫描所有的public类型的方法
                Method [] methods = clazz.getMethods ();
                for(Method method:methods){
                    if(!method.isAnnotationPresent (LRRequestMapping.class)){
                        continue;
                    }
                    LRRequestMapping requestMapping = method.getAnnotation (LRRequestMapping.class);
                    String regex = ("/"+baseUrl+requestMapping.value ().replaceAll ("\\*",".*")).replaceAll ("/+","/");
                    Pattern pattern = Pattern.compile (regex);
                    this.handlerMappings.add (new LRHandlerMapping (pattern,controller,method));
                }

            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void initHandlerAdapters(LRApplicationContext context) {
        //在初始化阶段，我们能做的时，将这些参数的名字或者类型按照一定的顺序保存下来
        //因为后边反射调用的时候，传的形参就是换一个数组
        //可以通过记录这些参数的位置index，逐个从数组中取值，这样就和参数的顺序无关了
        for(LRHandlerMapping handlerMapping:this.handlerMappings){
            //每个方法里有一个参数列表，这里保存的是形参列表
            this.handlerAdapters.put(handlerMapping,new LRHandlerAdapter());
        }
    }

    private void initHandlerExceptionResolver(LRApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(LRApplicationContext context) {
    }

    private void initThemeResolver(LRApplicationContext context) {
    }

    private void initLocaleResolver(LRApplicationContext context) {
    }

    private void initViewResolvers(LRApplicationContext context) {
        //在页面输入http://localhost:80/first.html
        //解决页面名字和末班文件关联的问题
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        for(File template:templateRootDir.listFiles()){
            this.viewResolvers.add(new LRViewResolver(templateRoot));
        }
    }

    private void initMultipartResolver(LRApplicationContext context) {
    }

    private void initFlashMapManager(LRApplicationContext context) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("<font size='25' color='blue'>500 Exception</font> <br/> Details:" +
                    "<br/>"+ Arrays.toString(e.getStackTrace()).replaceAll("\\[\\]","").replaceAll("\\s","\r\n")
            +"<font color='green'><i>Copyright@LiuRanEDU</i></font>");
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //根据用户的请求URL来获得一个Handler
        LRHandlerMapping handler = getHandler(request);
        if(handler == null){
            processDispathcResult(request,response,new LRModelAndView("404"));
            return;
        }
        LRHandlerAdapter ha = getHandlerAdapter(handler);
        LRModelAndView mv = ha.handle(request,response,handler);
    }

    private LRHandlerAdapter getHandlerAdapter(LRHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){
            return null;
        }
        LRHandlerAdapter ha = this.handlerAdapters.get(handler);
        if(ha.supports(handler)){
            return ha;
        }
        return null;
    }

    private void processDispathcResult(HttpServletRequest request, HttpServletResponse response, LRModelAndView lrModelAndView) throws Exception {
        //调用ViewAndResolver的resolverViewName()方法
        if(null == lrModelAndView){
            return;
        }
        if(this.viewResolvers.isEmpty()){
            return;
        }
        if(this.viewResolvers != null){
            for(LRViewResolver viewResolver:this.viewResolvers){
                LRView view = viewResolver.resolveViewName(lrModelAndView.getViewName(),null);
                if(view != null){
                    view.render(lrModelAndView.getModel(),request,response);
                    return;
                }
            }
        }
    }


    private LRHandlerMapping getHandler(HttpServletRequest request) {
        if(this.handlerMappings.isEmpty()){
            return null;
        }
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");
        for(LRHandlerMapping mapping:this.handlerMappings){
            Matcher matcher = mapping.getPattern().matcher(url);
            if(!matcher.matches()){
                continue;
            }
            return mapping;

        }
        return null;
    }
}
