package kelompok1.KedaiIceCream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kelompok1.KedaiIceCream.model.entity.MainData;
import kelompok1.KedaiIceCream.model.service.MainDataService;



@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private MainDataService mainDataService;

    @GetMapping
    public String viewLanding() {
        return "pages/landing";
    }

    @GetMapping("about")
    public String viewAbout() {
        return "pages/guest/about";
    }

    @GetMapping("contact")
    public String viewContact(Model model) {
        MainData mainData = mainDataService.getMainData();
        model.addAttribute("mainData", mainData);

        return "pages/guest/contact";
    }





    
    
}