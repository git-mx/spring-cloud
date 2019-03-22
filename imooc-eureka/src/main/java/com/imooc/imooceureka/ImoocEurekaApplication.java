package com.imooc.imooceureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ImoocEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocEurekaApplication.class, args);
    }

}
