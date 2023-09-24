package com.example.blogapplicationbackend.Service;


import com.example.blogapplicationbackend.Entity.Category;
import com.example.blogapplicationbackend.Model.CategoryModel;

import java.util.List;

public interface CategoryService {

    public CategoryModel createCategory(CategoryModel model);

    public CategoryModel getCategory(Long id);

    public CategoryModel updateCategory(Long id, CategoryModel category);

    public void deleteCategory(Long id);

    public List<CategoryModel> getAllCategory();
}
