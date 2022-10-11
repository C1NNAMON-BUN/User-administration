package com.nix.zhylina.controllers;

import com.nix.zhylina.services.hibernateService.IRoleService;
import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.services.ICaptchaService;
import com.nix.zhylina.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

import static com.nix.zhylina.utils.Constants.CONST_ERROR;
import static com.nix.zhylina.utils.Constants.CONST_G_RECAPTCHA_RESPONSE;
import static com.nix.zhylina.utils.Constants.CONST_PASSWORD;
import static com.nix.zhylina.utils.Constants.CONST_PASSWORD_SECOND;
import static com.nix.zhylina.utils.ErrorMessagesConstants.ERROR_PASS_CAPTCHA;

@Controller
public class RegistrationController {
    private final IUserService hibernateUserDao;
    private final ICaptchaService captchaService;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService hibernateRoleDao;

    @Autowired
    public RegistrationController(IUserService hibernateUserDao, IRoleService hibernateRoleDao, PasswordEncoder passwordEncoder, ICaptchaService captchaService) {
        this.hibernateUserDao = hibernateUserDao;
        this.captchaService = captchaService;
        this.passwordEncoder = passwordEncoder;
        this.hibernateRoleDao = hibernateRoleDao;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistration() {
        UserEntity userEntity = new UserEntity();
        userEntity.setRoleEntity(hibernateRoleDao.findByName("User").get());

        return new ModelAndView("RegistrationPage", "user", userEntity);
    }

    @PostMapping("/registration")
    public String postRegistration(HttpServletRequest request, @Valid @ModelAttribute("user") UserEntity user,
                                   BindingResult result, ModelMap model) {
        String validUser = Validator.validateAddNewUser(hibernateUserDao, user);
        String validPasswords = Validator.validateAddPassword(request.getParameter(CONST_PASSWORD),
                request.getParameter(CONST_PASSWORD_SECOND));

        if (!validUser.isEmpty()) {
            model.addAttribute(CONST_ERROR, validUser);

            return "RegistrationPage";
        } else if (!validPasswords.isEmpty()) {
            return "RegistrationPage";
        }

        if (result.hasErrors()) {
            model.addAttribute(CONST_ERROR, result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));

            return "RegistrationPage";
        } else if (Boolean.TRUE.equals(captchaService.isProcessResponse(request.getParameter(CONST_G_RECAPTCHA_RESPONSE)))) {
            user.setRoleEntity(hibernateRoleDao.findByName("User").get());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            hibernateUserDao.create(user);

            return "LoginPage";
        } else {
            model.addAttribute(CONST_ERROR, ERROR_PASS_CAPTCHA);

            return "RegistrationPage";
        }
    }
}
