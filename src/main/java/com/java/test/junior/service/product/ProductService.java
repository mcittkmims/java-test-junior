/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service.product;

import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;

import java.util.List;


public interface ProductService {
    Product getProduct(Long prodId);
    Product addProduct(ProductDTO productDTO);
    Product updateProduct(Long prodId, ProductDTO product);
    void deleteProduct(Long prodId);
    List<Product> getLimitedProducts(int page, int pageSize);

}