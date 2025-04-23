package com.java.test.junior.service.user;

import com.java.test.junior.exception.user.UsernameTakenException;
import com.java.test.junior.mapper.user.UserMapper;
import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), encoder.encode(userDTO.getPassword()));
        User newUser = userMapper.insertUser(user);
        if (newUser == null) {
            throw new UsernameTakenException("Username '" + userDTO.getUsername() + "' already exists");
        }
        return userMapper.insertUser(user);
    }
}
