package com.myself.springcloud.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PaymentFeignClientService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务端80 接收到paymentInfo_OK的错 ";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "服务端80 接收到paymentInfo_TimeOut的错 ";
    }
}
