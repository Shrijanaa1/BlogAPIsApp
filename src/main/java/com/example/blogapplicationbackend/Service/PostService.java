package com.example.blogapplicationbackend.Service;

import com.example.blogapplicationbackend.Model.PostModel;
import com.example.blogapplicationbackend.Payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostModel createPost(PostModel post,Long userId,Long categoryId);
    PostModel updatePost(Long id,PostModel post);
    void deletePosts(Long id);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sorting,String direction);
    PostModel getPostById(Long id);

    List<PostModel> getPostsByCategory(Long  categoryId);

    List<PostModel> getPostByUser(Long USerId);

    List<PostModel> searchPost(String keyword);

}
