package pl.mm.foodemo.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class LoginController {

    static final String LOGIN = "/login";
    static final String LOGIN_ERROR = "login-error";

    @GetMapping(LOGIN)
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping(LOGIN_ERROR)
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
