package com.apple.shoppingmallboot.member;

import com.apple.shoppingmallboot.config.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwtCookie = "";
        for( int i = 0; i < cookies.length; i++){
            if (cookies[i].getName().equals("jwt")) {
                jwtCookie = cookies[i].getValue();
            }
        }
        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e){
            filterChain.doFilter(request, response);
            return;
        }
        var arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr)
                .map(a -> new SimpleGrantedAuthority(a)).toList();

        var customUser = new CustomUser(
                claim.get("username").toString(),
                "none",
                authorities
        );
        customUser.setDisplayName(claim.get("displayName").toString());


        var authToken = new UsernamePasswordAuthenticationToken(
                customUser, ""
        );
        authToken.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);




        filterChain.doFilter(request, response);
    }
}
