package homework.web;

import homework.domain.model.User;
import homework.domain.projection.PermissionRepresentation;
import homework.service.interfaces.PermissionService;
import homework.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final PermissionService permissionService;

    @GetMapping("/")
    public ModelAndView viewHomePage(HttpServletRequest request) {
        String principal = request.getUserPrincipal().getName();
        User user = userService.findByUsername(principal).orElseThrow();
        ModelAndView model = new ModelAndView("home");
        List<User> users = userService.findAllExcept(principal);
        List<PermissionRepresentation> permissions = permissionService.findByUser(user.getId());
        model.addObject("documents", user.getDocuments());
        model.addObject("users", users);
        model.addObject("permissions", permissions);
        return model;
    }

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
