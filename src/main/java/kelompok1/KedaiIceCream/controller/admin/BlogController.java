package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kelompok1.KedaiIceCream.model.entity.Blog;
import kelompok1.KedaiIceCream.model.service.BlogService;
import kelompok1.KedaiIceCream.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    @Autowired BlogService blogService;

    @GetMapping
    public String viewBlog(Model model, @RequestParam(defaultValue = "1") int page) {
    int pageSize = 12;
    Pageable pageable = PageRequest.of(page - 1, pageSize);
    Page<Blog> blogPage = blogService.getAllBlog(pageable);

    model.addAttribute("activeUrl", "/admin/blog");
    model.addAttribute("pageTitle", "BLOGS");
    model.addAttribute("categories", blogService.getAllBlogCategories());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", blogPage.getTotalPages());
    model.addAttribute("blogs", blogPage.getContent());

    return "pages/admin/blog/blog";
    }

    @GetMapping("/create")
    public String viewBlogCreate(@ModelAttribute("blog") Blog blog, Model model){
        
        model.addAttribute("activeUrl", "/admin/blog/create");
        model.addAttribute("pageTitle", "CREATE BLOG");
        model.addAttribute("categories", blogService.getAllBlogCategories());
        return "pages/admin/blog/create.html";
    }

    @PostMapping("/create")
    public String blogCreate(@Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult, @RequestParam("imageFile") MultipartFile file, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activeUrl", "/admin/blog/create");
            model.addAttribute("pageTitle", "CREATE BLOG");
            model.addAttribute("categories", blogService.getAllBlogCategories());
            return "pages/admin/blog/create.html";
        }

        if (!file.isEmpty()) {
            // Generate unique filename
            String fileName = FileUploadUtil.generateUniqueFileName(file.getOriginalFilename());
            // Get the ServletContext to get the real path of the project
            ServletContext servletContext = request.getServletContext();
            // Set the upload directory to "public/images/" relative to the real path of the project
            String uploadDir = servletContext.getRealPath("") + File.separator + "images";

            try {
                FileUploadUtil.saveFile( uploadDir, fileName, file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
            // Set the image path in the Blog entity
            blog.setImage("/images/" + fileName);
        }

        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "blog berhasil dipost");
        blogService.saveBlog(blog);
        return "redirect:/admin/blog";
    }

    @GetMapping("/edit/{id}")
    public String viewBlogEdit(@PathVariable("id") Long id, @Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult , Model model ,RedirectAttributes redirectAttributes){
        Blog existingBlog = blogService.getBlogById(id);

        try {
            String existingContent = existingBlog.getContent();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(existingContent);
            model.addAttribute("jsonContent", jsonNode);
            System.out.println(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (blog == null) {
            redirectAttributes.addFlashAttribute("status", true);
            redirectAttributes.addFlashAttribute("statusMessage", "data gagal ditemukan");
            return "redirect/admin/blog";
        }

        model.addAttribute("blog", existingBlog);
        model.addAttribute("activeUrl", "/admin/blog/edit");
        model.addAttribute("pageTitle", "EDIT BLOG");
        model.addAttribute("categories", blogService.getAllBlogCategories());
        return "pages/admin/blog/edit";
    }

    @PostMapping("/edit/{id}")
    public String editBlog(@PathVariable("id") Long id, @Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult, @RequestParam(value = "imageFile", required = false) MultipartFile file, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Blog existingBlog = blogService.getBlogById(id);

        if (bindingResult.hasErrors()) {
            // Add categories to the model
            model.addAttribute("categories", blogService.getAllBlogCategories());
            return "pages/admin/blog/edit"; // Return the same view to display errors
        }

        String currentImagePath = existingBlog.getImage();

        if (file != null && !file.isEmpty()) {
            // Delete the current image file if it exists
            if (currentImagePath != null && !currentImagePath.isEmpty()) {
                String fullImagePath = request.getServletContext().getRealPath("") + File.separator + currentImagePath.substring(1);
                try {
                    Files.deleteIfExists(Paths.get(fullImagePath));
                } catch (IOException e) {
                    log.error("Failed to delete current image file: " + fullImagePath, e);
                }
            }

            // Generate unique filename
            String fileName = FileUploadUtil.generateUniqueFileName(file.getOriginalFilename());

            // Get the ServletContext to get the real path of the project
            ServletContext servletContext = request.getServletContext();

            // Set the upload directory to "public/images/" relative to the real path of the project
            String uploadDir = servletContext.getRealPath("") + File.separator + "images";

            try {
                FileUploadUtil.saveFile(uploadDir, fileName, file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }

            // Set the new image path in the Blog entity
            blog.setImage("/images/" + fileName);
        } else {
            // Keep the existing image path
            blog.setImage(existingBlog.getImage());
        }

        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "Data berhasil diupdate");
        blogService.updateBlog(blog, existingBlog);
        return "redirect:/admin/blog";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Blog existingBlog = blogService.getBlogById(id);

        if (existingBlog != null) {
            // Get the image path from the existing blog
            String imagePath = existingBlog.getImage();

            if (imagePath != null && !imagePath.isEmpty()) {
                // Get the ServletContext to get the real path of the project
                ServletContext servletContext = request.getServletContext();

                // Construct the full path of the image file
                String fullImagePath = servletContext.getRealPath("") + File.separator + imagePath.substring(1);

                // Delete the image file
                try {
                    Files.deleteIfExists(Paths.get(fullImagePath));
                } catch (IOException e) {
                    log.error("Failed to delete image file: " + fullImagePath, e);
                }
            }

            // Delete the blog post
            blogService.deleteById(id);
            redirectAttributes.addFlashAttribute("status", true);
            redirectAttributes.addFlashAttribute("statusMessage", "Data berhasil dihapus");
        } else {
            redirectAttributes.addFlashAttribute("status", false);
            redirectAttributes.addFlashAttribute("statusMessage", "Data gagal ditemukan");
        }

        return "redirect:/admin/blog";
    }
}
