package com.municipal.complaint.service;

import com.municipal.complaint.model.Comment;
import com.municipal.complaint.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Long complaintId, Long userId, String role, String text) {
        Comment comment = new Comment();
        comment.setComplaintId(complaintId);
        comment.setUserId(userId);
        comment.setRole(role);
        comment.setCommentText(text);
        comment.setTimestamp(Instant.now());
        return commentRepository.save(comment);
    }
}