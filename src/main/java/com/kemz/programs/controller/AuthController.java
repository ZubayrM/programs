package com.kemz.programs.controller;

import com.kemz.programs.dto.AuthDto;
import com.kemz.programs.service.UserService;
import com.kemz.programs.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String auth(String name, String pass){
        String token = null;
        try {
            Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(name, pass));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
//            UserDetails userDetails = userService.loadUserByUsername(name);
//            token = jwtTokenUtils.generateToken(userDetails);
//            log.info(String.format("Token создан: %s", token));
//            response.addHeader("authorization", token);
        } catch (BadCredentialsException e) {
            log.info(e.getMessage());
            return "authPage";
        }
        return "redirect:/home";
    }




}
