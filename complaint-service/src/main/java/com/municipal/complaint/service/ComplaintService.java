package com.municipal.complaint.service;

import com.municipal.complaint.dto.AssignDepartmentRequest;
import com.municipal.complaint.dto.ComplaintRequest;
import com.municipal.complaint.dto.UpdateStatusRequest;
import com.municipal.complaint.model.Complaint;
import com.municipal.complaint.model.ComplaintStatus;
import com.municipal.complaint.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Complaint createComplaint(Long userId, ComplaintRequest request) {
        Complaint complaint = new Complaint();
        complaint.setCategory(request.getCategory());
        complaint.setDescription(request.getDescription());
        complaint.setStatus(ComplaintStatus.PENDING);
        complaint.setAssignedDepartment(null);
        complaint.setCreatedBy(userId);
        complaint.setCreatedDate(Instant.now());
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getComplaintsByUser(Long userId) {
        return complaintRepository.findByCreatedBy(userId);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> findById(Long id) {
        return complaintRepository.findById(id);
    }

    public Complaint assignDepartment(Long id, AssignDepartmentRequest request) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        complaint.setAssignedDepartment(request.getAssignedDepartment());
        return complaintRepository.save(complaint);
    }

    public Complaint updateStatus(Long id, UpdateStatusRequest request) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        complaint.setStatus(request.getStatus());
        return complaintRepository.save(complaint);
    }

    public void deleteById(Long id) {
        complaintRepository.deleteById(id);
    }
}