package com.municipal.complaintservice.service;

import com.municipal.complaintservice.dto.*;

import java.util.List;

public interface ComplaintService {
    ComplaintResponse fileComplaint(CreateComplaintRequest request, String createdBy);
    List<ComplaintResponse> getComplaintsForUser(String userId);
    List<ComplaintResponse> getAllComplaints();
    ComplaintResponse assignDepartment(Long complaintId, String department);
    ComplaintResponse updateStatus(Long complaintId, String statusUpdatedByRole, com.municipal.complaintservice.model.Status status);
    CommentResponse addComment(Long complaintId, String userId, String userRole, String text, boolean restrictCitizenToOwnComplaint);
}