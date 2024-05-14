package kelompok1.KedaiIceCream.controller.admin;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kelompok1.KedaiIceCream.model.entity.Career;
import kelompok1.KedaiIceCream.model.entity.User;
import kelompok1.KedaiIceCream.model.model.CareerDto;
import kelompok1.KedaiIceCream.model.repository.UserRepository;
import kelompok1.KedaiIceCream.model.service.CareerService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin/karir")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewCareers(
            @ModelAttribute("career") Career career,
            @RequestParam(defaultValue = "") String searchTerm,
            @RequestParam(required = false) LocalDate openFilter,
            @RequestParam(required = false) LocalDate closeFilter,
            @RequestParam(required = false) LocalDateTime createdFilter,
            @RequestParam(defaultValue = "1") int page,
            Model model,
            HttpSession session
    ) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Career> careerPage;

        if (searchTerm.isEmpty() && openFilter == null && closeFilter == null && createdFilter == null) {
            careerPage = careerService.getAllCareers(pageable);
        } else {
            careerPage = careerService.findAllWithFilters(searchTerm, openFilter, closeFilter, createdFilter, pageable);
        }

        model.addAttribute("activeUrl", "/admin/karir");
        model.addAttribute("pageTitle", "KARIR");
        model.addAttribute("careers", careerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", careerPage.getTotalPages());
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("openFilter", openFilter);
        model.addAttribute("closeFilter", closeFilter);
        model.addAttribute("createdFilter", createdFilter);
        return "pages/admin/career/career";
    }

    @GetMapping("/create")
    public String viewCareerCreate(@ModelAttribute("career") Career career, Model model) {
        model.addAttribute("activeUrl", "/admin/karir/create");
        model.addAttribute("pageTitle", "Tambah Lowongan Karir Baru");
        return "pages/admin/career/create";
    }

    @PostMapping("/create")
    public String careerCreate(@Valid @ModelAttribute("career") Career careerDto, BindingResult bindingResult, Model model ,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activeUrl", "/admin/karir/create");
            model.addAttribute("pageTitle", "Tambah Lowongan Karir Baru");
            return "pages/admin/career/create"; // Return the create view if there are errors
        }

        Career career = careerService.saveCareer(careerDto);

        if (career == null) {
            // Redirect to a different view or URL after successful submission
            redirectAttributes.addFlashAttribute("status", false);
            redirectAttributes.addFlashAttribute("statusMessage", "data gagal disimpan");
            return "redirect:/admin/karir";
        }

        // Redirect to a different view or URL after successful submission
        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "data berhasil disimpan");
        return "redirect:/admin/karir"; // Redirect to the career list page
    }

    @GetMapping("/edit/{id}")
    public String viewCareerEdit(@PathVariable("id") Long id, @Valid @ModelAttribute("career") Career career, BindingResult bindingResult, Model model ,RedirectAttributes redirectAttributes) {
        Career existing_career = careerService.findById(id);
        if (career == null) {
            redirectAttributes.addFlashAttribute("status", true);
            redirectAttributes.addFlashAttribute("statusMessage", "data gagal ditemukan");
            return "redirect/admin/karir";
        }

        model.addAttribute("career", existing_career);
        model.addAttribute("activeUrl", "/admin/karir/edit");
        model.addAttribute( "pageTitle", "Edit Lowongan Karir");
        return "pages/admin/career/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCareer(@PathVariable("id") Long id, @Valid @ModelAttribute("career") Career career, BindingResult bindingResult , Model model ,RedirectAttributes redirectAttributes , @RequestParam( name = "CloseAt", required = false) LocalDate closeAt , @RequestParam( name = "StartAt", required = false) LocalDate startAt) {        
        if (bindingResult.hasErrors()) {
            model.addAttribute("activeUrl", "/admin/karir/create");
            model.addAttribute("pageTitle", "Edit Lowongan Karir");
            return "pages/admin/career/edit"; // Return the create view if there are errors
        }

        career.setCloseAt(closeAt);
        career.setStartAt(startAt);

        Career existingCareer = careerService.findById(id);
        careerService.updateCareer(career,existingCareer);
        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "data berhasil diupdate");
        return "redirect:/admin/karir";
    }

    @PostMapping("/delete/{id}")
    public String deleteCareer(@PathVariable("id") Long id,  Model model ,RedirectAttributes redirectAttributes ) {        
        redirectAttributes.addFlashAttribute("status", true);
        redirectAttributes.addFlashAttribute("statusMessage", "data berhasil dihapus");
        careerService.deleteById(id);
        return "redirect:/admin/karir";
    }
}