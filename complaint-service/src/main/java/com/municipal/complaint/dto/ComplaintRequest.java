package com.municipal.complaint.dto;

import com.municipal.complaint.model.ComplaintCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ComplaintRequest {

    @NotNull
    private ComplaintCategory category;

    @NotBlank
    @Size(min = 5, max = 1000)
    private String description;

    public ComplaintRequest() {}

    public ComplaintRequest(ComplaintCategory category, String description) {
        this.category = category;
        this.description = description;
    }

    public ComplaintCategory getCategory() {
        return category;
    }

    public void setCategory(ComplaintCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}