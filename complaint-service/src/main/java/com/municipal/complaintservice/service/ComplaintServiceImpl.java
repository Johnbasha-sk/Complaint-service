package com.municipal.complaintservice.service;

import com.municipal.complaintservice.dto.*;
import com.municipal.complaintservice.model.Comment;
import com.municipal.complaintservice.model.Complaint;
import com.municipal.complaintservice.model.Status;
import com.municipal.complaintservice.repository.CommentRepository;
import com.municipal.complaintservice.repository.ComplaintRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CommentRepository commentRepository;

    @Override
    public ComplaintResponse fileComplaint(CreateComplaintRequest request, String createdBy) {
        Complaint complaint = Complaint.builder()
            .category(request.getCategory())
            .description(request.getDescription())
            .status(Status.PENDING)
            .assignedDepartment(null)
            .createdBy(createdBy)
            .build();
        Complaint saved = complaintRepository.save(complaint);
        return mapToResponse(saved, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponse> getComplaintsForUser(String userId) {
        return complaintRepository.findByCreatedBy(userId).stream()
            .map(c -> mapToResponse(c, true))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll().stream()
            .map(c -> mapToResponse(c, true))
            .collect(Collectors.toList());
    }

    @Override
    public ComplaintResponse assignDepartment(Long complaintId, String department) {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new EntityNotFoundException("Complaint not found"));
        complaint.setAssignedDepartment(department);
        return mapToResponse(complaint, true);
    }

    @Override
    public ComplaintResponse updateStatus(Long complaintId, String statusUpdatedByRole, Status status) {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new EntityNotFoundException("Complaint not found"));
        complaint.setStatus(status);
        return mapToResponse(complaint, true);
    }

    @Override
    public CommentResponse addComment(Long complaintId, String userId, String userRole, String text, boolean restrictCitizenToOwnComplaint) {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new EntityNotFoundException("Complaint not found"));

        if (restrictCitizenToOwnComplaint && !complaint.getCreatedBy().equals(userId)) {
            throw new SecurityException("Citizens can only comment on their own complaints");
        }

        Comment comment = Comment.builder()
            .complaint(complaint)
            .userId(userId)
            .role(userRole)
            .commentText(text)
            .build();
        Comment saved = commentRepository.save(comment);
        return CommentResponse.builder()
            .commentId(saved.getCommentId())
            .complaintId(complaint.getId())
            .userId(saved.getUserId())
            .role(saved.getRole())
            .commentText(saved.getCommentText())
            .timestamp(saved.getTimestamp())
            .build();
    }

    private ComplaintResponse mapToResponse(Complaint complaint, boolean includeComments) {
        return ComplaintResponse.builder()
            .id(complaint.getId())
            .category(complaint.getCategory())
            .description(complaint.getDescription())
            .status(complaint.getStatus())
            .assignedDepartment(complaint.getAssignedDepartment())
            .createdBy(complaint.getCreatedBy())
            .createdDate(complaint.getCreatedDate())
            .comments(includeComments && complaint.getComments() != null ? complaint.getComments().stream()
                .map(cm -> CommentResponse.builder()
                    .commentId(cm.getCommentId())
                    .complaintId(complaint.getId())
                    .userId(cm.getUserId())
                    .role(cm.getRole())
                    .commentText(cm.getCommentText())
                    .timestamp(cm.getTimestamp())
                    .build())
                .collect(Collectors.toList()) : List.of())
            .build();
    }
}