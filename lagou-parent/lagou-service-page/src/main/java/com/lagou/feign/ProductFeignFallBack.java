package com.lagou.feign;

import com.lagou.entity.Products;
import org.springframework.stereotype.Component;

/**
 * 回滚类，实现feign接口，重写里面的方法 返回的是一个兜底数据(服务降级)
 */
@Component  //注入到ioc容器中
public class ProductFeignFallBack implements ProductFeign{
    @Override
    public Products findById(Integer id) {
        return null;
    }

    @Override
    public String getServerPort() {
        return "-1";
    }
}
