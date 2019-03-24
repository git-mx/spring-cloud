package com.shyfay.twoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TwoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoServerApplication.class, args);
    }

}
