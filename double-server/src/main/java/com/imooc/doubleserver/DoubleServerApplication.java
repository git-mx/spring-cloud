package com.imooc.doubleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DoubleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoubleServerApplication.class, args);
    }

}
