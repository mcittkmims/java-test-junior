package com.java.test.junior.service.user;

import com.java.test.junior.exception.user.UsernameTakenException;
import com.java.test.junior.mapper.user.UserMapper;
import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.UserDTO;
import com.java.test.junior.model.user.UserPrincipal;
import com.java.test.junior.service.jwt.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

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

    public void register(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), encoder.encode(userDTO.getPassword()));
        User newUser = userMapper.insertUser(user);
        if (newUser == null) {
            throw new UsernameTakenException("Username '" + userDTO.getUsername() + "' already exists");
        }
    }

    public String verify(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDTO.getUsername());
        }
        return null;
    }
}
