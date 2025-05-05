package com.java.test.junior.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
