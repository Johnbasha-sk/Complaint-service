package com.municipal.complaint.dto;

import com.municipal.complaint.model.ComplaintStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {

    @NotNull
    private ComplaintStatus status;

    public UpdateStatusRequest() {}

    public UpdateStatusRequest(ComplaintStatus status) {
        this.status = status;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }
}