package com.codegym.blog_wajax.dto;

import com.codegym.blog_wajax.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public BlogDTO() {
    }

    public BlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.author = blog.getAuthor();
        this.content = blog.getContent();
        if (blog.getCategory() != null) {
            this.categoryId = blog.getCategory().getId();
            this.categoryName = blog.getCategory().getName();
        }
        this.createdDate = blog.getCreatedDate();
        this.updatedDate = blog.getUpdatedDate();
    }

}
