package kelompok1.KedaiIceCream.model.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import kelompok1.KedaiIceCream.model.entity.Blog;
import kelompok1.KedaiIceCream.model.entity.BlogCategory;
import kelompok1.KedaiIceCream.model.repository.BlogCategoryRepository;
import kelompok1.KedaiIceCream.model.repository.BlogRepository;

@Service
public class BlogService {

    @Autowired BlogRepository blogRepository;
    @Autowired BlogCategoryRepository blogCategoryRepository;

    // Implement methods for CRUD operations
    public Blog saveBlog(Blog request) {
            ObjectMapper mapper = new ObjectMapper();
            Blog blog = new Blog();
            try {
                String jsonString = mapper.writeValueAsString(request.getContent());
                blog.setContent(jsonString);
            } catch (JsonProcessingException e) {
                // Handle potential exceptions during JSON conversion (optional)
                e.printStackTrace();
            // You might want to throw a custom exception here
            }
            
            blog.setImage(request.getImage());
            blog.setDescription(request.getDescription());
            blog.setCategory(request.getCategory());
            blog.setTitle(request.getTitle());
            blog.setCreatedAt(LocalDateTime.now());
            blog.setUpdatedAt(LocalDateTime.now());

            return blogRepository.save(blog);
    }

    @Transactional
    public Blog updateBlog(Blog request , Blog existingBlog) {
        ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = mapper.writeValueAsString(request.getContent());
                existingBlog.setContent(jsonString);
            } catch (JsonProcessingException e) {
                // Handle potential exceptions during JSON conversion (optional)
                e.printStackTrace();
            // You might want to throw a custom exception here
            }
        
        existingBlog.setTitle(request.getTitle());
        existingBlog.setDescription(request.getDescription());
        existingBlog.setCategory(request.getCategory());
        existingBlog.setImage(request.getImage());
        existingBlog.setUpdatedAt(LocalDateTime.now());

        return blogRepository.save(existingBlog);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public Page<Blog> getAllBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public List<BlogCategory> getAllBlogCategories() {
        return blogCategoryRepository.findAll();
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }


}