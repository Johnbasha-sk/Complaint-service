package com.municipal.complaintservice.dto;

import com.municipal.complaintservice.model.Category;
import com.municipal.complaintservice.model.Status;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ComplaintResponse {
    private Long id;
    private Category category;
    private String description;
    private Status status;
    private String assignedDepartment;
    private String createdBy;
    private Instant createdDate;
    private List<CommentResponse> comments;
}