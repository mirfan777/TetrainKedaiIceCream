package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    @GetMapping
    public String viewBlog(Model model){
        model.addAttribute("activeUrl", "/admin/blog");
        model.addAttribute("pageTitle", "BLOGS");
        return "pages/admin/blog/blog";
    }

    @GetMapping("create")
    public String viewBlogCreate(Model model){
        model.addAttribute("activeUrl", "/admin/blog/create");
        model.addAttribute("pageTitle", "CREATE BLOG");
        return "pages/admin/blog/create";
    }
}
