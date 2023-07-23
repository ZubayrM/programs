package com.kemz.programs.Repo;

import com.kemz.programs.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {

    Optional<Image> findByProgramId(Long programId);
}
