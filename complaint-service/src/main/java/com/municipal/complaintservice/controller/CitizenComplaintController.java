package com.municipal.complaintservice.controller;

import com.municipal.complaintservice.dto.*;
import com.municipal.complaintservice.service.ComplaintService;
import com.municipal.complaintservice.util.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizen/complaints")
@RequiredArgsConstructor
public class CitizenComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintResponse> fileComplaint(@Valid @RequestBody CreateComplaintRequest request) {
        String userId = CurrentUser.getUserId();
        ComplaintResponse response = complaintService.fileComplaint(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints() {
        String userId = CurrentUser.getUserId();
        return ResponseEntity.ok(complaintService.getComplaintsForUser(userId));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable("id") Long complaintId,
                                                      @Valid @RequestBody AddCommentRequest request) {
        String userId = CurrentUser.getUserId();
        String role = CurrentUser.getRoles().stream().findFirst().orElse("ROLE_CITIZEN");
        CommentResponse response = complaintService.addComment(complaintId, userId, role, request.getCommentText(), true);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}