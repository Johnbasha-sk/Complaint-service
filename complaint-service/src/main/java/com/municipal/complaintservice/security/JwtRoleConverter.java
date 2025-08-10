package com.municipal.complaintservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String rolesClaimName;

    public JwtRoleConverter(String rolesClaimName) {
        this.rolesClaimName = rolesClaimName == null || rolesClaimName.isBlank() ? "roles" : rolesClaimName;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object rolesObject = jwt.getClaim(rolesClaimName);
        if (rolesObject == null) {
            return Collections.emptyList();
        }

        if (rolesObject instanceof Collection<?> collection) {
            return collection.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        }

        // single string claim, comma-separated or space-separated
        String rolesString = rolesObject.toString();
        String[] roles = rolesString.split(",[\\s]*");
        return Set.of(roles).stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }
}