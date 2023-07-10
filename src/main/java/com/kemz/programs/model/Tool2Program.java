package com.kemz.programs.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Tool2Program {

    @Id
    private Long id;

    @Column(name = "tool_id")
    private Long toolId;

    @Column(name = "program_id")
    private Long programId;
}
