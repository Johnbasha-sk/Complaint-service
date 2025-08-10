package com.municipal.user.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }
        if (token != null) {
            try {
                Claims claims = jwtService.parse(token);
                Long userId = claims.get("userId", Integer.class).longValue();
                List<String> roles = new ArrayList<>();
                Object r = claims.get("roles");
                if (r instanceof List<?>) {
                    roles = ((List<?>) r).stream().map(Object::toString).collect(Collectors.toList());
                } else if (r instanceof String[]) {
                    roles = Arrays.asList((String[]) r);
                }
                List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
                var auth = new UserAuthenticationToken(userId, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) { }
        }
        filterChain.doFilter(request, response);
    }

    static class UserAuthenticationToken extends AbstractAuthenticationToken {
        private final Long userId;
        public UserAuthenticationToken(Long userId, List<GrantedAuthority> authorities) {
            super(authorities);
            this.userId = userId;
            setAuthenticated(true);
        }
        @Override
        public Object getCredentials() { return ""; }
        @Override
        public Object getPrincipal() { return userId; }
    }
}