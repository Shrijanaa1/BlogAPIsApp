package com.example.blogapplicationbackend.Security;

import com.example.blogapplicationbackend.Entity.User;
import com.example.blogapplicationbackend.Exceptions.ResourceNotFoundException;
import com.example.blogapplicationbackend.Repository.USerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private USerRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user= repository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email :"+username,0L));
       return user;
    }
}
