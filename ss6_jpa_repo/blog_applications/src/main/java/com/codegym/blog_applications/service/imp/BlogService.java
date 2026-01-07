package com.codegym.blog_applications.service.imp;

import com.codegym.blog_applications.entity.Blog;
import com.codegym.blog_applications.repository.IBlogRepository;
import com.codegym.blog_applications.service.IBlogService;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements IBlogService {
    private final IBlogRepository blogRepository;

    public BlogService(IBlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Page<Blog> getAllBlog(int size, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        return blogRepository.findAllBlog(PageRequest.of(page, size, sort));
    }

    @Override
    public Blog findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new NoResultException("Not found with id: " + id + "")) ;
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
    public Page<Blog> searchByTitle(int size, int page, String title) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return blogRepository.findAllByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Page<Blog> findByCategoryId(int size, int page, Long categoryId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return blogRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<Blog> searchByTitleAndCategory(int size, int page, String title, Long categoryId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return blogRepository.findByTitleContainingIgnoreCaseAndCategoryId(title, categoryId, pageable);
    }
}
