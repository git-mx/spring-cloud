package com.imooc.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ImoocProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocProductApplication.class, args);
    }

}
