package homework.web;

import homework.domain.model.User;
import homework.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String register(User user) {
        Optional<User> dbUser = userService.findByUsername(user.getLogin());
        if (dbUser.isEmpty()) {
            userService.save(user);
            return "redirect:login";
        } else {
            return "redirect:register?error";
        }
    }
}
