package com.kemz.programs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cipher;

    @ManyToMany(mappedBy = "tools")
    private Set<Program> programs;
}
