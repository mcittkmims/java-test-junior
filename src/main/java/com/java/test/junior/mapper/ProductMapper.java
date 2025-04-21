/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.mapper;

import com.java.test.junior.model.Product;
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

    @Insert("INSERT INTO Product (name, price, description, user_id) " +
            "VALUES (#{name}, #{price}, #{description}, #{userId})")
    void insertProduct(Product product);

    @Update("UPDATE Product SET name = #{name}, price = #{price}, description = #{description} WHERE id = #{id}")
    void updateProduct(Product product);

    @Delete("DELETE FROM Product WHERE id = #{id}")
    void deleteProduct(Long id);

    @Select("SELECT * FROM Product")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column ="price"),
            @Result(property = "description", column = "description"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Product> getAll();
}