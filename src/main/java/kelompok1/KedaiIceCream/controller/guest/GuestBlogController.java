package kelompok1.KedaiIceCream.controller.guest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import kelompok1.KedaiIceCream.model.entity.Blog;
import kelompok1.KedaiIceCream.model.service.BlogService;



@Controller
@RequestMapping("/blog")
public class GuestBlogController {

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

        return "pages/guest/blog/blogs";
        }
    
    @GetMapping("/{id}")
    public String viewBlogEdit(@PathVariable("id") Long id,@ModelAttribute("blog") Blog blog , Model model ,RedirectAttributes redirectAttributes){
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
            return "redirect/blogs";
        }

        model.addAttribute("blog", existingBlog);
        model.addAttribute("activeUrl", "/admin/blog/edit");
        model.addAttribute("pageTitle", "edit BLOG");
        return "pages/guest/blog/detail";
    }
    
    
}