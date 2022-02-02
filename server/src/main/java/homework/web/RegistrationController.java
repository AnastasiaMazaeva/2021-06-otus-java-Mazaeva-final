package homework.web;

import homework.domain.model.User;
import homework.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/register")
    public ModelAndView viewRegisterPage(ModelAndView view) {
        User user = new User();
        ModelAndView model = view == null ? new ModelAndView("register") : view;
        model.addObject("user", user);
        return model;
    }

    @PostMapping("/register")
    public String register(User user, RedirectAttributes attributes) {
        Optional<User> dbUser = userService.findByUsername(user.getLogin());
        if (dbUser.isEmpty()) {
            userService.save(user);
            return "redirect:login";
        } else {
            attributes.addFlashAttribute("message", "Choose unique username!");
            return "redirect:register?error";
        }
    }
}
