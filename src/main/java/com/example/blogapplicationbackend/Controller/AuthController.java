package com.example.blogapplicationbackend.Controller;

import com.example.blogapplicationbackend.Config.JwtAuthRequest;
import com.example.blogapplicationbackend.Config.JwtAuthResponse;
import com.example.blogapplicationbackend.Config.JwtTokenHelper;
import com.example.blogapplicationbackend.Exceptions.ApiException;
import com.example.blogapplicationbackend.Model.UserModel;
import com.example.blogapplicationbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception
    {
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
       String token= this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
        jwtAuthResponse.setToken(token);
        return  new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);

    }
    private  void authenticate(String username,String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationFilter=new UsernamePasswordAuthenticationToken(username,password) ;

        try{
                this.authenticationManager.authenticate(usernamePasswordAuthenticationFilter);
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details!!");
            throw new ApiException("Invalid Username or password!!");
        }
    }

    //register new user api

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserModel userModel)
    {
        UserModel registeredUser= userService.registerNewUser(userModel);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
