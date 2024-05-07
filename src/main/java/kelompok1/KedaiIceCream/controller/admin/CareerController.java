package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/karir")
public class CareerController {

    @GetMapping
    public String viewBlog(Model model){
        model.addAttribute("activeUrl", "/admin/karir");
        model.addAttribute("pageTitle", "KARIR");
        return "pages/admin/career/career";
    }

}
