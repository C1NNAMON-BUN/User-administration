package com.nix.zhylina.controllers;

import com.nix.zhylina.utils.SqlQueriesConstants;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectRolesController {
    @GetMapping("/mainPage")
    public String redirectRoles(Authentication authentication) {
        if(authentication.getAuthorities().toString().equals(SqlQueriesConstants.AUTHORITIES_ROLE_USER)) {
            return "redirect:/user";
        }else if(authentication.getAuthorities().toString().equals(SqlQueriesConstants.AUTHORITIES_ROLE_ADMIN)){
            return "redirect:/admin";
        } else {
            return "/Logout";
        }
    }
}
