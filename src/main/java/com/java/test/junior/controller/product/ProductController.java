/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.controller.product;


import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;
import com.java.test.junior.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


@RequestMapping("/products")
@RestController
@AllArgsConstructor
@Validated
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

    @Valid
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts(@RequestParam @Min(1) int page, @RequestParam("page_size") @Min(1) @Max(150) int pageSize) {
        return productService.getLimitedProducts(page, pageSize);
    }
}