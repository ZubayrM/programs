package com.kemz.programs.service;

import com.kemz.programs.Repo.DetailRepo;
import com.kemz.programs.model.Detail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
public class DetailService {

    private final DetailRepo detailRepo;

    @Autowired
    public DetailService(DetailRepo detailRepo) {
        this.detailRepo = detailRepo;
    }

    public Long add(String cipher, String name) {

        try {
            detailRepo.findByCipher(cipher)
                    .orElseThrow(()-> new RuntimeException(String.format("Деталь с шифром %s уже сушествует", cipher)));
            return detailRepo.save(
                    Detail.builder()
                            .cipher(cipher)
                            .name(name)
                            .build()).getId();
        } catch (Exception e){
            log.info(e.getMessage());
            return 0L;
        }

    }
}
