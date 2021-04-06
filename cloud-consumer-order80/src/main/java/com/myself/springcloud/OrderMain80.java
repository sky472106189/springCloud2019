package com.myself.springcloud;

import com.myself.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name="CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
// @RibbonClient(configuration = x.class) 从配置文件中去替换掉默认的负载均衡算法，注意：如果是手写的负载均衡算法，如MyLB的话，那么这行可有可无
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}
