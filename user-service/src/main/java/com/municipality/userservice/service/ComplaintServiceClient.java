package com.municipality.userservice.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceClient {

    private final RestTemplate restTemplate;
    private static final String COMPLAINT_SERVICE_URL = "http://complaint-service";

    public ComplaintServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<?> getCitizenComplaints(String jwtToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List> response = restTemplate.exchange(
                    COMPLAINT_SERVICE_URL + "/citizen/complaints",
                    HttpMethod.GET,
                    entity,
                    List.class
            );

            return response.getBody();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<?> getAllComplaints(String jwtToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List> response = restTemplate.exchange(
                    COMPLAINT_SERVICE_URL + "/staff/complaints",
                    HttpMethod.GET,
                    entity,
                    List.class
            );

            return response.getBody();
        } catch (Exception e) {
            return List.of();
        }
    }

    public Object fileComplaint(Map<String, Object> complaintData, String jwtToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(complaintData, headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    COMPLAINT_SERVICE_URL + "/citizen/complaints",
                    HttpMethod.POST,
                    entity,
                    Object.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to file complaint: " + e.getMessage());
        }
    }

    public Object updateComplaintStatus(Long complaintId, Map<String, Object> statusData, String jwtToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(statusData, headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    COMPLAINT_SERVICE_URL + "/staff/complaints/" + complaintId + "/status",
                    HttpMethod.PUT,
                    entity,
                    Object.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update complaint status: " + e.getMessage());
        }
    }

    public Object assignDepartment(Long complaintId, Map<String, Object> departmentData, String jwtToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(departmentData, headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    COMPLAINT_SERVICE_URL + "/staff/complaints/" + complaintId + "/assign",
                    HttpMethod.PUT,
                    entity,
                    Object.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign department: " + e.getMessage());
        }
    }

    public void deleteComplaint(Long complaintId, String jwtToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.exchange(
                    COMPLAINT_SERVICE_URL + "/admin/complaints/" + complaintId,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete complaint: " + e.getMessage());
        }
    }
}