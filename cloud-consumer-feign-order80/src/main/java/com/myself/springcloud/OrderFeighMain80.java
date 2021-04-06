package com.myself.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderFeighMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeighMain80.class,args);
    }
}
