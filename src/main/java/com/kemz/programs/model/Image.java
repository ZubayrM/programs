package com.kemz.programs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long imgId;

    @Lob
    private byte[] img;

    private String type;
}
