/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service;

import com.java.test.junior.model.Product;
import com.java.test.junior.model.ProductDTO;

import java.util.ArrayList;
import java.util.List;


public interface ProductService {
    Product getProduct(Long prodId);
    Product addProduct(ProductDTO productDTO);
    Product updateProduct(Long prodId, ProductDTO product);
    void deleteProduct(Long prodId);
    List<Product> getLimitedProducts(int page, int pageSize);

}