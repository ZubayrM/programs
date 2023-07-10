package com.kemz.programs.controller;

import com.kemz.programs.model.Program;
import com.kemz.programs.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("program")
public class ProgramController {

    private final ProgramService programService;


    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @ModelAttribute
    public Program program(){
        return new Program();
    }

    @PostMapping("add")
    public String add(Program program){
        programService.save(program);
        return "redirect:/home";
    };
}
