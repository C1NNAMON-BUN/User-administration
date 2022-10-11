package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.services.hibernateService.IRoleService;
import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

import static com.nix.zhylina.utils.Constants.CONST_ALL_ROLES;
import static com.nix.zhylina.utils.Constants.CONST_CURRENT_USER;
import static com.nix.zhylina.utils.Constants.CONST_ERROR;
import static com.nix.zhylina.utils.Constants.CONST_ID_ROLE;
import static com.nix.zhylina.utils.Constants.CONST_PASSWORD;
import static com.nix.zhylina.utils.Constants.CONST_PASSWORD_SECOND;

@Controller
@RequestMapping("/admin")
public class AddNewUserController {
    private final IUserService hibernateUserDao;
    private final IRoleService hibernateRoleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AddNewUserController(IUserService hibernateUserDao, IRoleService hibernateRoleDao, PasswordEncoder passwordEncoder) {
        this.hibernateUserDao = hibernateUserDao;
        this.hibernateRoleDao = hibernateRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/add-user")
    public String getAddUserPage(Model model, Authentication authentication) {
        model.addAttribute(CONST_ALL_ROLES, hibernateRoleDao.findAll());
        model.addAttribute(CONST_CURRENT_USER, hibernateUserDao.findByLogin(authentication.getName()).get());

        return "/admin/AddNewUser";
    }

    @PostMapping("/add-user")
    public String postAddUserPage(HttpServletRequest request, @Valid @ModelAttribute("user") UserEntity user,
                                  BindingResult result, ModelMap model) {
        model.addAttribute(CONST_ALL_ROLES, hibernateRoleDao.findAll());

        String validUser = Validator.validateAddNewUser(hibernateUserDao, user);
        String validPasswords = Validator.validateAddPassword(request.getParameter(CONST_PASSWORD),
                request.getParameter(CONST_PASSWORD_SECOND));
        if (!validUser.isEmpty()) {
            model.addAttribute(CONST_ERROR, validUser);

            return "admin/AddNewUser";
        } else if (!validPasswords.isEmpty()) {

            return "admin/AddNewUser";
        }
        if (result.hasErrors()) {
            model.addAttribute(CONST_ERROR, result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));

            return "admin/AddNewUser";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoleEntity(hibernateRoleDao.findById(Long.parseLong(request.getParameter(CONST_ID_ROLE))).get());
            hibernateUserDao.create(user);

            return "redirect:/admin";
        }
    }
}
