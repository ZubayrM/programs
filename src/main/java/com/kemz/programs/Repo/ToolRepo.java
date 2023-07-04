package com.kemz.programs.Repo;

import com.kemz.programs.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepo extends JpaRepository<Tool, Long> {

    List<Tool> findDistinctTool2ProgramByProgramId(Long programId);
}
