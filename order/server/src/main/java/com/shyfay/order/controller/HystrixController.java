package com.shyfay.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * hystrix测试，hystrix的作用：服务降级、依赖隔离、服务熔断、服务监控
 * @author mx
 * @since 2019/4/18
 */
@RestController
@RequestMapping("hystrix")
@DefaultProperties(defaultFallback = "defaultFallBack")
public class HystrixController {

    @RequestMapping(value="test1", method= RequestMethod.GET)
    //@HystrixCommand(fallbackMethod = "fallBack")
    //类上注解了@DefaultProperties就不用再写fallbackMethod了
    //@HystrixCommand
    //将product服务的/server/msg这个接口设置Thread.sleep(2000),如果不做以下设置的话会超时
    //default_executionTimeoutInMilliseconds 默认值是1000，即1秒超时。
    //这里可以根据不同的业务类型设置不同的超时时间。例如扣款、发送短信、等耗时的服务就将超时时间设置长一点
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
    })
    public String HystrixCommandTest(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:9001/server/msg", String.class);
    }

    /**
     * 服务熔断
     * hystrix熔断机制重断路器（circuitBreaker）有三种状态：1.Open 2.Close 3.Half Open
     * - 当断路器处于Open状态时，所有的请求过来之后都直接走降级方法不会去执行业务主方法，
     *                     意思就是直接去执行defaultFallBack，而不执行HystrixCircuitBreakerTest方法体的内容
     * - 当断路器处于Close状态时，所有的请求过来之后都会去执行业务主方法
     * - 当断路器处于Half Open状态时，允许定量的请求走业务主方法
     * 断路器会设置一个时间单位，就是sleepWindowInMilliseconds制定的值，以该时间单位进行循环去识别断路器处于那种状态，
     * 以requestVolumeThreshold指定的请求数作为统计基数，以errorThresholdPercentage制定的值为百分比，例如本地中的
     * 统计基数是10，百分比是60%。意思就是单位时间段内有10个请求过来，如果其中有7个或7个以上的请求都失败了，那么就将断路器
     * 设置成打开，过了sleepWindowInMilliseconds后，断路器将会自动设置成Half Open,当处于Half Open后接受定量的请求
     * 如果这些请求都成功或者成功的比例达到一定的值，就将断路器设置为Close状态。如果都失败或者失败比例达到一定的值就将断路器设置
     * 成Open状态。
     * 以本例为例，假设段时间内有10次请求过来，有7次请求都失败了，断路器处于Open状态熔断主业务，这时断路器启动一个休眠计时器，计时器的值是10000
     * 毫秒，在这10000毫秒内断路器都是处于Open状态（即这10000毫秒内所有的请求都走熔断方法），10000毫秒之后，断路器会被自动设置成Half Open
     * 这时，又来了一个请求，这时断路器允许请求走业务主方法，如果这个请求成功了，那么就将断路器设置成Close状态，如果这个请求失败了。那么在此将
     * 断路器设置成为Open状态，这时重新设置休眠计时器（休眠时间窗）
     *
     */
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled", value="true"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
    })
    @RequestMapping(value="test2", method=RequestMethod.GET)
    public String HystrixCircuitBreakerTest(@RequestParam("number")Integer number){
        if(number % 2 == 0){
            return "成功";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:9001/server/msg", String.class);
    }


    private String fallBack(){
        return "太挤了，请稍后再试...";
    }

    private String defaultFallBack(){
        return "糟糕，太拥挤了，请稍后再试...";
    }
}
