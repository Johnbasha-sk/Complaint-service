package com.municipality.complaintservice.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {

    @NotBlank(message = "Comment text is required")
    private String commentText;

    // Default constructor
    public CommentRequest() {
    }

    // Constructor with all fields
    public CommentRequest(String commentText) {
        this.commentText = commentText;
    }

    // Getters and Setters
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "CommentRequest{" +
                "commentText='" + commentText + '\'' +
                '}';
    }
}