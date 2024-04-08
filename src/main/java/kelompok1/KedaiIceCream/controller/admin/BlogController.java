package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin/blog/post")
public class BlogController {
    
    @GetMapping
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("pages/admin/post");
        return modelAndView;
    }
}
