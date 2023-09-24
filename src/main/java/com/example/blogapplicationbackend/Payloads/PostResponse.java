package com.example.blogapplicationbackend.Payloads;
import com.example.blogapplicationbackend.Model.PostModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinitions;
import java.util.List;

@NoArgsConstructor
@Data
public class PostResponse {
    private List<PostModel> content;
    private int pageNumber;
    private int pageSize;
    private String sorting;
    private Long totalElements;
    private int totalPages;
    private boolean lastPage;

}
