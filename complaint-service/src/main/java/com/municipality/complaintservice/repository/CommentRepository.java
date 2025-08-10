package com.municipality.complaintservice.repository;

import com.municipality.complaintservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.complaint.id = :complaintId ORDER BY c.timestamp ASC")
    List<Comment> findByComplaintIdOrderByTimestampAsc(@Param("complaintId") Long complaintId);

    @Query("SELECT c FROM Comment c WHERE c.userId = :userId ORDER BY c.timestamp DESC")
    List<Comment> findByUserIdOrderByTimestampDesc(@Param("userId") Long userId);
}