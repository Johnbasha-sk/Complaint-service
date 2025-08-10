package com.municipal.complaint.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserServiceClient userServiceClient;

    public JwtAuthenticationFilter(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("jwt".equals(c.getName()) && StringUtils.hasText(c.getValue())) {
                        authHeader = "Bearer " + c.getValue();
                        break;
                    }
                }
            }
        }
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            try {
                UserDetailsResponse userDetails = userServiceClient.validateTokenAndGetUser(authHeader);
                if (userDetails != null && userDetails.getUserId() != null) {
                    List<String> roles = userDetails.getRoles() != null ? userDetails.getRoles() : List.of();
                    List<GrantedAuthority> authorities = roles
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    Authentication authentication = new UserAuthenticationToken(userDetails.getUserId(), authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                SecurityContextHolder.clearContext();
            }
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
        public Object getCredentials() {
            return "";
        }

        @Override
        public Object getPrincipal() {
            return userId;
        }

        public Long getUserId() {
            return userId;
        }
    }
}