package com.java.test.junior.controller.user;

import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.UserDTO;
import com.java.test.junior.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserDTO userDTO) {
        userService.register(userDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        return userService.verify(userDTO);
    }
}
