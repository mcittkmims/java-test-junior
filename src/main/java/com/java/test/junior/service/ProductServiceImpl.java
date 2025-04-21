/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service;

import com.java.test.junior.mapper.ProductMapper;
import com.java.test.junior.model.Product;
import com.java.test.junior.model.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor

public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;


    @Override
    public Product createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public List<Product> getProducts(){
        return productMapper.getAll();
    }

    @Override
    public Product getProduct(Long prodId){
        return productMapper.findById(prodId);
    }
    @Override
    public void addProduct(Product product){
        productMapper.insertProduct(product);
    }

    @Override
    public void updateProduct(Long prodId, Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void deleteProduct(Long prodId){
        productMapper.deleteProduct(prodId);
    }
}
