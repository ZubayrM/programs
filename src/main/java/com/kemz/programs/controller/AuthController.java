package com.kemz.programs.controller;

import com.kemz.programs.service.UserService;
import com.kemz.programs.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @GetMapping
    public String page(){
        return "authPage";
    }

    @PostMapping
    public String auth(String name, String pass) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(name, pass));
            UserDetails userDetails = userService.loadUserByUsername(name);
            String token = jwtTokenUtils.generateToken(userDetails);
            log.info(String.format("Token создан: %s", token));
        } catch (BadCredentialsException e) {
            log.info(e.getMessage());
            return "redirect:/auth";
        }
        return "redirect:/home";
    }




}
