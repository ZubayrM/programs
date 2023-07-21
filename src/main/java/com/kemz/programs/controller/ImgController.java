package com.kemz.programs.controller;

import com.kemz.programs.model.Image;
import com.kemz.programs.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ImgController {

    private final ImgService imgService;

    @Autowired
    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }


    @GetMapping("img/{id}")
    @ResponseBody
    public ResponseEntity img(@PathVariable("id") Long id) throws IOException {
        Image image = imgService.get(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getType())).body(image.getImg());
    }

}
