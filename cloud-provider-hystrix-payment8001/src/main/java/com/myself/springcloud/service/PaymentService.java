package com.myself.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {

    /********************************************服务降级演示Start********************************************/

    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_OK,id="+id+"\tO(∩_∩)O哈哈~";
    }

/*    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
            //@HystrixProperty 超过3秒才走fallbackMethod,没超过就正常执行
    })*/
    public String paymentInfo_TimeOut(Integer id){
        int timeNumber = 1; // 模拟超时异常
//        int age = 10/0; // 模拟运行异常
        try { Thread.sleep(timeNumber*100); } catch (InterruptedException e) { e.printStackTrace(); }
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOut,id="+id+"\t┭┮﹏┭┮";
    }

    // 超时3秒后执行此方法
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "系统繁忙或者运行报错，请稍后再试,id="+id+"\t";
    }

    /********************************************服务降级演示END********************************************/

    /********************************************服务熔断演示START********************************************/
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallBack",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),// 时间窗口期（时间范围）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("*******id 不能为负");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t 调用成功，流水号"+serialNumber;
    }

    public String paymentCircuitBreaker_fallBack(@PathVariable("id") Integer id){
        return "id 不能为负,请稍后再试 id:"+id;
    }

    /********************************************服务熔断演示END********************************************/


}
