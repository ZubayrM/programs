package com.kemz.programs.service;

import com.kemz.programs.Repo.DetailRepo;
import com.kemz.programs.Repo.ProgramRepo;
import com.kemz.programs.Repo.ToolRepo;
import com.kemz.programs.model.Detail;
import com.kemz.programs.model.Program;
import com.kemz.programs.model.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HomeService {

    private final DetailRepo detailRepo;
    private final ProgramRepo programRepo;
    private final ToolRepo toolRepo;

    @Autowired
    public HomeService(DetailRepo detailRepo, ProgramRepo programRepo, ToolRepo toolRepo) {
        this.detailRepo = detailRepo;
        this.programRepo = programRepo;
        this.toolRepo = toolRepo;
    }

    public List<Detail> getAllDetail() {
        return detailRepo.findAll();
    }

    public List<Program> getPrograms(Long detailId) {
        return programRepo.findByDetailId(detailId);
    }

    public List<Tool> getTools(Long programId) {
        ArrayList<Tool> tools =
                new ArrayList<>(programRepo
                        .findById(programId).orElseThrow()
                        .getTools());
        log.info("Список инструментов" + tools.size());
        return tools;
    }

    public Program getProgram(Long programId) {
        return programRepo.findById(programId).orElse(new Program());
    }

    public Page<Detail> getDetailBySearch(String text, Pageable pageable) {
        return detailRepo.findByNameContainingIgnoreCaseOrCipherContainingIgnoreCase(text, text, pageable);
    }

    public Page<Detail> getDetails(PageRequest of) {
        return detailRepo.findAll(of);
    }
}
