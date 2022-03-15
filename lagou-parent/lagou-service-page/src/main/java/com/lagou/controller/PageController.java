package com.lagou.controller;

import com.lagou.entity.Products;
import com.lagou.feign.ProductFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/page")
public class PageController {
    /*@Autowired
    private RestTemplate restTemplate;*/
    /*@Autowired
    private DiscoveryClient discoveryClient;  //要引入soring cloud中客户端
    @GetMapping("/getData/{id}")
    public Products getProduct(@PathVariable("id") Integer id){
        Products products = restTemplate.getForObject("http://lagou-service-product/product/findById/" + id, Products.class);
        return products;
    }
    @GetMapping("/getData2/{id}")
    public Products getProduct2(@PathVariable("id") Integer id){
        //获取服务注册中心的服务提供者列表
        List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-product");
        //获取第一个即可
        ServiceInstance serviceInstance = instances.get(0);
        //获取服务提供者的ip、端口号
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        //通过拼接的方式获得服务提供者的url
        String url = "http://" + host + ":" + port + "/" + "product/findById/" + id;
        //发起请求
        return restTemplate.getForObject(url, Products.class);
    }
    //获取端口号
    @RequestMapping("/getPort")
    public String getPort(){
        //通过服务提供者在注册中心的别名发起请求
        String url = "http://lagou-service-product/server/getPort";
        String port = restTemplate.getForObject(url, String.class);
        return port;
    }
    //获取端口号
    @HystrixCommand(
            threadPoolKey = "getPort2",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value = "1"),
                    @HystrixProperty(name="maxQueueSize",value = "20"),
            },
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")  // 超过两秒触发熔断
            }
    )
    @RequestMapping("/getPort2")
    public String getPort2(){
        String url = "http://lagou-service-product/server/getPort";
        String port = restTemplate.getForObject(url, String.class);
        return port;
    }

    *//**
     * 8秒内,最小请求2个，错误数量阈值50%,自我恢复时间窗口3秒
     * 出现调用错误，会有一个8秒的时间窗口，在这8秒的时间窗口内，会统计错误数量是否达到最小请求数的百分比阈值，
     * 如果达到了跳闸，跳闸后有一个3秒的自我修复活动窗口，在这个窗口内，会判断服务是否可用，可用则重置熔断器，不可用仍然处于跳闸状态
     * @return
     *//*
    @HystrixCommand(
            threadPoolKey = "getPort3",  //唯一,不唯一就会共用
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "2"),  //守护两个线程
                    @HystrixProperty(name = "maxQueueSize",value = "20")//队列最大等待数
            },
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),  //超过2秒后触发熔断
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "8000"),//统计时间窗口定义,8秒
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "2"),//统计时间窗口最小的请求数
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),//统计时间窗口内的错误数量百分比阈值
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "3000")//自我修复时的活动窗口长度
            },
            fallbackMethod = "myFallBack"  //回退方法
    )
    @RequestMapping("/getPort3")
    public String getPort3(){
        String url = "http://lagou-service-product/server/getPort";
        String port = restTemplate.getForObject(url, String.class);
        return port;
    }
    *//**
     * 定义一个回退方法(也就是返回回退数据)
     * 该方法形参和返回值都要与原始方法一致
     * @return
     *//*
    public String myFallBack(){
        return "-1";    //熔断触发返回这个数据
    }*/
    /**
     * feign远程调用组件
     */
    @Autowired
    private ProductFeign productFeign;
    @RequestMapping("/getData/{id}")
    public Products getData(@PathVariable("id") Integer id){
        //调用接口中的方法实际上是发出的远程调用
        return productFeign.findById(id);
    }
    /**
     * 获取端口号
     */
    @RequestMapping("/getPort")
    public String getPort(){
        return productFeign.getServerPort();
    }
}
