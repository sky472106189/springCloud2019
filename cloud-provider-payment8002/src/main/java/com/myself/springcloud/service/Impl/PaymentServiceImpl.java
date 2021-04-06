package com.myself.springcloud.service.Impl;

import com.myself.springcloud.dao.PaymentDao;
import com.myself.springcloud.entities.Payment;
import com.myself.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao PaymentDao;

    @Override
    public int create(Payment payment) {
        return PaymentDao.create(payment);
    }

    @Override
    public Payment getPaymentId(Long id) {
        return PaymentDao.getPaymentId(id);
    }
}
