package com.example.blogapplicationbackend.Service;

import com.example.blogapplicationbackend.Entity.Category;
import com.example.blogapplicationbackend.Exceptions.ResourceNotFoundException;
import com.example.blogapplicationbackend.Model.CategoryModel;
import com.example.blogapplicationbackend.Repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {
    @Autowired
    private CategoryRepository repository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public CategoryModel createCategory(CategoryModel  category) {
       Category cat= mapper.map(category, Category.class);
        Category addedCat=repository.save(cat);
        return mapper.map(addedCat,CategoryModel.class);
    }

    @Override
    public CategoryModel getCategory(Long id) {
        Category category=repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",id));
        return mapper.map(category,CategoryModel.class);
    }

    @Override
    public CategoryModel updateCategory(Long id, CategoryModel category) {
        Category cat=repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("category","Category Id",category.getCategoryId()));
        cat.setCategoryTitle(category.getCategoryTitle());
        cat.setCategoryDescription(category.getCategoryDescription());
        Category updatedCat=repository.save(cat);
        return mapper.map(updatedCat,CategoryModel.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category=repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",id));
        repository.delete(category);
    }
    @Override
    public List<CategoryModel> getAllCategory(){
        List<Category> categories=repository.findAll() ;
       List<CategoryModel> cat1= categories.stream().map((cat)-> mapper.map(cat,CategoryModel.class)).collect(Collectors.toList());
       return cat1;
    }

}
