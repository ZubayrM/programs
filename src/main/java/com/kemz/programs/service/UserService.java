package com.kemz.programs.service;

import com.kemz.programs.Repo.RoleRepo;
import com.kemz.programs.Repo.UserRepo;
import com.kemz.programs.model.Usr;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;


    private Usr getUserByName(String name){
        return userRepo.findByName(name).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("Пользователь с именем %s не найден", name)
                )
        );
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usr request = getUserByName(name);
        return new org.springframework.security.core.userdetails.User(
                request.getName(),
                request.getPass(),
                request.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
