package com.municipality.complaintservice.controller;

import com.municipality.complaintservice.dto.ComplaintRequest;
import com.municipality.complaintservice.dto.ComplaintResponse;
import com.municipality.complaintservice.dto.CommentRequest;
import com.municipality.complaintservice.dto.CommentResponse;
import com.municipality.complaintservice.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

    private final ComplaintService complaintService;

    public CitizenController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/complaints")
    public ResponseEntity<ComplaintResponse> fileComplaint(@Valid @RequestBody ComplaintRequest request) {
        try {
            ComplaintResponse response = complaintService.fileComplaint(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(Authentication authentication) {
        try {
            Long userId = (Long) authentication.getDetails();
            List<ComplaintResponse> complaints = complaintService.getComplaintsByUser(userId);
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/complaints/{id}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication) {
        try {
            Long userId = (Long) authentication.getDetails();
            
            // Check if user owns the complaint
            if (!complaintService.isComplaintOwner(id, userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            CommentResponse response = complaintService.addComment(id, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/complaints/{id}")
    public ResponseEntity<ComplaintResponse> getComplaint(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            Long userId = (Long) authentication.getDetails();
            
            // Check if user owns the complaint
            if (!complaintService.isComplaintOwner(id, userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            ComplaintResponse response = complaintService.getComplaintById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}