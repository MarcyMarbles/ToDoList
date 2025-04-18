package com.example.todolist.Controller.MVC;

import com.example.todolist.Entity.MenuElement;
import com.example.todolist.Entity.NotPersistent.Menu;
import com.example.todolist.Entity.Roles;
import com.example.todolist.Repos.MenuElementRepository;
import com.example.todolist.Service.RolesService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.GrantedAuthority;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class MenuController {
    @Autowired
    private MenuElementRepository menuElementRepository;

    @ModelAttribute("menu")
    public Set<MenuElement> menu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<Roles> roles = rolesService.getUserRoles(auth); // тебе нужен метод, возвращающий Set<Roles>
        return menuService.generateMenu(roles);
    }

}
