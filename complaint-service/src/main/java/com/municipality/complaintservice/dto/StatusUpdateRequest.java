package com.municipality.complaintservice.dto;

import com.municipality.complaintservice.entity.ComplaintStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private ComplaintStatus status;

    // Default constructor
    public StatusUpdateRequest() {
    }

    // Constructor with all fields
    public StatusUpdateRequest(ComplaintStatus status) {
        this.status = status;
    }

    // Getters and Setters
    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StatusUpdateRequest{" +
                "status=" + status +
                '}';
    }
}