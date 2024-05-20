package kelompok1.KedaiIceCream.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kelompok1.KedaiIceCream.model.dto.RegisterUser;
import kelompok1.KedaiIceCream.model.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/register")
public class RegisterController {
    @Autowired
    private AuthService userService;

    @GetMapping
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("pages/auth/register");
        log.info("test");
        return modelAndView;
    }

    @PostMapping
    public RegisterUser register(@ModelAttribute RegisterUser user ) {
        userService.register(user);
        return user;
    }
}
