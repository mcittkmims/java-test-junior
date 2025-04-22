/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.mapper;

import com.java.test.junior.model.Product;
import com.java.test.junior.model.ProductDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM Product WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column ="price"),
            @Result(property = "description", column = "description"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Product findById(Long id);

    @Select("INSERT INTO Product (name, price, description, user_id) " +
            "VALUES (#{product.name}, #{product.price}, #{product.description}, #{userId}) " +
            "RETURNING *")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "description", column = "description"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Product insertProduct(@Param("product") ProductDTO productDTO,@Param("userId") Long userId);

    @Select("UPDATE Product SET name = #{product.name}, price = #{product.price}, description = #{product.description} " +
            "WHERE id = #{id} " +
            "RETURNING *")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "description", column = "description"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Product updateProduct(@Param("product") ProductDTO productDTO, @Param("id") Long id);

    @Delete("DELETE FROM Product WHERE id = #{id}")
    void deleteProduct(Long id);


    @Select("SELECT * FROM Product LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column ="price"),
            @Result(property = "description", column = "description"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Product> getLimitedProducts(@Param("limit") int limit, @Param("offset") int offset);
}