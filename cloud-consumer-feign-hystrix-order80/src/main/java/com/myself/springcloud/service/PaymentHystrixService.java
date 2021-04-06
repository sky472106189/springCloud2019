package com.myself.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFeignClientService.class)
// Feign也有默认超时时间，如果超过了Feign的默认超时时间，就会报如下错误。
// Feign的调用默认时长是1秒钟，如果1秒没连接上或者1秒没响应就会报错。
// 设置超时时间请看yml文件

/*
* 记录一下这个坑
* FeignClient是有默认超时时间的，Hystrix也有，都是默认1000ms
* 如果只设置了Hystrix的超时时间且超过了1秒，没有设置Feign的，且Payment模块没报错(假设Payment执行时间超过1秒)，那么必然会走到fallback = PaymentFeignClientService.class
* 如果只设置了Feign的超时时间且超过了1秒，没有设置Hystrix，且Payment模块没报错(假设Payment执行时间超过1秒)，那么必然会走Hystrix指定的错误。
* 同时，都设置了，且都能正常执行，如果发生Payment模块突然宕机，也会走fallback = PaymentFeignClientService.class 报错
* Hystrix的各项默认值可以参考HystrixCommandProperties.java
*
* 网上结论：
* 根据上面分析，Hystrix的熔断时间要大于Feign或Ribbon的connectTimeout+readTimeout，
* 由于ribbon的重试次数为RetryCount = (maxAutoRetries + 1) * (maxAutoRetriesNextServer + 1)，因此必须保证(maxAutoRetries + 1) * (maxAutoRetriesNextServer + 1)*（ConnectTimeout+ReadTimeout）< timeoutInMilliseconds，
* 因为如果小于超时时间, 那就熔断了, 没有机会重试了。
* 参考：https://blog.csdn.net/weixin_40857858/article/details/108327446
* */
public interface PaymentHystrixService {

    @GetMapping("/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id")Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut(@PathVariable("id")Integer id);
}
