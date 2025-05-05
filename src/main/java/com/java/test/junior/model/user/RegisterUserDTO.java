package com.java.test.junior.model.user;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class RegisterUserDTO {

    @NotBlank(message = "Username is required")
    @Pattern(
            regexp = "^(?=[A-Za-z0-9_]*[A-Za-z])[A-Za-z0-9_]+$",
            message = "Username must contain letters, digits and '_' only!"
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[./#^:'@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and contain at least one lowercase letter, uppercase letter, digit and symbol"
    )
    private String password;

    @NotNull
    private UserRoles role;
}
