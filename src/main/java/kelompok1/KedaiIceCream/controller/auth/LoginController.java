package kelompok1.KedaiIceCream.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kelompok1.KedaiIceCream.model.entity.User;
import kelompok1.KedaiIceCream.model.model.LoginUser;
import kelompok1.KedaiIceCream.model.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/auth/login")
public class LoginController {
    @Autowired
    private AuthService userService;

    @GetMapping
    public String view( @ModelAttribute("user") LoginUser user , Model model , BindingResult bindingResult) {
        return "pages/auth/login";
    }

    @PostMapping
    public String login(@Valid @ModelAttribute("user") LoginUser user ,BindingResult bindingResult, Model model , HttpServletRequest request , RedirectAttributes redirectAttrs) {
        User authUser = null;

        authUser = userService.authenticate(user);

        if (bindingResult.hasErrors() && authUser == null) {
            return "pages/auth/login";
        }
        
        if (authUser != null) {
            log.info("Authentication successful");
            request.getSession().setAttribute("user", authUser);
            return "redirect:/";
        } else {
            redirectAttrs.addFlashAttribute("error_message", "Password or username is incorrect");
            return "redirect:/auth/login";
        }
    
    }
}
