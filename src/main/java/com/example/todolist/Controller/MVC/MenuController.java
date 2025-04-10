package com.example.todolist.Controller.MVC;

import com.example.todolist.Entity.NotPersistent.Menu;
import com.example.todolist.Entity.Roles;
import com.example.todolist.Service.RolesService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.GrantedAuthority;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {

    private final RolesService rolesService;
    private Menu menu;

    XmlMapper xmlMapper = new XmlMapper();

    @Value("${menu.file.path}")
    private String menuFilePath;

    public MenuController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @PostConstruct
    private void init() {
        try {
            File file = new File(menuFilePath);
            if (file.exists()) {
                this.menu = xmlMapper.readValue(file, Menu.class);
            } else if (file.createNewFile()) {
                Menu home = new Menu("Home", "/home", "fa fa-home", null, rolesService.getAllRoles());
                Menu logout = new Menu("Logout", "/logout", "fa fa-sign-out", null, rolesService.getAllRoles());
                this.menu = new Menu("Tasker", null, null, new ArrayList<>(List.of(home, logout)), rolesService.getAllRoles());
                xmlMapper.writeValue(file, this.menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Menu filterMenuByRole(Menu menu, List<String> userRoles) {
        if (menu.getRoles() != null && !menu.getRoles().isEmpty()) {
            boolean hasAccess = menu.getRoles().stream()
                    .map(Roles::getName)
                    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                    .anyMatch(userRoles::contains);

            if (!hasAccess) return null;
        }

        if (menu.getSubMenu() != null) {
            List<Menu> filteredSubMenu = new ArrayList<>();
            for (Menu subMenu : menu.getSubMenu()) {
                Menu filtered = filterMenuByRole(subMenu, userRoles);
                if (filtered != null) {
                    filteredSubMenu.add(filtered);
                }
            }
            menu.setSubMenu(filteredSubMenu);
        }

        return menu;
    }


    @ModelAttribute("menu")
    public Menu getMenu() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).anyMatch("ROLE_ANONYMOUS"::equals)) {
            return new Menu();
        }
        List<String> userRoles = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Menu clonedMenu = new Menu(
                menu.getTitle(), menu.getUrl(), menu.getIcon(),
                menu.getSubMenu(), menu.getRoles()
        );
        return filterMenuByRole(clonedMenu, userRoles);
    }

}
