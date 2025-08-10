package com.municipal.complaintservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCommentRequest {
    @NotBlank
    private String commentText;
}