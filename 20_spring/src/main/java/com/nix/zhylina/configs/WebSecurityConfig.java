package com.nix.zhylina.configs;

import com.nix.zhylina.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.nix.zhylina.utils.SpringConfigConstants.ACCESS_ROLE_ADMIN;
import static com.nix.zhylina.utils.SpringConfigConstants.ANT_MATCHER_ADMIN_PAGES;
import static com.nix.zhylina.utils.SpringConfigConstants.ANT_MATCHER_REGISTRATION_PAGE;
import static com.nix.zhylina.utils.SpringConfigConstants.ANT_MATCHER_ROLE_ADMIN;
import static com.nix.zhylina.utils.SpringConfigConstants.ANT_MATCHER_ROLE_USER;
import static com.nix.zhylina.utils.SpringConfigConstants.ANT_MATCHER_USER_PAGE;
import static com.nix.zhylina.utils.SpringConfigConstants.DEFAULT_SUCCESS_URL;
import static com.nix.zhylina.utils.SpringConfigConstants.LOGIN_PAGE;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ANT_MATCHER_USER_PAGE).hasAnyRole(ANT_MATCHER_ROLE_USER, ANT_MATCHER_ROLE_ADMIN)
                .antMatchers(ANT_MATCHER_ADMIN_PAGES).access(ACCESS_ROLE_ADMIN)
                .antMatchers(ANT_MATCHER_REGISTRATION_PAGE).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable().formLogin()
                .loginPage(LOGIN_PAGE).permitAll()
                .failureUrl("/login?error=true")
                .loginProcessingUrl(LOGIN_PAGE).defaultSuccessUrl(DEFAULT_SUCCESS_URL, true);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder());
    }

}
