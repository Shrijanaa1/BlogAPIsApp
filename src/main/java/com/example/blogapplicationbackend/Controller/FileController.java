package com.example.blogapplicationbackend.Controller;

import com.example.blogapplicationbackend.Exceptions.ResourceNotFoundException;
import com.example.blogapplicationbackend.Model.PostModel;
import com.example.blogapplicationbackend.Payloads.FileResponse;
import com.example.blogapplicationbackend.Service.FileService;
import com.example.blogapplicationbackend.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/image")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private PostService postService;

    @Value("${project.image}")
    private String imageLocation;

    @PostMapping("/upload/{postId}")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("image")MultipartFile  file,@PathVariable("postId") Long id) {
        String name= null;
        try {
            PostModel post=postService.getPostById(id);
            name = fileService.uploadImage(imageLocation,file);

            post.setImageName(name);
            PostModel updatedPost=postService.updatePost(id,post);
//            return new ResponseEntity<>(new FileResponse(name,"image was successfully uploaded"),HttpStatus.ACCEPTED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(name,"file couldn't be uploaded"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new FileResponse(name,"file successfully uploaded"), HttpStatus.OK);
    }
//method to serve image
    @GetMapping(value = "/getimage/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void getdownloadImage(@PathVariable("imageName") String imageName,
                                        HttpServletResponse response)  {

        try {
            InputStream resource=fileService.getResource(imageLocation,imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource,response.getOutputStream());
        } catch (IOException e) {
            throw new ResourceNotFoundException("imagename",imageName,3L);
        }
    }
}
