package com.java.test.junior.service.user;

import com.java.test.junior.exception.user.UsernameTakenException;
import com.java.test.junior.mapper.user.UserMapper;
import com.java.test.junior.model.user.LoginUserDTO;
import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.RegisterUserDTO;
import com.java.test.junior.service.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public UserService(UserMapper userMapper, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void register(RegisterUserDTO registerUserDTO) {
        User user = new User(registerUserDTO.getUsername(), encoder.encode(registerUserDTO.getPassword()), registerUserDTO.getRole().toString());
        User newUser = userMapper.insertUser(user);
        if (newUser == null) {
            throw new UsernameTakenException("Username '" + registerUserDTO.getUsername() + "' already exists");
        }
    }

    public String verify(LoginUserDTO loginUserDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginUserDTO.getUsername());
        }
        return null;
    }
}
