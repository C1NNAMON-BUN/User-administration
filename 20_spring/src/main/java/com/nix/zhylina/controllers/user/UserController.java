package com.nix.zhylina.controllers.user;

import com.nix.zhylina.dao.impl.HibernateUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static com.nix.zhylina.utils.Constants.CONST_CURRENT_USER;

@Controller
public class UserController {
    private final HibernateUserDao hibernateUserDao;

    @Autowired
    public UserController(HibernateUserDao hibernateUserDao) {
        this.hibernateUserDao = hibernateUserDao;
    }

    @GetMapping("/user")
    @ResponseBody
    public ModelAndView getUserPage(Authentication authentication, Model model) {
        model.addAttribute(CONST_CURRENT_USER, hibernateUserDao.findByLogin(authentication.getName()).get());

        return new ModelAndView("user/UserPage");
    }
}
