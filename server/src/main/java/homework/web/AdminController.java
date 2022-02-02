package homework.web;

import homework.domain.model.User;
import homework.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("")
    public ModelAndView getList(HttpServletRequest request) {
        List<User> users = userService.findAllExcept(request.getUserPrincipal().getName());
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @PostMapping("/user/{userId}/toggle")
    public ModelAndView toggleActive(@PathVariable("userId") Long userId, HttpServletRequest request) {
        userService.toggleActive(userId);
        List<User> users = userService.findAllExcept(request.getUserPrincipal().getName());
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("users", users);
        return modelAndView;
    }
}
