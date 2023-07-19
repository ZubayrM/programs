package com.kemz.programs.controller;

import com.kemz.programs.dto.HomeDto;
import com.kemz.programs.model.Detail;
import com.kemz.programs.model.Message;
import com.kemz.programs.model.Program;
import com.kemz.programs.model.Tool;
import com.kemz.programs.service.HomeService;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.Attribute;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;

@Log4j2
@Controller
@RequestMapping("home")
@SessionAttributes("home_dto")
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

    @ModelAttribute("home_dto")
    public HomeDto homeDto(){
        return HomeDto.builder().details(homeService.getAllDetail()).build();
    }

//    @ModelAttribute("details")
//    public List<Detail> details(){
//        return homeService.getAllDetail();
//    }
//
//    @ModelAttribute("programs")
//    public List<Program> programs(){
//        return new ArrayList<>();
//    }
//
//    @ModelAttribute("tools")
//    public List<Tool> tools(){
//        return new ArrayList<>();
//    }

    @GetMapping
    public String home(@ModelAttribute(name = "home_dto") HomeDto homeDto){
        homeDto.setDetails(homeService.getAllDetail());
        return "home";
    }

    @GetMapping("programs/{detail_id}")
    public String getPrograms(@PathVariable(name = "detail_id") Long detailId, @ModelAttribute(name = "home_dto") HomeDto homeDto){
        //model.addAttribute("programs", homeService.getPrograms(detailId));
        homeDto.setPrograms(homeService.getPrograms(detailId));
        homeDto.setTools(new ArrayList<>());
        return "redirect:/home";
    }

    @GetMapping("tools/{program_id}")
    public String getTools(@PathVariable(name = "program_id") Long programId, @ModelAttribute(name = "home_dto")  HomeDto homeDto) throws NotFoundException {
//        model.addAttribute("tools",homeService.getTools(programId));
        homeDto.setTools(homeService.getTools(programId));
        return "redirect:/home";
    }

    @GetMapping(value = "down/program/{program_id}")
    @ResponseBody
    public String downloadProgram(@PathVariable(name = "program_id") Long programId) throws IOException {
        return homeService.getProgram(programId);
    }

}
