package com.kemz.programs.Repo;

import com.kemz.programs.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepo extends JpaRepository<Program, Long> {

    List<Program> findByDetailId(Long id);
}
