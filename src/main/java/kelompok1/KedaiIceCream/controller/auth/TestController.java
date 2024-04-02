package kelompok1.KedaiIceCream.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import kelompok1.KedaiIceCream.model.model.RegisterUser;
import kelompok1.KedaiIceCream.model.service.UserService;
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
    private UserService userService;

    @GetMapping
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("pages/landing");
        log.info("test");
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