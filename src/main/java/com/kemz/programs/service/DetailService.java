package com.kemz.programs.service;

import com.kemz.programs.Repo.DetailRepo;
import com.kemz.programs.model.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailService {

    private final DetailRepo detailRepo;

    @Autowired
    public DetailService(DetailRepo detailRepo) {
        this.detailRepo = detailRepo;
    }

    public Long add(String cipher, String name) {
        return detailRepo.save(
                Detail.builder()
                        .cipher(cipher)
                        .name(name)
                        .build()).getId();
    }
}
