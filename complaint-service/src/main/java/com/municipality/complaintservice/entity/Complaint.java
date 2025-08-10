package com.municipality.complaintservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private ComplaintCategory category;

    @NotBlank
    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status = ComplaintStatus.PENDING;

    @Column(name = "assigned_department")
    private String assignedDepartment;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // Default constructor
    public Complaint() {
        this.createdDate = LocalDateTime.now();
        this.status = ComplaintStatus.PENDING;
    }

    // Constructor with required fields
    public Complaint(ComplaintCategory category, String description, Long createdBy) {
        this();
        this.category = category;
        this.description = description;
        this.createdBy = createdBy;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", assignedDepartment='" + assignedDepartment + '\'' +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                '}';
    }
}