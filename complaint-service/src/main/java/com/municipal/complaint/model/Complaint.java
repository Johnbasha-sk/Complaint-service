package com.municipal.complaint.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintCategory category;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status;

    @Column(name = "assigned_department")
    private String assignedDepartment;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    public Complaint() {
    }

    public Complaint(Long id, ComplaintCategory category, String description, ComplaintStatus status, String assignedDepartment, Long createdBy, Instant createdDate) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.status = status;
        this.assignedDepartment = assignedDepartment;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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