package com.kemz.programs.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detail_id")
    private Long detailId;

    @Column(name="user_id")
    private Long userId;

    private String index;
    
    private String code;

    @ManyToMany
    @JoinTable(name = "tool2Program",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private Set<Tool> tools;

}