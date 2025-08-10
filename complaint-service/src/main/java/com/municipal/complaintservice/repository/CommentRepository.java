package com.municipal.complaintservice.repository;

import com.municipal.complaintservice.model.Comment;
import com.municipal.complaintservice.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByComplaint(Complaint complaint);
}