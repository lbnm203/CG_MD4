package com.codegym.blog_applications.service;

import com.codegym.blog_applications.entity.Blog;
import com.codegym.blog_applications.repository.IBlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements IBlogService{
    private final IBlogRepository blogRepository;

    public BlogService(IBlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog findById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public boolean save(Blog blog) {
        if (blog.getId() == null) {
            blogRepository.save(blog);
            return true;
        } else {
            if (blogRepository.existsById(blog.getId())) {
                return false;
            } else  {
                blogRepository.save(blog);
                return true;
            }
        }
    }

    @Override
    public boolean update(Blog blog) {
        if (blogRepository.existsById(blog.getId())) {
            blogRepository.save(blog);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Blog> searchByTitle(String title) {
        return blogRepository.findAllByTitleContaining(title);
    }
}
