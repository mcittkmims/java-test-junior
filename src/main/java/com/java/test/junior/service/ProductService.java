/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service;

import com.java.test.junior.model.Product;
import com.java.test.junior.model.ProductDTO;

import java.util.ArrayList;
import java.util.List;


public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    List<Product> getProducts();
    Product getProduct(Long prodId);
    void addProduct(Product product);
    void updateProduct(Long prodId, Product product);
    void deleteProduct(Long prodId);

}