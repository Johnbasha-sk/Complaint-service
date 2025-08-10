package com.municipality.complaintservice.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentAssignRequest {

    @NotBlank(message = "Department is required")
    private String assignedDepartment;

    // Default constructor
    public DepartmentAssignRequest() {
    }

    // Constructor with all fields
    public DepartmentAssignRequest(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    // Getters and Setters
    public String getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    @Override
    public String toString() {
        return "DepartmentAssignRequest{" +
                "assignedDepartment='" + assignedDepartment + '\'' +
                '}';
    }
}