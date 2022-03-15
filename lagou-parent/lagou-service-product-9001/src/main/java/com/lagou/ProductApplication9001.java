package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient   //注册到服务中心
@MapperScan("com.lagou.mapper")
public class ProductApplication9001 {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication9001.class,args);
    }
}
