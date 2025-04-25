/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.mapper.product;

import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ProductMapper {
    Product findById(Long id);

    Product insertProduct(Product product);

    Product updateProduct(Product product);

    Product deleteProduct(Long id);

    List<Product> getLimitedProducts(@Param("limit") int limit, @Param("offset") int offset);

    List<Product> searchProductsByName(String name);
}