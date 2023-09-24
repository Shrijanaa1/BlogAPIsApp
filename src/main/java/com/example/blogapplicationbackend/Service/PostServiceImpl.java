package com.example.blogapplicationbackend.Service;

import com.example.blogapplicationbackend.Entity.Category;
import com.example.blogapplicationbackend.Entity.Post;
import com.example.blogapplicationbackend.Entity.User;
import com.example.blogapplicationbackend.Exceptions.ResourceNotFoundException;
import com.example.blogapplicationbackend.Model.PostModel;
import com.example.blogapplicationbackend.Payloads.PostResponse;
import com.example.blogapplicationbackend.Repository.CategoryRepository;
import com.example.blogapplicationbackend.Repository.PostRepository;
import com.example.blogapplicationbackend.Repository.USerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository repository;
    @Autowired
    private USerRepository uSerRepository;
    @Autowired
    private CategoryRepository catRepository;

    private ModelMapper modelMapper=new ModelMapper();

    @Override
    public PostModel createPost(PostModel post1,Long userId,Long categoryId)
    {
        User user=uSerRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User id",userId));
        Category category=catRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
        Post post= modelMapper.map(post1,Post.class);
        post.setImageName("default.png");
        post.setDateAdded(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post postNew=repository.save(post);
        return modelMapper.map(postNew,PostModel.class);

    }

    @Override
    public PostModel updatePost(Long id, PostModel post) {
       Post post1= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",id));
       post1.setPostTitle(post.getPostTitle());
       post1.setPostDescription(post.getPostDescription());
       post1.setImageName(post.getImageName());
       repository.save(post1);
       return modelMapper.map(post1,PostModel.class);

    }

    @Override
    public void deletePosts(Long id) {
        Post post=repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Post id",id));
        repository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String Sorting,String direction) {
        Sort sort=(direction.equalsIgnoreCase("asc")?sort=Sort.by(Sorting).ascending():Sort.by(Sorting).descending());
        Pageable p1=PageRequest.of(pageNumber,pageSize, sort);
//        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        Page<Post> pages=repository.findAll(p1);
        List<Post> posts= pages.getContent();

        List<PostModel> postsm=posts.stream().map((post)->modelMapper.map(post,PostModel.class)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postsm);
        postResponse.setPageNumber(pages.getNumber());
        postResponse.setPageSize(pages.getSize());
//        postResponse.setSorting(pages.);
        postResponse.setTotalElements(pages.getTotalElements());
        postResponse.setTotalPages(pages.getTotalPages());
        postResponse.setLastPage(pages.isLast());
        return postResponse;
    }

    @Override
    public PostModel getPostById(Long id) {
        Post post=repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","PostId",id));
        return modelMapper.map(post,PostModel.class);
    }

    @Override
    public List<PostModel> getPostsByCategory(Long categoryId) {
//        Pageable p=PageRequest.of(pageNumber,pageSize);
//        Page<Post> allPosts=repository.findAll(p);
        Category category=catRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
        List<Post> posts=repository.findByCategory(category);
        List<PostModel> postsm= posts.stream().map((post->this.modelMapper.map(post,PostModel.class))).collect(Collectors.toList());
//      PostResponse  postResponse=new PostResponse();
//      postResponse.setContent(postsm);
//      postResponse.setTotalElements(allPosts.getTotalElements());
      return postsm;
    }

    @Override
    public List<PostModel> getPostByUser(Long USerId) {
         User user=uSerRepository.findById(USerId).orElseThrow(()->new ResourceNotFoundException("Post","USerID",USerId));
         List<Post> posts=repository.findByUser(user);
        List<PostModel>postm= posts.stream().map((post -> modelMapper.map(post,PostModel.class))).collect(Collectors.toList());
        return postm;
    }

    @Override
    public List<PostModel> searchPost(String keyword) {
       List<Post> posts= repository.findByPostTitleContaining(keyword);
       List<PostModel>newPosts=posts.stream().map((post)->modelMapper.map(post,PostModel.class)).collect(Collectors.toList());
       return newPosts;
    }
}
