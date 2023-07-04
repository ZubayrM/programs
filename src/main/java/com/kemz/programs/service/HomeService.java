package com.kemz.programs.service;

import com.kemz.programs.Repo.DetailRepo;
import com.kemz.programs.Repo.ProgramRepo;
import com.kemz.programs.Repo.Tool2ProgramRepo;
import com.kemz.programs.Repo.ToolRepo;
import com.kemz.programs.model.Detail;
import com.kemz.programs.model.Program;
import com.kemz.programs.model.Tool;
import com.kemz.programs.model.Tool2Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final DetailRepo detailRepo;
    private final ProgramRepo programRepo;
    private final ToolRepo toolRepo;
    private final Tool2ProgramRepo tool2ProgramRepo;

    @Autowired
    public HomeService(DetailRepo detailRepo, ProgramRepo programRepo, ToolRepo toolRepo, Tool2ProgramRepo tool2ProgramRepo) {
        this.detailRepo = detailRepo;
        this.programRepo = programRepo;
        this.toolRepo = toolRepo;
        this.tool2ProgramRepo = tool2ProgramRepo;
    }

    public List<Detail> getAllDetail() {
        return detailRepo.findAll();
    }

    public List<Program> getPrograms(Long detailId) {
        return programRepo.findByDetailId(detailId);
    }

    public List<Tool> getTools(Long programId) {

        return toolRepo.
    }
}
