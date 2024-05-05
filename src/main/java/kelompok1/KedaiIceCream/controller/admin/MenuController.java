package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    @GetMapping
    public String viewMenu(Model model){
        model.addAttribute("activeUrl", "/admin/menu");
        return "pages/admin/menu/menu";
    }
}
