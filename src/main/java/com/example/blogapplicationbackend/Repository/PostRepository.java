package com.example.blogapplicationbackend.Repository;

import com.example.blogapplicationbackend.Entity.Category;
import com.example.blogapplicationbackend.Entity.Post;
import com.example.blogapplicationbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByPostTitleContaining(String postTitle);
}
