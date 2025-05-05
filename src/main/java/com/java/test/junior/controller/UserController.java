package com.java.test.junior.controller;

import com.java.test.junior.model.user.LoginUserDTO;
import com.java.test.junior.model.user.RegisterUserDTO;
import com.java.test.junior.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        userService.register(registerUserDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.verify(loginUserDTO);
    }
}
