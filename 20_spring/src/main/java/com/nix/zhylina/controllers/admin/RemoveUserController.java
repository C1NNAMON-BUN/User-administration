package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.dto.ConverterEntity;
import com.nix.zhylina.models.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

import static com.nix.zhylina.utils.Constants.CONST_ID;
import static com.nix.zhylina.utils.Constants.CONST_USERSLIST;

@Controller
public class RemoveUserController {
    private final IUserService hibernateUserDao;

    public RemoveUserController(IUserService hibernateUserDao) {
        this.hibernateUserDao = hibernateUserDao;
    }

    @GetMapping("/delete-user")
    public String postRegistration(HttpServletRequest request, ModelMap model, Authentication authentication) {
        UserEntity deleteUser = hibernateUserDao.findById(Long.parseLong(request.getParameter(CONST_ID))).orElse(null);

        hibernateUserDao.remove(deleteUser);
        model.addAttribute(CONST_USERSLIST, hibernateUserDao.findAll().stream()
                .map(ConverterEntity::convertUserEntityToDto)
                .collect(Collectors.toList()));
        if (authentication.getName().equals(deleteUser.getLogin())) {
            return "redirect:/logout";
        }

        return "redirect:/admin";
    }
}
