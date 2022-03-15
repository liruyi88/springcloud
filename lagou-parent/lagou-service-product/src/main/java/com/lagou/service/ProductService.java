package com.lagou.service;

import com.lagou.entity.Products;

public interface ProductService {
    /**
     * 通过商品id查询商品信息
     */
    Products findById(Integer id);
}
