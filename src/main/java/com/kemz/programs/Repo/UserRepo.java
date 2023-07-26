package com.kemz.programs.Repo;

import com.kemz.programs.model.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Usr,Long> {
    Optional<Usr> findByName(String name);
}
