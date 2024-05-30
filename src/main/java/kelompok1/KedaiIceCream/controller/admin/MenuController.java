package kelompok1.KedaiIceCream.controller.admin;

import kelompok1.KedaiIceCream.model.entity.Menu;
import kelompok1.KedaiIceCream.model.entity.MenuCategory;
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

@Slf4j
@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping()
    public String viewMenu(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 10; // Ukuran halaman yang diinginkan
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Menu> menuPage = menuService.getAllMenus(pageable);

        model.addAttribute("menus", menuPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", menuPage.getTotalPages());
        model.addAttribute("activeUrl", "/admin/menu");
        model.addAttribute("pageTitle", "MENU");

        return "pages/admin/menu/menu";
    }

    @GetMapping("create")
    public String viewCreateMenu(Model model, @ModelAttribute("menu") Menu menu, BindingResult bindingResult) {
    List<MenuCategory> categories = menuService.getAllMenuCategories();
    if (bindingResult.hasErrors()) {
        // Add categories to the model
        model.addAttribute("categories", menuService.getAllMenuCategories());
        return "pages/admin/blog/edit"; // Return the same view to display errors
    }
    model.addAttribute("categories", categories);
    model.addAttribute("activeUrl", "/admin/menu/create");
    model.addAttribute("pageTitle", "TAMBAH MENU");
    return "pages/admin/menu/create";
}

    @PostMapping("create")
    public String createMenu(@ModelAttribute Menu menu, BindingResult bindingResult, @RequestParam("imageFile") MultipartFile file, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request) {
        menuService.createMenu(menu, file);
        return "redirect:/admin/menu";
    }

    @GetMapping("{id}/edit")
    public String viewEditMenu(@PathVariable Long id, Model model) {
        Menu menu = menuService.getMenuById(id);
        List<MenuCategory> categories = menuService.getAllMenuCategories();

        model.addAttribute("menu", menu);
        model.addAttribute("categories", categories);
        model.addAttribute("activeUrl", "/admin/menu/" + id + "/edit");
        model.addAttribute("pageTitle", "EDIT MENU");
        return "pages/admin/menu/edit";
    }

    @PostMapping("{id}/edit")
    public String updateMenu(@PathVariable Long id , @ModelAttribute Menu menu, BindingResult bindingResult, @RequestParam(value = "imageFile", required = false) MultipartFile file, Model model ,RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            // Add categories to the model
            model.addAttribute("categories",menuService.getAllMenuCategories());
            return "pages/admin/blog/edit"; // Return the same view to display errors
        }

        if(file != null && !file.isEmpty()){
            menuService.updateMenu(id, menu , file);
        }

        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "Data berhasil diupdate");
        return "redirect:/admin/menu";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Menu existingmenu = menuService.getMenuById(id);

        if (existingmenu != null) {
            // Get the image path from the existing menu
            String imagePath = existingmenu.getImage();

            if (imagePath != null && !imagePath.isEmpty()) {
                // Get the ServletContext to get the real path of the project
                ServletContext servletContext = request.getServletContext();

                // Construct the full path of the image file
                String fullImagePath = servletContext.getRealPath("") + File.separator + imagePath.substring(1);

                // Delete the image file
                try {
                    Files.deleteIfExists(Paths.get(fullImagePath));
                } catch (IOException e) {
                    log.error("Failed to delete image file: " + fullImagePath, e);
                }
            }

            // Delete the menu post
            menuService.deleteMenu(id);
            redirectAttributes.addFlashAttribute("status", true);
            redirectAttributes.addFlashAttribute("statusMessage", "Data berhasil dihapus");
        } else {
            redirectAttributes.addFlashAttribute("status", false);
            redirectAttributes.addFlashAttribute("statusMessage", "Data gagal ditemukan");
        }

        return "redirect:/admin/menu";
    }

}