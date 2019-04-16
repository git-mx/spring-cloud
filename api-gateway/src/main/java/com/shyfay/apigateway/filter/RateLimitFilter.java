package com.shyfay.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 限流，在zuul中，限流应该是放在所有过滤器之前，
 * 这里我们使用google.grava的令牌桶算法
 * 令牌桶算法的意思就是，定时往桶里面放令牌（比如一秒钟放5个，即一秒钟最多请求5次），
 * 让所有的请求都去从令牌桶里面取令牌，请求拿到令牌后再去执行接下来的操作，没有
 * 拿到令牌的请求，就只能终止，限流有很多好处，例如发送短信的接口，我们就必须要做限流，防止恶意请求无限次的发送短信
 * 从而避免损失
 * @author mx
 * @since 2019/4/16
 */
@Component
public class RateLimitFilter extends ZuulFilter {

    //每秒钟往桶里放100个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    //限流应该放在所有前置过滤器之前
    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if(!RATE_LIMITER.tryAcquire()){
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.setSendZuulResponse(false);
            //冲突
            ctx.setResponseStatusCode(HttpStatus.CONFLICT.value());
        }
        return null;
    }
}
