package com.kemz.programs.Repo;

import com.kemz.programs.model.Detail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRepo extends JpaRepository<Detail, Long> {

    Page<Detail> findByNameContainingIgnoreCaseOrCipherContainingIgnoreCase(String s1, String s2, Pageable p);
}
