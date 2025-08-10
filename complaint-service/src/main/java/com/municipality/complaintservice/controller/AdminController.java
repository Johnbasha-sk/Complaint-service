package com.municipality.complaintservice.controller;

import com.municipality.complaintservice.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ComplaintService complaintService;

    public AdminController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @DeleteMapping("/complaints/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        try {
            complaintService.deleteComplaint(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}