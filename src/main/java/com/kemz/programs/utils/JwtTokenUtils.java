package com.kemz.programs.utils;

import com.kemz.programs.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final UserService userService;

    @Value(value = "${jwt.secret}")
    private String secret;

    @Value(value = "${jwt.time}")
    private Duration duration;


    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap();
        List<String> list = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put("roles", list);
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + duration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserName(String token){
        return getClaims(token).getSubject();
    }

    public List<String> getRoles(String token){
        return getClaims(token).get("roles", List.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
