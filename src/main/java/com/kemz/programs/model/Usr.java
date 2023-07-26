package com.kemz.programs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Usr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String pass;

    @ManyToMany
    @JoinTable(name = "usr_role",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
