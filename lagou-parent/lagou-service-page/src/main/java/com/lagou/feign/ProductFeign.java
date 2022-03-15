package com.lagou.feign;

import com.lagou.entity.Products;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * feign远程调用组件
 * 类似于dubbo，服务消费者拿到服服务提供者的接口，本地调用,实际上发出的是
 * 远程调用，此时就可以不使用restTemplate
 * feign支持spring mvc注解
 */
            //要调用的服务名                    回滚类
@FeignClient(name = "lagou-service-product",fallback = ProductFeignFallBack.class)
public interface ProductFeign {
    //方法名、返回值、参数必须与服务提供者相同 url要添加上一级路径(完整的路径)
    @GetMapping("/product/findById/{id}")
    public Products findById(@PathVariable("id") Integer id);
    @RequestMapping("/server/getPort")
    public String getServerPort();
}
