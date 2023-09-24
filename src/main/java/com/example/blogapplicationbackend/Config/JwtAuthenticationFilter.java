package com.example.blogapplicationbackend.Config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private JwtTokenHelper tokenHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken=request.getHeader("Authorization");
//        Bearer 2344578dsdg
        String username=tokenHelper.extractUsername(requestToken);
        String token=null;
        if(requestToken != null && requestToken.startsWith("Bearer "))
        {
             token=requestToken.substring(7);

             try {
                 username=  this.tokenHelper.extractUsername(token);

             }catch (IllegalArgumentException e){
                 System.out.println("username couldn't be get");
             }catch (ExpiredJwtException e){
                 System.out.println("jwt token has expired");
             }catch (MalformedJwtException e){
                 System.out.println("invalid  jwt");
             }
        }else {
            System.out.println("jwt token doesn't start with bearer");
        }
//        once we get token we validate
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userDetailService.loadUserByUsername(username);
            if(tokenHelper.validateToken(token,userDetails)){
//                now we should do authentication

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()    );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            else {
                System.out.println("invalid jwt token");
            }
        }
        filterChain.doFilter(request,response);
    }
}
