package com.municipal.complaint.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "complaint_id", nullable = false)
    private Long complaintId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "comment_text", nullable = false, length = 2000)
    private String commentText;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public Comment() {
    }

    public Comment(Long commentId, Long complaintId, Long userId, String role, String commentText, Instant timestamp) {
        this.commentId = commentId;
        this.complaintId = complaintId;
        this.userId = userId;
        this.role = role;
        this.commentText = commentText;
        this.timestamp = timestamp;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
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

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", complaintId=" + complaintId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                ", commentText='" + commentText + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}