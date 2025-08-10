package com.municipal.complaint.controller;

import com.municipal.complaint.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/complaints")
public class AdminController {

    private final ComplaintService complaintService;

    public AdminController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        complaintService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}