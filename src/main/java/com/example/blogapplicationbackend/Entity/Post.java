package com.example.blogapplicationbackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postTitle;
     private String postDescription;
     private  String imageName;
     @ManyToOne
     @JoinColumn(name = "category_id")
     private Category category;
     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;

     @OneToMany(mappedBy ="post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     private Set<Comment> comments=new HashSet<>();

     private Date dateAdded;

}
