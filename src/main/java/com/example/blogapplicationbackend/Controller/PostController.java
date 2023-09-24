package com.example.blogapplicationbackend.Controller;


import com.example.blogapplicationbackend.Config.AppConstants;
import com.example.blogapplicationbackend.Model.PostModel;
import com.example.blogapplicationbackend.Payloads.ApiResponse;
import com.example.blogapplicationbackend.Payloads.PostResponse;
import com.example.blogapplicationbackend.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    //    create
    @PostMapping("/userId/{userId}/categoryid/{categoryId}/posts")
    public ResponseEntity<PostModel> createPost(@RequestBody PostModel model,
                                                @PathVariable("userId") Long userid,
                                                @PathVariable("categoryId") Long categoryid) {
        PostModel createdPost = postService.createPost(model, userid, categoryid);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //    get by user
    @GetMapping("/userid/{userid}")
    public ResponseEntity<List<PostModel>> getByUser(@PathVariable("userid") Long UserId) {
        List<PostModel> posts = postService.getPostByUser(UserId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    //    get by category
    @GetMapping("/categoryid/{categoryId}")
    public ResponseEntity<List<PostModel>> getByCategory(@PathVariable("categoryId") Long CategoryId) {
        List<PostModel> posts = postService.getPostsByCategory(CategoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    //get all posts
    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(name = "pageNumber",defaultValue = AppConstants.Page_NUMBER,required = false)Integer number,
                                                       @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer size,
                                                    @RequestParam(name = "Sorting",defaultValue = AppConstants.SORT_BY,required = false)String sorting,
                                                    @RequestParam(name = "direction",defaultValue = AppConstants.SORT_DIR,required = false)String direction){
    PostResponse posts=postService.getAllPost(number,size,sorting,direction);
    return new ResponseEntity<>(posts,HttpStatus.FOUND);
    }
    @DeleteMapping("/{id}")
    public ApiResponse deletePost(@PathVariable Long id){
            postService.deletePosts(id);
            return new ApiResponse("post is successfully deleted","success");
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostModel>> searchPostByTitle(@PathVariable String keyword){
            List<PostModel> searchedPosts=postService.searchPost(keyword);
            return new ResponseEntity<>(searchedPosts,HttpStatus.FOUND);
    }

    }
