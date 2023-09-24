package com.example.blogapplicationbackend.Controller;

import com.example.blogapplicationbackend.Model.CommentModel;
import com.example.blogapplicationbackend.Payloads.ApiResponse;
import com.example.blogapplicationbackend.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentServ;

    @PostMapping("/post/{postid}/comments")
    public ResponseEntity<CommentModel> createComment(@PathVariable Long postId,
                                                      @RequestBody CommentModel comment){
       CommentModel createdComment= commentServ.createComment(postId,comment);
       return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
    @DeleteMapping ("/comment/{commentid}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentid){
        commentServ.deleteComment(commentid);
//        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        return new ResponseEntity<>(new ApiResponse("comment deleted successfully","true"),HttpStatus.OK);
    }
}
