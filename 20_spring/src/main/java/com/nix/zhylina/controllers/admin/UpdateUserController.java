package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.services.hibernateService.IRoleService;
import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
import static com.nix.zhylina.utils.Constants.CONST_ERROR;
import static com.nix.zhylina.utils.Constants.CONST_ID;
import static com.nix.zhylina.utils.Constants.CONST_ID_ROLE;
import static com.nix.zhylina.utils.Constants.CONST_OBJECT_TO_EDIT;
import static com.nix.zhylina.utils.ErrorMessagesConstants.ERROR_EMAIL_IS_EXIST;

@Controller
@RequestMapping("/admin")
public class UpdateUserController {
    private final IUserService hibernateUserDao;
    private final IRoleService hibernateRoleDao;
    private final PasswordEncoder passwordEncoder;
    private UserEntity userEntity = null;

    @Autowired
    public UpdateUserController(IUserService hibernateUserDao, IRoleService hibernateRoleDao, PasswordEncoder passwordEncoder) {
        this.hibernateUserDao = hibernateUserDao;
        this.hibernateRoleDao = hibernateRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/edit-user")
    public String getUserPage(HttpServletRequest request, Model model) {
        userEntity = hibernateUserDao.findById(Long.parseLong(request.getParameter(CONST_ID))).get();
        model.addAttribute(CONST_ALL_ROLES, hibernateRoleDao.findAll());
        model.addAttribute(CONST_OBJECT_TO_EDIT, userEntity);

        return "/admin/UpdateUser";
    }

    @PostMapping("/edit-user")
    public String updateUserPage(HttpServletRequest request, @Valid @ModelAttribute("user") UserEntity user, BindingResult result, ModelMap model) {
        model.addAttribute(CONST_ALL_ROLES, hibernateRoleDao.findAll());
        model.addAttribute(CONST_OBJECT_TO_EDIT, userEntity);

        if (result.hasErrors()) {
            model.addAttribute(CONST_ERROR, result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));

            return "admin/UpdateUser";
        } else {
            if (Validator.isValidateUpdatePassword(user.getPassword(), userEntity.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else if (Validator.isValidateUserToUpdate(hibernateUserDao, user)) {
                model.addAttribute(CONST_ERROR, ERROR_EMAIL_IS_EXIST);

                return "admin/UpdateUser";
            }

            user.setId(userEntity.getId());
            user.setLogin(userEntity.getLogin());
            user.setRoleEntity(hibernateRoleDao.findById(Long.parseLong(request.getParameter(CONST_ID_ROLE))).get());
            hibernateUserDao.update(user);
            userEntity = null;

            return "redirect:/admin";

        }
    }
}
