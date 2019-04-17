//package com.shyfay.apigateway.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;
//
//
///**
// * 定义zuul前置过滤器，我们可以让所有的微服务都走统一身份认证平台
// * 然后让所有的外部请求都走网关，然后在网关统一处理身份认证处理，这样就不用每个服务
// * 都去实现身份校验的逻辑了
// * @author mx
// * @since 2019/4/16
// */
//@Component
//public class TokenFilter extends ZuulFilter {
//
//    //filter类型，此处设置成前置过滤器
//    @Override
//    public String filterType() {
//        return PRE_TYPE;
//    }
//
//    //filter顺序，
//    @Override
//    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER - 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        //RequestContext ctx = RequestContext.getCurrentContext();
//        //return !ctx.containsKey(FORWARD_TO_KEY) && !ctx.containsKey(SERVICE_ID_KEY);
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        String token = request.getParameter("token");
//        if(StringUtils.isBlank(token)){
//            //ctx.setSendZuulResponse(false);
//            //404，在以后编程中我们都可以时候用HttpStatus这个类的这些常量
//            //ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//        }
//        return null;
//    }
//}
