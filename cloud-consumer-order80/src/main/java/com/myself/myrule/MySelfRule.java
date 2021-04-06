package com.myself.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* Ribbon官方文档给出警告：
*   自定义配置了不能放在@ComponentScan所扫描的当下包下以及子包下。
* 否则我们自定义的这个配置类就会被所有Ribbon客户端所共享，达不到特殊化定制的目的了。
* */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        /*
        * Ribbon自带的特定算法
        * RoundRobinRule  轮询
        * RandomRule  随机
        * RetryRule  先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试
        * WeightedResponseTimeRule  对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择
        * BestAvailableRule  会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
        * AvailabilityFilteringRule  先过滤掉故障实例，再选择并发较小的实例
        * ZoneAvoidanceRule  默认规则，复合判断server所在区域的性能和server的可用性选择服务器
         * */
        return new RandomRule();//定义为随机
    }
}
