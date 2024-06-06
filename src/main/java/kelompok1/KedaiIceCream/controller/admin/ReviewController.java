package kelompok1.KedaiIceCream.controller.admin;

import kelompok1.KedaiIceCream.model.entity.MenuReview;
import kelompok1.KedaiIceCream.model.entity.MenuCategory;
import kelompok1.KedaiIceCream.model.entity.MenuReply;
import kelompok1.KedaiIceCream.model.entity.MenuReview;
import kelompok1.KedaiIceCream.model.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/review")
public class ReviewController {

    @Autowired
    private MenuService menuService;

    @GetMapping()
    public String viewMenu(Model model, @ModelAttribute("newReview") MenuReview menuReview , @ModelAttribute("newReply") MenuReply menuReply) {
        List<MenuReview> review = menuService.getAllMenuReview();


        model.addAttribute("reviews", review);
        model.addAttribute("activeUrl", "/admin/review");
        model.addAttribute("pageTitle", "Review Menu");

        return "pages/admin/review/review";
    }

    @GetMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<MenuReview> optionalReview = menuService.getMenuReviewById(id);
        if (optionalReview.isPresent()) {
            MenuReview review = optionalReview.get();
            menuService.deleteReviewById(review); // Add this method to the MenuService
            redirectAttributes.addFlashAttribute("message", "Review deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Review not found.");
        }
        return "redirect:/admin/review";
    }

    @PostMapping("/reply")
    public String saveReply(@ModelAttribute("newReply") MenuReply menuReply, @RequestParam("review_id") Long reviewId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        Optional<MenuReview> optionalReview = menuService.getMenuReviewById(reviewId);
        if (!optionalReview.isPresent()) {

            redirectAttributes.addFlashAttribute("status", false);
            redirectAttributes.addFlashAttribute("statusMessage", "Review not found.");
            return "redirect:/menu";
        }
        MenuReview existingReview = optionalReview.get();
        menuReply.setMenuReview(existingReview);

        // Simpan balasan review
        menuService.saveReply(menuReply  , reviewId);
        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "Balasan berhasil ditambahkan.");
        return "redirect:admin//menu/" + menuReply.getMenuReview().getMenu().getId();
    }

    
}