package com.shyfay.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mx
 * @since 2019/3/23
 */
@RestController
@RequestMapping("rt")
@Slf4j
public class RestTemplateController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("get")
    public String getMsg(){
        //第一种方式，对方只有单台服务
        //RestTemplate restTemplate = new RestTemplate();
        //String response = restTemplate.getForObject("http://localhost:8081/server/msg", String.class);
        //第二种方式，对方服务启动了多个副本，使用集群的方式部署，这时就要要用到eureka了
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/server/msg";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        //第三种方式，使用@LoadBalanced注解RestTemplate Bean的方式
        //String response = restTemplate.getForObject("http://PRODUCT/server/msg", String.class);
        log.info("response:{}", response);
        return response;
        /**
         * 对于服务发现来说，有两种发现方式，一种是服务端发现，一种是客户端发现，服务端发现的意思就是：所有服务对于客户端来说是透明的，客户端完全
         * 不需要知道服务端有哪些服务，服务注册中心为所有的服务端都设置了一个代理，客户端就是通过这些代理来发现服务的
         * 客户端发现，就是客户端实现知道有哪些服务已经注册到了服务注册中心，然后在自己代码里选定好对应的服务进行调用
         * 典型的服务端发现是zookeeper注册中心，SpringCloud的Eureka属于客户端发现
         *
         */
    }
}
