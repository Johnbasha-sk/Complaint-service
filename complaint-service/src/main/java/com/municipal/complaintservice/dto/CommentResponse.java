package com.municipal.complaintservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CommentResponse {
    private Long commentId;
    private Long complaintId;
    private String userId;
    private String role;
    private String commentText;
    private Instant timestamp;
}