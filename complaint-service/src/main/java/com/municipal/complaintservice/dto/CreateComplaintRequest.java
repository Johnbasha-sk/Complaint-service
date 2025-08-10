package com.municipal.complaintservice.dto;

import com.municipal.complaintservice.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateComplaintRequest {
    @NotNull
    private Category category;

    @NotBlank
    private String description;
}