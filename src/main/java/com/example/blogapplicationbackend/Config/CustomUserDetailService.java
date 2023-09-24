package com.example.blogapplicationbackend.Config;

import com.example.blogapplicationbackend.Entity.User;
import com.example.blogapplicationbackend.Exceptions.ResourceNotFoundException;
import com.example.blogapplicationbackend.Repository.USerRepository;
import com.example.blogapplicationbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private USerRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user= userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user","user name:"+username  , 0L));
       return  user;
    }
}
