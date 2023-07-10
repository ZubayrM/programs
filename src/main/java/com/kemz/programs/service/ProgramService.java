package com.kemz.programs.service;

import com.kemz.programs.Repo.ProgramRepo;
import com.kemz.programs.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramService {

    private final ProgramRepo programRepo;

    @Autowired
    public ProgramService(ProgramRepo programRepo) {
        this.programRepo = programRepo;
    }

    public void save(Program program) {
        programRepo.save(program);
    }
}
