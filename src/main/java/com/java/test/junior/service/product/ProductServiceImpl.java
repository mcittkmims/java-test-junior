/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service.product;

import com.java.test.junior.exception.product.ProductCreationException;
import com.java.test.junior.exception.product.ProductDeletionException;
import com.java.test.junior.exception.product.ProductNotFoundException;
import com.java.test.junior.mapper.product.ProductMapper;
import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;
import com.java.test.junior.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        Product product = new Product(productDTO.getName(), productDTO.getPrice(), productDTO.getDescription(), SecurityUtils.getCurrentUserId());
        product = productMapper.insertProduct(product);
        if (product == null){
            throw new ProductCreationException("Adding product failed");
        }
        return product;
    }

    @Override
    public Product updateProduct(Long prodId, ProductDTO productDTO) {
        Product product = new Product(prodId, productDTO.getName(), productDTO.getPrice(), productDTO.getDescription(), SecurityUtils.getCurrentUserId());
        product = productMapper.updateProduct(product);
        if (product == null){
            throw new ProductNotFoundException("Product with id " + prodId + " not found");
        }
        return product;
    }

    @Override
    public void deleteProduct(Long prodId){
        Product product = productMapper.deleteProduct(prodId);
        if (product == null){
            throw new ProductDeletionException("No saved product with id " + prodId);
        }
    }

    @Override
    public List<Product> getLimitedProducts(int page, int pageSize){
        return productMapper.getLimitedProducts(pageSize, page*pageSize - pageSize);
    }

}
