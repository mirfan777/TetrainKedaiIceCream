package kelompok1.KedaiIceCream.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    @GetMapping
    public String viewMenu(Model model){
        model.addAttribute("activeUrl", "/admin/menu");
        model.addAttribute("pageTitle", "MENU");
        return "pages/admin/menu/menu";
    }

    @GetMapping("tambah")
    public String viewCreateMenu(Model model){
        model.addAttribute("activeUrl", "/admin/menu/tambah");
        model.addAttribute("pageTitle", "TAMBAH MENU");
        return "pages/admin/menu/menu_create";
    }
}
