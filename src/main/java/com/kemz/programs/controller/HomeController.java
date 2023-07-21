package com.kemz.programs.controller;

import com.kemz.programs.dto.HomeDto;
import com.kemz.programs.model.Message;
import com.kemz.programs.model.Program;
import com.kemz.programs.service.DetailService;
import com.kemz.programs.service.HomeService;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Log4j2
@Controller
@RequestMapping("home")
@SessionAttributes("home_dto")
public class HomeController {

    private final HomeService homeService;
    private final DetailService detailService;

    @Autowired
    public HomeController(HomeService homeService, DetailService detailService) {
        this.detailService = detailService;
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

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("programs/{detail_id}")
    public String getPrograms(@PathVariable(name = "detail_id") Long detailId, @ModelAttribute(name = "home_dto") HomeDto homeDto){
        homeDto.setDetailActive(detailId);
        homeDto.setPrograms(homeService.getPrograms(detailId));
        homeDto.setTools(new ArrayList<>());
        return "redirect:/home";
    }

    @GetMapping("tools/{program_id}")
    public String getTools(@PathVariable(name = "program_id") Long programId, @ModelAttribute(name = "home_dto")  HomeDto homeDto) throws NotFoundException {
        homeDto.setTools(homeService.getTools(programId));
        return "redirect:/home";
    }

    @GetMapping("addProgramPage")
    public String addPage(@ModelAttribute("home_dto") HomeDto homeDto, @ModelAttribute("program") Program program){
        program.setDetailId(homeDto.getDetailActive());
        program.setUserId(1L);
        return "addProgramPage";
    }

    @GetMapping(value = "down/program/{program_id}")
    @ResponseBody
    public String downloadProgram(@PathVariable(name = "program_id") Long programId) throws IOException {
        return homeService.getProgram(programId);
    }

    @PostMapping("/search")
    public String search(@RequestParam("text") String text, @ModelAttribute("home_dto") HomeDto homeDto ){
        homeDto.setDetails(homeService.getDetailBySearch(text));
        return "redirect:/home";
    }


    @PostMapping("addDetail")
    public String addDetail(String cipher, String name, @ModelAttribute("home_dto") HomeDto homeDto){
        detailService.add(cipher, name);
        homeDto.setDetails(homeService.getAllDetail());
        return "redirect:/home";
    }

    @PostMapping("/addImg")
    public String addImg(@ModelAttribute("img") MultipartFile file){

        return "redirect:/home";
    }
}
