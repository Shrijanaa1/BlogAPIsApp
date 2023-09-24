package com.example.blogapplicationbackend.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter

public class CategoryModel {

    @NotNull

    private Long categoryId;
    @Size(min = 7,message = "title mustn't be less than 7character long")
    private String categoryTitle;
    @Size(min=10,message = "Description should be at least 10 characters long")
    private String categoryDescription;
}
