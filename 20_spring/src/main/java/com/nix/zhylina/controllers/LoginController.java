package com.nix.zhylina.controllers;

import com.nix.zhylina.utils.Constants;
import com.nix.zhylina.utils.ErrorMessagesConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @PostMapping("/login")
    public String homeUser(){
        return "user/UserPage";
    }

    @GetMapping("/login")
    public String homePage(ModelMap model, @RequestParam(required = false) boolean error) {
        if (error) {
            model.addAttribute(Constants.CONST_ERROR, ErrorMessagesConstants.ERROR_LOGIN);
        }
        return "LoginPage";
    }
}
