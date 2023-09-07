package com.kemz.programs.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("code-fanuc")
    private MultipartFile codeFanuc;
    @JsonProperty("code-haas")
    private MultipartFile codeHaas;
    @JsonProperty("code-h")
    private MultipartFile codeH;

    private String type;
}
