package com.example.todolist.Controller.MVC;

import com.example.todolist.Entity.NotPersistent.Menu;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class UiAdvice {
    private final MenuController menuController;

    public UiAdvice(MenuController menuController) {
        this.menuController = menuController;
    }

    @Controller
    static class CustomErrorController implements ErrorController {
        @RequestMapping("/error")
        public String handleError(HttpServletRequest request) {
            Object statusCode = request.getAttribute("javax.servlet.error.status_code");
            if (statusCode != null) {
                int code = Integer.parseInt(statusCode.toString());
                if (code == 404) {
                    return "redirect:/home";
                }
            }
            return "error";
        }
    }

    @ModelAttribute("menu")
    public Menu addMenu(Model model) {
        return menuController.getMenu();
    }

    @ModelAttribute("usr")
    public UserDetails addUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
            !authentication.getPrincipal().equals("anonymousUser")) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
