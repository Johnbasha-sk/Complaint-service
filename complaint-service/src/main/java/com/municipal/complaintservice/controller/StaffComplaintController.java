package com.municipal.complaintservice.controller;

import com.municipal.complaintservice.dto.*;
import com.municipal.complaintservice.model.Status;
import com.municipal.complaintservice.service.ComplaintService;
import com.municipal.complaintservice.util.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff/complaints")
@RequiredArgsConstructor
public class StaffComplaintController {

    private final ComplaintService complaintService;

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<ComplaintResponse> assignDepartment(@PathVariable("id") Long complaintId,
                                                              @Valid @RequestBody AssignComplaintRequest request) {
        return ResponseEntity.ok(complaintService.assignDepartment(complaintId, request.getAssignedDepartment()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ComplaintResponse> updateStatus(@PathVariable("id") Long complaintId,
                                                          @Valid @RequestBody UpdateStatusRequest request) {
        String role = CurrentUser.getRoles().stream().findFirst().orElse("ROLE_STAFF");
        return ResponseEntity.ok(complaintService.updateStatus(complaintId, role, request.getStatus()));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable("id") Long complaintId,
                                                      @Valid @RequestBody AddCommentRequest request) {
        String userId = CurrentUser.getUserId();
        String role = CurrentUser.getRoles().stream().findFirst().orElse("ROLE_STAFF");
        CommentResponse response = complaintService.addComment(complaintId, userId, role, request.getCommentText(), false);
        return ResponseEntity.ok(response);
    }
}