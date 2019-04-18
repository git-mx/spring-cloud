package com.shyfay.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.shyfay.apigateway.constant.CookieConstant;
import com.shyfay.apigateway.utils.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 卖家鉴权
 *
 * @author mx
 * @since 2019/4/17
 */
@Component
public class SellerAuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 2;
    }

    //可以将需要鉴权的路径放到config配置中心区，实现动态鉴权
    @Override
    public boolean shouldFilter() {
        return "/order/order/finish".equals(RequestContext.getCurrentContext().getRequest().getRequestURI());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        Cookie cookie = CookieUtil.get(ctx.getRequest(), CookieConstant.TOKEN);
        if(null == cookie
            || StringUtils.isBlank(cookie.getValue())
            || StringUtils.isBlank(String.format(CookieConstant.TOKEN, cookie.getValue()))
        ){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
