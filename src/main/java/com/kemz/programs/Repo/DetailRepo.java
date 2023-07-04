package com.kemz.programs.Repo;

import com.kemz.programs.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepo extends JpaRepository<Detail, Long> {
}
