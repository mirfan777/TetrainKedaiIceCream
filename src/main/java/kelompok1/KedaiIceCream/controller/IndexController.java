package kelompok1.KedaiIceCream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String viewLanding() {
        return "pages/landing";
    }

    @GetMapping("about")
    public String viewAbout() {
        return "pages/landing";
    }

    @GetMapping("contact")
    public String viewContact() {
        return "pages/landing";
    }

    @GetMapping("Menus")
    public String view() {
        return "pages/landing";
    }


    
    
}