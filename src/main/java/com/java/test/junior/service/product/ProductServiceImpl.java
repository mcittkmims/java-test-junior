/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service.product;

import com.java.test.junior.exception.product.*;
import com.java.test.junior.mapper.product.ProductMapper;
import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;
import com.java.test.junior.model.user.UserPrincipal;
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
    public Product addProduct(ProductDTO productDTO, UserPrincipal userPrincipal){
        Product product = new Product(productDTO, userPrincipal.getUserId());
        product = productMapper.insertProduct(product);
        if (product == null){
            throw new ProductCreationException("Adding product failed");
        }
        return product;
    }

    @Override
    public Product updateProduct(Long prodId, ProductDTO productDTO, UserPrincipal userPrincipal) {
        Product product = new Product(prodId, productDTO, userPrincipal.getUserId());
        product = productMapper.updateProduct(product);
        if (product == null){
            throw new ProductNotFoundException("Product with id " + prodId + " not found");
        }
        return product;
    }

    @Override
    public void deleteProduct(Long prodId, UserPrincipal userPrincipal){
        Product product = productMapper.deleteProduct(prodId, userPrincipal.getUserId());
        if (product == null){
            throw new ProductDeletionException("No saved product with id " + prodId);
        }
    }

    @Override
    public List<Product> getLimitedProducts(int page, int pageSize){
        return productMapper.getLimitedProducts(pageSize, page*pageSize - pageSize);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productMapper.searchProductsByName(name);
    }

    @Override
    public void likeProduct(Long prodId, UserPrincipal userPrincipal) {
        int changedRows = productMapper.likeOrDislikeProduct(prodId, userPrincipal.getUserId(), true);
        if (changedRows == 0){
            throw new FailedToLikeProductException("Error liking the product");
        }
    }

    @Override
    public void dislikeProduct(Long prodId, UserPrincipal userPrincipal) {
        int changedRows = productMapper.likeOrDislikeProduct(prodId, userPrincipal.getUserId(), false);
        if(changedRows == 0){
            throw new FailedToDislikeProductException("Error disliking the product");
        }

    }

}
