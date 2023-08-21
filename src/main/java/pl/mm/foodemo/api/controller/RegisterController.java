package pl.mm.foodemo.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.UserRegisterDTO;
import pl.mm.foodemo.business.service.UserService;

import java.util.Map;

@Controller
@Validated
@AllArgsConstructor
public class RegisterController {

    public static final String REGISTER = "/register";

    private final UserService userService;

    @GetMapping(value = REGISTER)
    public ModelAndView registerPage() {
        return new ModelAndView("register", Map.of("userRegisterDTO", UserRegisterDTO.builder().build()));
    }


    @PostMapping(value = REGISTER)
    public String register(
            @Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
        BindingResult result
    ) {

        if(result.hasErrors()) {
            return "error";
        }

        userService.registerUser(userRegisterDTO);

        return "redirect:/login";
    }
}
