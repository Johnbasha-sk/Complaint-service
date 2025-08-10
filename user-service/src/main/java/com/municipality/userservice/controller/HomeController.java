package com.municipality.userservice.controller;

import com.municipality.userservice.entity.Role;
import com.municipality.userservice.entity.User;
import com.municipality.userservice.service.ComplaintServiceClient;
import com.municipality.userservice.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final UserService userService;
    private final ComplaintServiceClient complaintServiceClient;

    public HomeController(UserService userService, ComplaintServiceClient complaintServiceClient) {
        this.userService = userService;
        this.complaintServiceClient = complaintServiceClient;
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Get user role and redirect to appropriate dashboard
            String username = authentication.getName();
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                return "redirect:" + userService.getRedirectUrlByRole(user.getRole());
            }
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/citizen/home")
    @PreAuthorize("hasRole('CITIZEN')")
    public String citizenHome(Authentication authentication, HttpServletRequest request, Model model) {
        String username = authentication.getName();
        Long userId = (Long) authentication.getDetails();
        String jwtToken = getTokenFromCookie(request);
        
        User user = userService.findByUsername(username).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        
        // Fetch complaints from Complaint Service
        try {
            List<?> complaints = complaintServiceClient.getCitizenComplaints(jwtToken);
            model.addAttribute("complaints", complaints);
        } catch (Exception e) {
            model.addAttribute("complaints", List.of());
            model.addAttribute("complaintError", "Unable to load complaints. Please try again later.");
        }
        
        return "dashboard/citizen";
    }

    @GetMapping("/staff/home")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public String staffHome(Authentication authentication, HttpServletRequest request, Model model) {
        String username = authentication.getName();
        Long userId = (Long) authentication.getDetails();
        String jwtToken = getTokenFromCookie(request);
        
        User user = userService.findByUsername(username).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        
        // Fetch all complaints from Complaint Service
        try {
            List<?> complaints = complaintServiceClient.getAllComplaints(jwtToken);
            model.addAttribute("complaints", complaints);
        } catch (Exception e) {
            model.addAttribute("complaints", List.of());
            model.addAttribute("complaintError", "Unable to load complaints. Please try again later.");
        }
        
        return "dashboard/staff";
    }

    @GetMapping("/admin/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHome(Authentication authentication, HttpServletRequest request, Model model) {
        String username = authentication.getName();
        Long userId = (Long) authentication.getDetails();
        String jwtToken = getTokenFromCookie(request);
        
        User user = userService.findByUsername(username).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        
        // Fetch all complaints from Complaint Service
        try {
            List<?> complaints = complaintServiceClient.getAllComplaints(jwtToken);
            model.addAttribute("complaints", complaints);
        } catch (Exception e) {
            model.addAttribute("complaints", List.of());
            model.addAttribute("complaintError", "Unable to load complaints. Please try again later.");
        }
        
        // Fetch all users for user management
        try {
            List<User> allUsers = userService.findAllActiveUsers();
            model.addAttribute("allUsers", allUsers);
        } catch (Exception e) {
            model.addAttribute("allUsers", List.of());
        }
        
        return "dashboard/admin";
    }

    @PostMapping("/citizen/complaints")
    @PreAuthorize("hasRole('CITIZEN')")
    @ResponseBody
    public Map<String, Object> fileComplaint(@RequestBody Map<String, Object> complaintData, 
                                            HttpServletRequest request) {
        String jwtToken = getTokenFromCookie(request);
        Map<String, Object> response = new HashMap<>();
        
        try {
            Object result = complaintServiceClient.fileComplaint(complaintData, jwtToken);
            response.put("success", true);
            response.put("data", result);
            response.put("message", "Complaint filed successfully!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to file complaint: " + e.getMessage());
        }
        
        return response;
    }

    @PutMapping("/staff/complaints/{id}/status")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @ResponseBody
    public Map<String, Object> updateComplaintStatus(@PathVariable Long id, 
                                                     @RequestBody Map<String, Object> statusData,
                                                     HttpServletRequest request) {
        String jwtToken = getTokenFromCookie(request);
        Map<String, Object> response = new HashMap<>();
        
        try {
            Object result = complaintServiceClient.updateComplaintStatus(id, statusData, jwtToken);
            response.put("success", true);
            response.put("data", result);
            response.put("message", "Complaint status updated successfully!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update status: " + e.getMessage());
        }
        
        return response;
    }

    @PutMapping("/staff/complaints/{id}/assign")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @ResponseBody
    public Map<String, Object> assignDepartment(@PathVariable Long id, 
                                               @RequestBody Map<String, Object> departmentData,
                                               HttpServletRequest request) {
        String jwtToken = getTokenFromCookie(request);
        Map<String, Object> response = new HashMap<>();
        
        try {
            Object result = complaintServiceClient.assignDepartment(id, departmentData, jwtToken);
            response.put("success", true);
            response.put("data", result);
            response.put("message", "Department assigned successfully!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to assign department: " + e.getMessage());
        }
        
        return response;
    }

    @DeleteMapping("/admin/complaints/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public Map<String, Object> deleteComplaint(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = getTokenFromCookie(request);
        Map<String, Object> response = new HashMap<>();
        
        try {
            complaintServiceClient.deleteComplaint(id, jwtToken);
            response.put("success", true);
            response.put("message", "Complaint deleted successfully!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to delete complaint: " + e.getMessage());
        }
        
        return response;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("authToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}