package com.example.blogapplicationbackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel {
    private Long id;
    @NotBlank
    @Size(min = 3,message = "username must be greater than 3")
    private String name;
    @Email(message = "enter valid email")
    private String email;
    @NotBlank
    @Size(min = 7,max=20,message = "password must be greater then 7 characters and less than 20")
    private String password;
    @NotEmpty
    private String about;

    private List<RoleModel> roles=new ArrayList<>();
}
