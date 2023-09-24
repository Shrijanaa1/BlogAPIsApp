package com.example.blogapplicationbackend.Service;

import com.example.blogapplicationbackend.Entity.Comment;
import com.example.blogapplicationbackend.Entity.Post;
import com.example.blogapplicationbackend.Exceptions.ResourceNotFoundException;
import com.example.blogapplicationbackend.Model.CommentModel;
import com.example.blogapplicationbackend.Model.PostModel;
import com.example.blogapplicationbackend.Repository.CommentRepository;
import com.example.blogapplicationbackend.Repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private ModelMapper modelMapper=new ModelMapper();

    @Override
    public CommentModel createComment(Long postId, CommentModel commentModel) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postid",postId));
        Comment comment=modelMapper.map(commentModel, Comment.class);
        comment.setPost(post);
        Comment savedComment=commentRepository.save(comment);
        CommentModel model=modelMapper.map(savedComment,CommentModel.class);
        return model;
    }

    @Override
    public void deleteComment(Long commentId) {

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment id",commentId));
        commentRepository.delete(comment);

    }
}
