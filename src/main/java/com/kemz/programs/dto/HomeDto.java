package com.kemz.programs.dto;

import com.kemz.programs.model.Detail;
import com.kemz.programs.model.Program;
import com.kemz.programs.model.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeDto {


    private List<Detail> details;

    private List<Program> programs;

    private List<Tool> tools;

}
