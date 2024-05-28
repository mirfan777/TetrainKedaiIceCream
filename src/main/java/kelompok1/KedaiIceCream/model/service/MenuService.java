package kelompok1.KedaiIceCream.model.service;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import kelompok1.KedaiIceCream.model.entity.Menu;
import kelompok1.KedaiIceCream.model.entity.MenuCategory;
import kelompok1.KedaiIceCream.model.repository.MenuCategoryRepository;
import kelompok1.KedaiIceCream.model.repository.MenuRepository;
import kelompok1.KedaiIceCream.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;


    @Autowired
    private HttpServletRequest request;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Menu createMenu(Menu menu, MultipartFile file) {
        handleFileUpload(menu, file);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setUpdatedAt(LocalDateTime.now());
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, Menu menu, MultipartFile file) {
        Menu existingMenu = menuRepository.findById(id).orElse(null);
        if (existingMenu != null) {
            existingMenu.setTitle(menu.getTitle());
            existingMenu.setDescription(menu.getDescription());
            existingMenu.setPrice(menu.getPrice());
            existingMenu.setDiscount(menu.getDiscount());
            existingMenu.setPromo(menu.getPromo());
            existingMenu.setCategory(menu.getCategory());
            handleFileUpdated(menu , existingMenu, file);
            existingMenu.setUpdatedAt(LocalDateTime.now());
            return menuRepository.save(existingMenu);
        }
        return null;
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    public List<MenuCategory> getAllCategories() {
        return menuCategoryRepository.findAll();
    }

    public MenuCategory getCategoryById(Long id) {
        return menuCategoryRepository.findById(id).orElse(null);
    }

    public MenuCategory createCategory(MenuCategory category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return menuCategoryRepository.save(category);
    }

    public MenuCategory updateCategory(Long id, MenuCategory category) {
        MenuCategory existingCategory = menuCategoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setTitle(category.getTitle());
            existingCategory.setUpdatedAt(LocalDateTime.now());
            return menuCategoryRepository.save(existingCategory);
        }
        return null;
    }

    public void deleteCategory(Long id) {
        menuCategoryRepository.deleteById(id);
    }

    private void handleFileUpload(Menu menu, MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = FileUploadUtil.generateUniqueFileName(file.getOriginalFilename());
            ServletContext servletContext = request.getServletContext();
            String uploadDir = servletContext.getRealPath("") + File.separator + "images";
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            menu.setImage("/images/" + fileName);
        }
    }

    private void handleFileUpdated (Menu existingMenu ,Menu menu, MultipartFile file){
        String currentImagePath = existingMenu.getImage();

        if (file != null && !file.isEmpty()) {
            // Delete the current image file if it exists
            if (currentImagePath != null && !currentImagePath.isEmpty()) {
                String fullImagePath = request.getServletContext().getRealPath("") + File.separator + currentImagePath.substring(1);
                try {
                    Files.deleteIfExists(Paths.get(fullImagePath));
                } catch (IOException e) {
                    log.error("Failed to delete current image file: " + fullImagePath, e);
                }
            }

            // Generate unique filename
            String fileName = FileUploadUtil.generateUniqueFileName(file.getOriginalFilename());

            // Get the ServletContext to get the real path of the project
            ServletContext servletContext = request.getServletContext();

            // Set the upload directory to "public/images/" relative to the real path of the project
            String uploadDir = servletContext.getRealPath("") + File.separator + "images";

            try {
                FileUploadUtil.saveFile(uploadDir, fileName, file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }

            // Set the new image path in the menu entity
            menu.setImage("/images/" + fileName);
        } else {
            // Keep the existing image path
            menu.setImage(existingMenu.getImage());
        }
    }
}