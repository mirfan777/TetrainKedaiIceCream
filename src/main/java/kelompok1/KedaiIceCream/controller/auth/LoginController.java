package kelompok1.KedaiIceCream.controller.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kelompok1.KedaiIceCream.model.entity.User;
import kelompok1.KedaiIceCream.model.model.LoginUser;
import kelompok1.KedaiIceCream.model.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/login")
public class LoginController {
    @Autowired
    private AuthService userService;

    @GetMapping
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("pages/auth/login");
        return modelAndView;
    }

    @PostMapping
    public RedirectView login(@ModelAttribute LoginUser user , HttpServletRequest request, HttpServletResponse response , RedirectAttributes redirectAttrs) throws IOException {
        User authUser = userService.authenticate(user);

        HttpSession session = request.getSession();
        
        if (authUser != null) {
            log.info("authentikasi berhasil");
            session.setAttribute("user", authUser);
            return new RedirectView("/");
        }else {
            log.info("authentikasi gagal");
            redirectAttrs.addFlashAttribute("error_message", "Password or username is incorrect");
            return new RedirectView("/auth/login");
        }
    }
}
