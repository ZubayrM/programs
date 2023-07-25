package com.kemz.programs.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProgramDto {

    private Long detailId;

    private Long userId;

    private String index;

    private MultipartFile code;

    private String type;
}
