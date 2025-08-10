package com.municipality.complaintservice.dto;

import com.municipality.complaintservice.entity.ComplaintCategory;
import com.municipality.complaintservice.entity.ComplaintStatus;
import java.time.LocalDateTime;
import java.util.List;

public class ComplaintResponse {

    private Long id;
    private ComplaintCategory category;
    private String description;
    private ComplaintStatus status;
    private String assignedDepartment;
    private Long createdBy;
    private LocalDateTime createdDate;
    private List<CommentResponse> comments;

    // Default constructor
    public ComplaintResponse() {
    }

    // Constructor with all fields
    public ComplaintResponse(Long id, ComplaintCategory category, String description, 
                           ComplaintStatus status, String assignedDepartment, 
                           Long createdBy, LocalDateTime createdDate, 
                           List<CommentResponse> comments) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.status = status;
        this.assignedDepartment = assignedDepartment;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.comments = comments;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public String getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(String assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ComplaintResponse{" +
                "id=" + id +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", assignedDepartment='" + assignedDepartment + '\'' +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", comments=" + comments +
                '}';
    }
}