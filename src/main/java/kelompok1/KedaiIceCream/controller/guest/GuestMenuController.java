package kelompok1.KedaiIceCream.controller.guest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kelompok1.KedaiIceCream.model.entity.Menu;
import kelompok1.KedaiIceCream.model.entity.MenuReply;
import kelompok1.KedaiIceCream.model.entity.MenuReview;
import kelompok1.KedaiIceCream.model.service.MenuService;
import kelompok1.KedaiIceCream.util.FilterBadword;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/menu")
public class GuestMenuController {

    @Autowired MenuService menuService;

    @GetMapping
        public String viewmenu(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 12;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Menu> menuPage = menuService.getAllMenusPages(pageable);

        model.addAttribute("activeUrl", "/admin/menu");
        model.addAttribute("pageTitle", "menuS");
        model.addAttribute("categories", menuService.getAllMenuCategories());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", menuPage.getTotalPages());
        model.addAttribute("menus", menuPage.getContent());

        return "pages/guest/menu/menu";
        }

        @GetMapping("/{id}")
        public String viewMenuDetail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
            Menu existingMenu = menuService.getMenuById(id);
            if (existingMenu == null) {
                redirectAttributes.addFlashAttribute("status", false);
                redirectAttributes.addFlashAttribute("statusMessage", "data gagal ditemukan");
                return "redirect:/menus";
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<MenuReview> menuReviewPage = menuService.getMenuReviewsByMenuId(existingMenu.getId(), pageable);

            // Create a new instance of MenuReview and add it to the model
            MenuReview newReview = new MenuReview();
            MenuReply newReply = new MenuReply();
            model.addAttribute("newReply", newReply);
            newReview.setMenu(existingMenu);
            model.addAttribute("newReview", newReview);

            // Add other model attributes
            model.addAttribute("menu", existingMenu);
            model.addAttribute("menuReviewPage", menuReviewPage);
            return "pages/guest/menu/detail";
        }

        @PostMapping("/review")
        public String saveReview(@ModelAttribute("newReview") MenuReview menuReview, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
            if (bindingResult.hasErrors()) {
                // Jika terdapat error validasi, kembalikan ke halaman detail menu
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newReview", bindingResult);
                redirectAttributes.addFlashAttribute("newReview", menuReview);
                return "redirect:/menu/" + menuReview.getMenu().getId();
            }

            if (FilterBadword.isBadWord(menuReview.getBody())) {
                redirectAttributes.addFlashAttribute("status", false);
                redirectAttributes.addFlashAttribute("statusMessage", "Gak Sopan Le");
                return "redirect:/menu/" + menuReview.getMenu().getId();
            }

            // Simpan review baru
            menuService.saveReview(menuReview);
            redirectAttributes.addFlashAttribute("status", true);
            redirectAttributes.addFlashAttribute("statusMessage", "Review berhasil ditambahkan.");
            return "redirect:/menu/" + menuReview.getMenu().getId();
        }

        @PostMapping("/review/reply")
        public String saveReply(@ModelAttribute("newReply") MenuReply menuReply, @RequestParam("review_id") Long reviewId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
            if (bindingResult.hasErrors()) {
                // Jika terdapat error validasi, kembalikan ke halaman detail menu
                redirectAttributes.addFlashAttribute("newReply", menuReply);
                return "redirect:/menu/" + menuReply.getMenuReview().getMenu().getId();
            }

            Optional<MenuReview> optionalReview = menuService.getMenuReviewById(reviewId);
            if (!optionalReview.isPresent()) {
                log.info("Review not found.");
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
            return "redirect:/menu/" + menuReply.getMenuReview().getMenu().getId();
        }
    
}