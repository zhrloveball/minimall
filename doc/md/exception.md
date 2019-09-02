##### 10.2.1 注册 HandlerExceptionResolver

```Java
public class WebMvcConfigurationSupport implements ApplicationContextAware, ServletContextAware{

    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        
        List<HandlerExceptionResolver> exceptionResolvers = new ArrayList();
        configureHandlerExceptionResolvers(exceptionResolvers);
        if (exceptionResolvers.isEmpty()) {
            // 添加三个默认HandlerExceptionResolver类
            // ExceptionHandlerExceptionResolver  
            // ResponseStatusExceptionResolver
            // DefaultHandlerExceptionResolver
            this.addDefaultHandlerExceptionResolvers(exceptionResolvers);
        }

        extendHandlerExceptionResolvers(exceptionResolvers);
        HandlerExceptionResolverComposite composite = new HandlerExceptionResolverComposite();
        composite.setOrder(0);
        composite.setExceptionResolvers(exceptionResolvers);
        return composite;
    }
    
    protected final void addDefaultHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    
        // ExceptionHandlerExceptionResolver  
        ExceptionHandlerExceptionResolver exceptionHandlerResolver = this.createExceptionHandlerExceptionResolver();
        exceptionHandlerResolver.setContentNegotiationManager(this.mvcContentNegotiationManager());
        exceptionHandlerResolver.setMessageConverters(this.getMessageConverters());
        exceptionHandlerResolver.setCustomArgumentResolvers(this.getArgumentResolvers());
        exceptionHandlerResolver.setCustomReturnValueHandlers(this.getReturnValueHandlers());
        if (jackson2Present) {
            exceptionHandlerResolver.setResponseBodyAdvice(Collections.singletonList(new JsonViewResponseBodyAdvice()));
        }

        if (this.applicationContext != null) {
            exceptionHandlerResolver.setApplicationContext(this.applicationContext);
        }
        // 设置自定义 ExceptionHandlerMethodResolver
        exceptionHandlerResolver.afterPropertiesSet();
        exceptionResolvers.add(exceptionHandlerResolver);
        // ResponseStatusExceptionResolver
        ResponseStatusExceptionResolver responseStatusResolver = new ResponseStatusExceptionResolver();
        responseStatusResolver.setMessageSource(this.applicationContext);
        exceptionResolvers.add(responseStatusResolver);
        // DefaultHandlerExceptionResolver
        exceptionResolvers.add(new DefaultHandlerExceptionResolver());
    }
}
```

``exceptionHandlerResolver.afterPropertiesSet()`` 会扫描使用了``@ExceptionHandler``注解的方法

```Java
public class ExceptionHandlerExceptionResolver extends AbstractHandlerMethodExceptionResolver implements ApplicationContextAware, InitializingBean {

    public void afterPropertiesSet() {
        this.initExceptionHandlerAdviceCache();
        //...
    }

    private void initExceptionHandlerAdviceCache() {
        if (this.getApplicationContext() != null) {
            // 找出所有@ControllerAdvice注解的类，并排序
            List<ControllerAdviceBean> adviceBeans = ControllerAdviceBean.findAnnotatedBeans(this.getApplicationContext());
            AnnotationAwareOrderComparator.sort(adviceBeans);
            
            for (ControllerAdviceBean adviceBean : adviceBeans) {
                Class<?> beanType = adviceBean.getBeanType();
                if (beanType == null) {
                    throw new IllegalStateException("Unresolvable type for ControllerAdviceBean: " + adviceBean);
                }
                ExceptionHandlerMethodResolver resolver = new ExceptionHandlerMethodResolver(beanType);
                //遍历这些类，找出有@ExceptionHandler注解标注的方法
                if (resolver.hasExceptionMappings()) {
                    this.exceptionHandlerAdviceCache.put(adviceBean, resolver);
                }
                if (ResponseBodyAdvice.class.isAssignableFrom(beanType)) {
                    this.responseBodyAdvice.add(adviceBean);
                }
            }
            // ...
           
        }
    }
}
```

![exceptionResolvers](/png/exceptionResolver.png)


##### 10.2.2 DispatcherServlet 初始化 handlerExceptionResolvers 对象

DispatcherServlet 是入口类，负责分发请求，初始化时调用 ``onRefresh()``，其中 ``initHandlerExceptionResolvers()`` 会初始化 ``handlerExceptionResolvers``

```Java
public class DispatcherServlet extends FrameworkServlet {

    protected void onRefresh(ApplicationContext context) {
        this.initStrategies(context);
    }

    protected void initStrategies(ApplicationContext context) {
        this.initMultipartResolver(context);
        this.initLocaleResolver(context);
        this.initThemeResolver(context);
        this.initHandlerMappings(context);
        this.initHandlerAdapters(context);
        // 初始化 handlerExceptionResolvers
        this.initHandlerExceptionResolvers(context);
        this.initRequestToViewNameTranslator(context);
        this.initViewResolvers(context);
        this.initFlashMapManager(context);
    }
    
    private void initHandlerExceptionResolvers(ApplicationContext context) {
        
        // private List<HandlerExceptionResolver> handlerExceptionResolvers;
        this.handlerExceptionResolvers = null;
        if (this.detectAllHandlerExceptionResolvers) {
            // 从Spring容器的上下文ApplicationContext中找出所有HandlerExceptionResolvers对象
            Map<String, HandlerExceptionResolver> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerExceptionResolver.class, true, false);
            if (!matchingBeans.isEmpty()) {
                this.handlerExceptionResolvers = new ArrayList(matchingBeans.values());
                // 对HandlerExceptionResolvers根据order值进行排序
                AnnotationAwareOrderComparator.sort(this.handlerExceptionResolvers);
            }
        }
        //...
    }
}
```

##### 10.2.3 DispatcherServlet 处理业务请求中的异常

```Java
public class DispatcherServlet extends FrameworkServlet {
    
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        // ...
        try {
            ModelAndView mv = null;
            Exception dispatchException = null;

            try {
                // ...
                mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
                // ...
            } catch (Exception ex) {
                dispatchException = ex;
            } catch (Throwable err) {
                dispatchException = new NestedServletException("Handler dispatch failed", err);
            }
            // 处理请求结果，结果可能是 ModelAndView 或 Exception，需要将 Exception 处理为 ModelAndView
            this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
        } catch (Exception e){ 
            // ...  
        } catch (Throwable throwable){
            // ...
        }
        // ...

    }
    
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, 
        @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv, @Nullable Exception exception) throws Exception {
    
        boolean errorView = false;
        if (exception != null) {
            if (exception instanceof ModelAndViewDefiningException) {
                this.logger.debug("ModelAndViewDefiningException encountered", exception);
                mv = ((ModelAndViewDefiningException)exception).getModelAndView();
            } else {
                Object handler = mappedHandler != null ? mappedHandler.getHandler() : null;
                // 如果返回值是异常，轮询handlerExceptionResolvers中的HandlerExceptionResolver，直到将Exception转化为ModelAndView
                mv = this.processHandlerException(request, response, handler, exception);
                errorView = mv != null;
            }
        }
        // ...
    }
}
```