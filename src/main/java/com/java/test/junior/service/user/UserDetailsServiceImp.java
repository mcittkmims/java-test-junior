package com.java.test.junior.service.user;

import com.java.test.junior.mapper.user.UserMapper;
import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mapper.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
