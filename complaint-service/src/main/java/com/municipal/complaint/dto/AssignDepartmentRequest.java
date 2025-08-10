package com.municipal.complaint.dto;

import jakarta.validation.constraints.NotBlank;

public class AssignDepartmentRequest {

    @NotBlank
    private String assignedDepartment;

    public AssignDepartmentRequest() {}

    public AssignDepartmentRequest(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    public String getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }
}