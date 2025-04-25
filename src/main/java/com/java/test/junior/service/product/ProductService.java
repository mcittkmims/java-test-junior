/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service.product;

import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;
import com.java.test.junior.model.user.UserPrincipal;

import java.util.List;


public interface ProductService {
    Product getProduct(Long prodId);

    Product addProduct(ProductDTO productDTO, UserPrincipal userPrincipal);

    Product updateProduct(Long prodId, ProductDTO product, UserPrincipal userPrincipal);

    void deleteProduct(Long prodId, UserPrincipal userPrincipal);

    List<Product> getLimitedProducts(int page, int pageSize);

    List<Product> searchProductsByName(String name);

    void likeProduct(Long prodId, UserPrincipal userPrincipal);

    void dislikeProduct(Long prodId, UserPrincipal userPrincipal);
}