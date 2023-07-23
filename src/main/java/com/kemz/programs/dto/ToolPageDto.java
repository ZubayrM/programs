package com.kemz.programs.dto;

import com.kemz.programs.model.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolPageDto {

    private Tool toolActive;

    private List<Tool> toolList;
}
