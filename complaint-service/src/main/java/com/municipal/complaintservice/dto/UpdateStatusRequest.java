package com.municipal.complaintservice.dto;

import com.municipal.complaintservice.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    @NotNull
    private Status status;
}