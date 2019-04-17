//package com.shyfay.apigateway.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import com.shyfay.apigateway.constant.CookieConstant;
//import com.shyfay.apigateway.constant.RedisConstant;
//import com.shyfay.apigateway.utils.CookieUtil;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
//
///**
// * zuul鉴权，所谓鉴权就是让order服务的创建订单接口只能让买家访问
// * 让order服务的完结订单只能让卖家访问
// * 让product服务的list接口买家和卖家都可以访问
// * 将这个filter拆解称为BuyerAuthFilter和SellerAuthFilter
// * @author mx
// * @since 2019/4/17
// */
//@Component
//public class AuthFilter extends ZuulFilter {
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public String filterType() {
//        return PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER - 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        if("/order/order/create".equals(request.getRequestURI())){
//            Cookie cookie = CookieUtil.get(request, CookieConstant.OPENID);
//            if(null ==cookie || StringUtils.isBlank(cookie.getValue())){
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//
//        if("/order/order/finish".equals(request.getRequestURI())){
//            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
//            if(null == cookie
//                || StringUtils.isBlank(cookie.getValue())
//                || StringUtils.isBlank(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))
//            ){
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//        return null;
//    }
//}
