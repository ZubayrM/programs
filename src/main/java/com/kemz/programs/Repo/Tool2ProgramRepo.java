package com.kemz.programs.Repo;

import com.kemz.programs.model.Tool2Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tool2ProgramRepo extends JpaRepository<Tool2Program, Long> {

}
