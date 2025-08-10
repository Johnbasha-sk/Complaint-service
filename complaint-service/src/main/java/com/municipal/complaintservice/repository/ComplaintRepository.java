package com.municipal.complaintservice.repository;

import com.municipal.complaintservice.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByCreatedBy(String createdBy);
}