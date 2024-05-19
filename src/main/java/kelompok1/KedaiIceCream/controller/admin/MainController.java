package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/main")
public class MainController {

    @GetMapping("/landing")
    public String viewBlog(Model model){
        model.addAttribute("activeUrl", "/admin/main/landing");
        model.addAttribute("pageTitle", "LANDING PAGE");
        return "pages/admin/main/landing";
    }

}