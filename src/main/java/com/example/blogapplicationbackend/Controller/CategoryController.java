package com.example.blogapplicationbackend.Controller;

import com.example.blogapplicationbackend.Model.CategoryModel;
import com.example.blogapplicationbackend.Payloads.ApiResponse;
import com.example.blogapplicationbackend.Service.CategoryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryServiceImplementation categoryService;
    //create
    @PostMapping("/create")
    public ResponseEntity<CategoryModel> createCategory(@RequestBody CategoryModel model){
        CategoryModel createdModel=categoryService.createCategory(model);
        return new ResponseEntity<CategoryModel>(createdModel,HttpStatus.CREATED);

    }
    //read
    @GetMapping("/{id}")
    public ResponseEntity<CategoryModel> getCategory(@PathVariable Long id) {
        CategoryModel updatedModel = categoryService.getCategory(id);
        return new ResponseEntity<CategoryModel>(updatedModel, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<CategoryModel> updateCategory(@PathVariable Long id,@RequestBody CategoryModel model){
            CategoryModel updatedModel=categoryService.updateCategory(id,model);
        return new ResponseEntity<CategoryModel>(updatedModel,HttpStatus.CREATED);
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse> (new ApiResponse("Category is deleted succccesssfully!!","true"),HttpStatus.OK);
    }
    //getallCATEGORY
        @GetMapping("/")
        public ResponseEntity<List<CategoryModel>> getALlCategories(){
            List<CategoryModel>models= categoryService.getAllCategory();
            return ResponseEntity.ok(models);
        }
    }
