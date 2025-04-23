package com.java.test.junior.mapper.user;

import com.java.test.junior.model.user.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {


    @Select("INSERT INTO users (username, password, role) " +
            "VALUES (#{username}, #{password}, #{role}) " +
            "ON CONFLICT (username) DO NOTHING " +
            "RETURNING *")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role")
    })
    User insertUser(User user);


    @Select("SELECT * FROM users WHERE username LIKE #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role"),
    })
    User findByUsername(String username);

}
