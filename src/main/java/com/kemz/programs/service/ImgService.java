package com.kemz.programs.service;

import com.kemz.programs.Repo.ImageRepo;
import com.kemz.programs.model.Image;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgService {

    private final ImageRepo imageRepo;

    @Autowired
    public ImgService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public Image get(Long id) throws NotFoundException {
        return imageRepo.findById(id).orElseThrow(()-> new NotFoundException("нету"));
    }
}
