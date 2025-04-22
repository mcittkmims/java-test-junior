/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service;

import com.java.test.junior.exception.ProductNotFoundException;
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
    public Product getProduct(Long prodId){
        Product product = productMapper.findById(prodId);
        if (product == null){
            throw new ProductNotFoundException("Product with id " + prodId + " not found");
        }
        return product;
    }
    @Override
    public Product addProduct(ProductDTO productDTO){
        return productMapper.insertProduct(productDTO, 2321L);
    }

    @Override
    public Product updateProduct(Long prodId, ProductDTO productDTO) {
        Product product = productMapper.updateProduct(productDTO, prodId);
        if (product == null){
            throw new ProductNotFoundException("Product with id " + prodId + " not found");
        }
        return product;
    }

    @Override
    public void deleteProduct(Long prodId){
        productMapper.deleteProduct(prodId);
    }

    @Override
    public List<Product> getLimitedProducts(int page, int pageSize){
        return productMapper.getLimitedProducts(pageSize, page*pageSize - pageSize);
    }

}
