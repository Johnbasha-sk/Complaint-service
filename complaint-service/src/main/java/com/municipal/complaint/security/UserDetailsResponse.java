package com.municipal.complaint.security;

import java.util.List;

public class UserDetailsResponse {
    private Long userId;
    private List<String> roles;

    public UserDetailsResponse() {}

    public UserDetailsResponse(Long userId, List<String> roles) {
        this.userId = userId;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}