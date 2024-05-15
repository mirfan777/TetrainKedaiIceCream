package kelompok1.KedaiIceCream.controller.admin;

import java.io.StringReader;

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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import kelompok1.KedaiIceCream.model.entity.Blog;
import kelompok1.KedaiIceCream.model.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;

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
    public String blogCreate(@Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult, Model model ,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activeUrl", "/admin/blog/create");
            model.addAttribute("pageTitle", "CREATE BLOG");
            model.addAttribute("categories", blogService.getAllBlogCategories());
            return "pages/admin/blog/create.html"; // Return the same view to display errors
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
        model.addAttribute("pageTitle", "edit BLOG");
        model.addAttribute("categories", blogService.getAllBlogCategories());
        return "pages/admin/blog/edit";
    }

    @PostMapping("/edit/{id}")
    public String editBlog(@PathVariable("id") Long id, @Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult , Model model ,RedirectAttributes redirectAttributes) {
        Blog existing_blog = blogService.getBlogById(id);
        
        if (bindingResult.hasErrors()) {
            // Add categories to the model
            model.addAttribute("categories", blogService.getAllBlogCategories());
            return "pages/admin/blog/edit"; // Return the same view to display errors
        }

        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "data berhasil diupdate");
        blogService.updateBlog(blog , existing_blog);
        return "redirect:/admin/blog";
    }

    @PostMapping("/delete/{id}")
    public String deleteCareer(@PathVariable("id") Long id,  Model model ,RedirectAttributes redirectAttributes ) {        
        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "data berhasil dihapus");
        blogService.deleteById(id);
        return "redirect:/admin/blog";
    }
}
