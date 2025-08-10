package com.municipality.complaintservice.dto;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long commentId;
    private Long userId;
    private String role;
    private String commentText;
    private LocalDateTime timestamp;

    // Default constructor
    public CommentResponse() {
    }

    // Constructor with all fields
    public CommentResponse(Long commentId, Long userId, String role, String commentText, LocalDateTime timestamp) {
        this.commentId = commentId;
        this.userId = userId;
        this.role = role;
        this.commentText = commentText;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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
        return "CommentResponse{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                ", commentText='" + commentText + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}