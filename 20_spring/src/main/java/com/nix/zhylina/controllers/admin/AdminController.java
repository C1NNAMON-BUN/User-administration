package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.dto.ConverterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

import static com.nix.zhylina.utils.Constants.CONST_CURRENT_USER;
import static com.nix.zhylina.utils.Constants.CONST_USERSLIST;

@Controller
public class AdminController {
    private final IUserService hibernateUserDao;

    @Autowired
    public AdminController(IUserService hibernateUserDao) {
        this.hibernateUserDao = hibernateUserDao;
    }

    @GetMapping("/admin")
    public ModelAndView getAdminPage(Model model, Authentication authentication) {
        model.addAttribute(CONST_USERSLIST, hibernateUserDao.findAll().stream()
                .map(ConverterEntity::convertUserEntityToDto)
                .collect(Collectors.toList()));
        model.addAttribute(CONST_CURRENT_USER, hibernateUserDao.findByLogin(authentication.getName()).get());
        return new ModelAndView("/admin/AdminPage");
    }
}
