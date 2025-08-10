package com.municipality.complaintservice.dto;

import com.municipality.complaintservice.entity.ComplaintCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComplaintRequest {

    @NotNull(message = "Category is required")
    private ComplaintCategory category;

    @NotBlank(message = "Description is required")
    private String description;

    // Default constructor
    public ComplaintRequest() {
    }

    // Constructor with all fields
    public ComplaintRequest(ComplaintCategory category, String description) {
        this.category = category;
        this.description = description;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "ComplaintRequest{" +
                "category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}