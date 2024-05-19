package kelompok1.KedaiIceCream.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kelompok1.KedaiIceCream.model.dto.RegisterUser;
import kelompok1.KedaiIceCream.model.entity.User;
import kelompok1.KedaiIceCream.model.service.AuthService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;



/**
 * IndexController
 */
@Slf4j
@RestController
@RequestMapping("/")

public class TestController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AuthService userService;

    @GetMapping
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("pages/landing");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("user", user);
        modelAndView.addObject("activeUrl", "/");
        return modelAndView;
    }

    @PostMapping("/test")
    public RegisterUser register(@ModelAttribute RegisterUser user ) {
        userService.register(user);
        log.info("test");
        log.info(user.getUsername());
        log.info(user.getEmail());
        return user;
    }
    
    
}