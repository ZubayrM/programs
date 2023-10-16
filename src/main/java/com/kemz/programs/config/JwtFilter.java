package com.kemz.programs.config;

import com.kemz.programs.service.UserService;
import com.kemz.programs.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
//@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("мой фильтр заработал");

        try {

            String token = request.getHeader("Authorization");

            log.info(String.format("Получили из header: %s" ,token));


            SecurityContextHolder
                    .getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                            jwtTokenUtils.getUserName(token),
                            null,
                            jwtTokenUtils.getRoles(token).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    ));
        } catch (Exception e){
            log.debug("Что то пошло не так в фильтре");
        }

        filterChain.doFilter(request,response);

    }
}
