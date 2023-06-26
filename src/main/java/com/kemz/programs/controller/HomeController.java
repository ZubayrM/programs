package com.kemz.programs.controller;

import com.kemz.programs.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class HomeController {

    @ModelAttribute("msg")
    public Message getText(){
        return new Message();
    }

    @GetMapping
    public String home(){
        return "home";
    }

}
