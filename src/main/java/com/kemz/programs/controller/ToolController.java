package com.kemz.programs.controller;

import com.kemz.programs.Repo.ProgramRepo;
import com.kemz.programs.Repo.ToolRepo;
import com.kemz.programs.dto.HomeDto;
import com.kemz.programs.dto.ToolPageDto;
import com.kemz.programs.model.Program;
import com.kemz.programs.model.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintException;
import java.util.ArrayList;

@Controller
@RequestMapping("tool")
@SessionAttributes({"tool_dto", "home_dto"})
@RequiredArgsConstructor
public class ToolController {

    private final ToolRepo toolRepo;
    private final ProgramRepo programRepo;


    @ModelAttribute("tool_dto")
    public ToolPageDto toolDto(){
        return ToolPageDto.builder()
                .toolActive(Tool.builder().name("").cipher("").build())
                .toolList(toolRepo.findAll())
                .newPack(new ArrayList<>())
                .build();
    };



    @GetMapping("addPage")
    public String addToolPage(@ModelAttribute("home_dto")HomeDto homeDto){
        return "addToolPage";
    }

    @GetMapping("{id}")
    public String getTool(@PathVariable Long id, @ModelAttribute("tool_dto") ToolPageDto toolPageDto) {
        toolPageDto.setToolActive(toolRepo.findById(id).orElseThrow());
        toolPageDto.getNewPack().add(toolRepo.findById(id).orElseThrow());
        return "redirect:/tool/addPage";
    }

    @PostMapping
    public String add(@ModelAttribute("tool_dto") ToolPageDto toolPageDto, @RequestParam("program_id") Long programId) {
        Program program = programRepo.findById(programId).orElseThrow();
        program.getTools().addAll(toolPageDto.getNewPack());
        programRepo.save(program);
        toolPageDto.setNewPack(new ArrayList<>());
        return "redirect:/home/tools/" + programId;
    }

    public String print(){
        //todo когда нибудь я доделаю этот метот
        return "redirect:/home";
    }

}
