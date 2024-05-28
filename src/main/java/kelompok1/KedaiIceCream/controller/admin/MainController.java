package kelompok1.KedaiIceCream.controller.admin;

import kelompok1.KedaiIceCream.model.entity.MainData;
import kelompok1.KedaiIceCream.model.service.MainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/main")
public class MainController {

    @Autowired
    private MainDataService mainDataService;

    @GetMapping
    public String viewMain(Model model) {
        MainData mainData = mainDataService.getMainData();
        model.addAttribute("mainData", mainData);
        model.addAttribute("activeUrl", "/admin/main");
        model.addAttribute("pageTitle", "Main");
        return "pages/admin/main/main";
    }

    @PostMapping
    public String editMain(@ModelAttribute("mainData") MainData mainData , RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "Data berhasil diupdate");
        mainDataService.saveMainData(mainData);
        return "redirect:/admin/main";
    }
}