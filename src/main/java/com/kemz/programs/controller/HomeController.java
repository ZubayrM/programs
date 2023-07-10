package com.kemz.programs.controller;

import com.kemz.programs.model.Detail;
import com.kemz.programs.model.Message;
import com.kemz.programs.model.Program;
import com.kemz.programs.model.Tool;
import com.kemz.programs.service.HomeService;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("home")
@SessionAttributes("programs")
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        String[] a = new String[]{"1",""};
        this.homeService = homeService;
    }

    @ModelAttribute("msg")
    public Message getText(){
        return new Message();
    }

    @ModelAttribute("details")
    public List<Detail> details(){
        return homeService.getAllDetail();
    }

    @ModelAttribute("programs")
    public List<Program> programs(){
        return new ArrayList<>();
    }

    @ModelAttribute("tools")
    public List<Tool> tools(){
        return new ArrayList<>();
    }

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("programs/{detail_id}")
    public String getPrograms(@PathVariable(name = "detail_id") Long detailId, Model model){
        model.addAttribute("programs", homeService.getPrograms(detailId));

        return "redirect:/home";
    }

    @GetMapping("tools/{program_id}")
    public String getTools(@PathVariable(name = "program_id") Long programId, Model model) throws NotFoundException {
        model.addAttribute("tools",homeService.getTools(programId));
        return "redirect:/home";
    }

}
