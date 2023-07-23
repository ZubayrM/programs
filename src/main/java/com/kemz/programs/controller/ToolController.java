package com.kemz.programs.controller;

import com.kemz.programs.Repo.ProgramRepo;
import com.kemz.programs.Repo.ToolRepo;
import com.kemz.programs.dto.HomeDto;
import com.kemz.programs.dto.ToolPageDto;
import com.kemz.programs.model.Program;
import com.kemz.programs.model.Tool;
import com.kemz.programs.service.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintException;

@Controller
@RequestMapping("tool")
@SessionAttributes({"tool_dto", "home_dto"})
public class ToolController {



    private final ToolRepo toolRepo;
    //private final Tool2ProgramRepo tool2ProgramRepo;
    private final ProgramRepo programRepo;


    @Autowired
    public ToolController(ToolRepo toolRepo,
                          //Tool2ProgramRepo tool2ProgramRepo,
                          ProgramRepo programRepo) throws PrintException {
        this.toolRepo = toolRepo;
        //this.tool2ProgramRepo = tool2ProgramRepo;
        this.programRepo = programRepo;



    }


    @ModelAttribute("tool_dto")
    public ToolPageDto toolDto(){
        return ToolPageDto.builder()
                .toolActive(Tool.builder().name("").cipher("").build())
                .toolList(toolRepo.findAll()).build();
    };



    @GetMapping("addPage")
    public String addToolPage(@ModelAttribute("home_dto")HomeDto homeDto){
        return "addToolPage";
    }

    @GetMapping("{id}")
    public String getTool(@PathVariable Long id, @ModelAttribute("tool_dto") ToolPageDto toolPageDto){
        toolPageDto.setToolActive(toolRepo.findById(id).orElseThrow());
        return "redirect:/tool/addPage";
    }

    @PostMapping
    public String add(@RequestParam("tool_id") Long toolId, @RequestParam("program_id") Long programId){

        Program program = programRepo.findById(programId).orElseThrow();
        program.getTools().add(toolRepo.findById(toolId).orElseThrow());
        programRepo.save(program);

//        tool2ProgramRepo.save(Tool2Program.builder()
//                .programId(programId)
//                .toolId(toolId)
//                .build());
        return "redirect:/home/tools/" + programId;
    }

    public String print(){


        return "redirect:/home";
    }

}
