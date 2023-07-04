package com.kemz.programs.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tool2Program {

    @Id
    private Long id;

    private Long toolId;

    private Long programId;
}
