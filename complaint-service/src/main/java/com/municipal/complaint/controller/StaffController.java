package com.municipal.complaint.controller;

import com.municipal.complaint.dto.AssignDepartmentRequest;
import com.municipal.complaint.dto.CommentRequest;
import com.municipal.complaint.dto.UpdateStatusRequest;
import com.municipal.complaint.model.Complaint;
import com.municipal.complaint.service.CommentService;
import com.municipal.complaint.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class StaffController {

    private final ComplaintService complaintService;
    private final CommentService commentService;

    public StaffController(ComplaintService complaintService, CommentService commentService) {
        this.complaintService = complaintService;
        this.commentService = commentService;
    }

    @GetMapping("/staff/complaints")
    public ResponseEntity<List<Complaint>> allComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/staff/complaints/{id}/assign")
    public ResponseEntity<Complaint> assignComplaint(@PathVariable("id") Long id, @Valid @RequestBody AssignDepartmentRequest request) {
        return ResponseEntity.ok(complaintService.assignDepartment(id, request));
    }

    @PutMapping("/staff/complaints/{id}/status")
    public ResponseEntity<Complaint> updateStatus(@PathVariable("id") Long id, @Valid @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(complaintService.updateStatus(id, request));
    }

    @PostMapping("/staff/complaints/{id}/comments")
    public ResponseEntity<?> commentAny(Authentication authentication, @PathVariable("id") Long complaintId, @Valid @RequestBody CommentRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("STAFF");
        return ResponseEntity.ok(commentService.addComment(complaintId, userId, role, request.getCommentText()));
    }
}