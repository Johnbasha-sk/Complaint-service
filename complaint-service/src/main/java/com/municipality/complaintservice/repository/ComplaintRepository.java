package com.municipality.complaintservice.repository;

import com.municipality.complaintservice.entity.Complaint;
import com.municipality.complaintservice.entity.ComplaintCategory;
import com.municipality.complaintservice.entity.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByCreatedByOrderByCreatedDateDesc(Long createdBy);

    List<Complaint> findByStatusOrderByCreatedDateDesc(ComplaintStatus status);

    List<Complaint> findByCategoryOrderByCreatedDateDesc(ComplaintCategory category);

    List<Complaint> findByAssignedDepartmentOrderByCreatedDateDesc(String assignedDepartment);

    @Query("SELECT c FROM Complaint c ORDER BY c.createdDate DESC")
    List<Complaint> findAllOrderByCreatedDateDesc();

    @Query("SELECT c FROM Complaint c WHERE c.createdBy = :userId AND c.status = :status ORDER BY c.createdDate DESC")
    List<Complaint> findByCreatedByAndStatusOrderByCreatedDateDesc(@Param("userId") Long userId, @Param("status") ComplaintStatus status);
}