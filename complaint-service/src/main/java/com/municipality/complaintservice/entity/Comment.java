package com.municipality.complaintservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String role;

    @NotBlank
    @Column(name = "comment_text", nullable = false, length = 1000)
    private String commentText;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Default constructor
    public Comment() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with required fields
    public Comment(Complaint complaint, Long userId, String role, String commentText) {
        this();
        this.complaint = complaint;
        this.userId = userId;
        this.role = role;
        this.commentText = commentText;
    }

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                ", commentText='" + commentText + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}