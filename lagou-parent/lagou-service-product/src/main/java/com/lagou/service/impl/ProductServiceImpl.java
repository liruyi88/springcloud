package com.lagou.service.impl;

import com.lagou.entity.Products;
import com.lagou.mapper.ProductMapper;
import com.lagou.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public Products findById(Integer id) {
        return productMapper.selectById(id);
    }
}
