package com.kemz.programs.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private String type;

    @ManyToMany
    @JoinTable(name = "toolProgram",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private Set<Tool> tools;

}
