package com.municipal.complaint.controller;

import com.municipal.complaint.dto.CommentRequest;
import com.municipal.complaint.dto.ComplaintRequest;
import com.municipal.complaint.model.Complaint;
import com.municipal.complaint.security.JwtAuthenticationFilter;
import com.municipal.complaint.service.CommentService;
import com.municipal.complaint.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/citizen/complaints")
public class CitizenController {

    private final ComplaintService complaintService;
    private final CommentService commentService;

    public CitizenController(ComplaintService complaintService, CommentService commentService) {
        this.complaintService = complaintService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Complaint> fileComplaint(Authentication authentication, @Valid @RequestBody ComplaintRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        Complaint created = complaintService.createComplaint(userId, request);
        return ResponseEntity.created(URI.create("/citizen/complaints/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Complaint>> myComplaints(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(complaintService.getComplaintsByUser(userId));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> commentOnOwn(Authentication authentication, @PathVariable("id") Long complaintId, @Valid @RequestBody CommentRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        // Ensure complaint belongs to the current user
        Complaint complaint = complaintService.findById(complaintId).orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        if (!complaint.getCreatedBy().equals(userId)) {
            return ResponseEntity.status(403).body("Cannot comment on another user's complaint");
        }
        String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("CITIZEN");
        return ResponseEntity.ok(commentService.addComment(complaintId, userId, role, request.getCommentText()));
    }
}