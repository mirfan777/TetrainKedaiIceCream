package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    @GetMapping("/post")
    public String viewBlog(Model model){
        model.addAttribute("activeUrl", "/admin/blog/post");
        return "pages/admin/blog/post";
    }
}
