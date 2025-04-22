/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.controller;

import com.java.test.junior.exception.PaginationParamsException;
import com.java.test.junior.model.Product;
import com.java.test.junior.model.ProductDTO;
import com.java.test.junior.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/products")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{prodId}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable Long prodId){
        return productService.getProduct(prodId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody @Valid ProductDTO productDTO){
        return productService.addProduct(productDTO);
    }

    @PutMapping("/{prodId}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long prodId, @RequestBody @Valid ProductDTO product){
        return productService.updateProduct(prodId, product);
    }

    @DeleteMapping("/{prodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long prodId){
        productService.deleteProduct(prodId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts(@RequestParam int page, @RequestParam("page_size") int pageSize){
        if(page <= 0 || pageSize <= 0){
            throw new PaginationParamsException("Invalid query parameters");
        }
        return productService.getLimitedProducts(page, pageSize);
    }
}