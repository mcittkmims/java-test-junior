package com.java.test.junior.model.user;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
}
