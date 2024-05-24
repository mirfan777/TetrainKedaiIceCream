package kelompok1.KedaiIceCream.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kelompok1.KedaiIceCream.model.dto.RegisterUser;
import kelompok1.KedaiIceCream.model.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/auth/register")
public class RegisterController {
    @Autowired
    private AuthService userService;
    

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerUser", new RegisterUser());
        return "pages/auth/register"; // Return the name of your registration page template
    }

    @PostMapping
    public String registerUser(@ModelAttribute("registerUser") RegisterUser registerUser) {
        userService.register(registerUser);
        return "redirect:/auth/login"; // Redirect to the login page after successful registration
    }
}

