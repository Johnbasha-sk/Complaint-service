package com.municipality.complaintservice.service;

import com.municipality.complaintservice.dto.*;
import com.municipality.complaintservice.entity.Complaint;
import com.municipality.complaintservice.entity.ComplaintStatus;
import com.municipality.complaintservice.entity.Comment;
import com.municipality.complaintservice.repository.ComplaintRepository;
import com.municipality.complaintservice.repository.CommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CommentRepository commentRepository;

    public ComplaintService(ComplaintRepository complaintRepository, CommentRepository commentRepository) {
        this.complaintRepository = complaintRepository;
        this.commentRepository = commentRepository;
    }

    public ComplaintResponse fileComplaint(ComplaintRequest request) {
        Long userId = getCurrentUserId();
        
        Complaint complaint = new Complaint(request.getCategory(), request.getDescription(), userId);
        Complaint savedComplaint = complaintRepository.save(complaint);
        
        return convertToResponse(savedComplaint);
    }

    public List<ComplaintResponse> getComplaintsByUser(Long userId) {
        List<Complaint> complaints = complaintRepository.findByCreatedByOrderByCreatedDateDesc(userId);
        return complaints.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ComplaintResponse> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAllOrderByCreatedDateDesc();
        return complaints.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ComplaintResponse updateStatus(Long complaintId, StatusUpdateRequest request) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
        
        complaint.setStatus(request.getStatus());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        
        return convertToResponse(updatedComplaint);
    }

    public ComplaintResponse assignDepartment(Long complaintId, DepartmentAssignRequest request) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
        
        complaint.setAssignedDepartment(request.getAssignedDepartment());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        
        return convertToResponse(updatedComplaint);
    }

    public CommentResponse addComment(Long complaintId, CommentRequest request) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
        
        Long userId = getCurrentUserId();
        String role = getCurrentUserRole();
        
        Comment comment = new Comment(complaint, userId, role, request.getCommentText());
        Comment savedComment = commentRepository.save(comment);
        
        return convertToCommentResponse(savedComment);
    }

    public void deleteComplaint(Long complaintId) {
        if (!complaintRepository.existsById(complaintId)) {
            throw new RuntimeException("Complaint not found with id: " + complaintId);
        }
        complaintRepository.deleteById(complaintId);
    }

    public ComplaintResponse getComplaintById(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
        return convertToResponse(complaint);
    }

    public boolean isComplaintOwner(Long complaintId, Long userId) {
        return complaintRepository.findById(complaintId)
                .map(complaint -> complaint.getCreatedBy().equals(userId))
                .orElse(false);
    }

    private ComplaintResponse convertToResponse(Complaint complaint) {
        List<CommentResponse> comments = complaint.getComments().stream()
                .map(this::convertToCommentResponse)
                .collect(Collectors.toList());

        return new ComplaintResponse(
                complaint.getId(),
                complaint.getCategory(),
                complaint.getDescription(),
                complaint.getStatus(),
                complaint.getAssignedDepartment(),
                complaint.getCreatedBy(),
                complaint.getCreatedDate(),
                comments
        );
    }

    private CommentResponse convertToCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getUserId(),
                comment.getRole(),
                comment.getCommentText(),
                comment.getTimestamp()
        );
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Long) auth.getDetails();
    }

    private String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
    }
}