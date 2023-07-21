package com.kemz.programs.controller;

import com.kemz.programs.dto.HomeDto;
import com.kemz.programs.dto.ProgramDto;
import com.kemz.programs.model.Program;
import com.kemz.programs.service.ProgramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Slf4j
@Controller
@RequestMapping("program")
@SessionAttributes("program")
public class ProgramController {

    private final ProgramService programService;


    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @ModelAttribute("program")
    public ProgramDto program(){
        return new ProgramDto();
    }



    @PostMapping("add")
    public String add(@ModelAttribute("program") ProgramDto program) throws IOException {
        programService.save(Program.builder()
                .detailId(program.getDetailId())
                .index(program.getIndex())
                .code(new String(program.getCode(), StandardCharsets.UTF_8)).build());
        return "redirect:/home";
    };
}
