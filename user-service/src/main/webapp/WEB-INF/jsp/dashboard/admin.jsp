<%@ include file="../layout/header.jsp" %>

<div class="row">
    <!-- Sidebar -->
    <div class="col-md-3 col-lg-2 sidebar p-3">
        <div class="d-flex align-items-center mb-4">
            <i class="fas fa-user-shield fa-2x me-3"></i>
            <div>
                <h6 class="mb-0">${user.username}</h6>
                <small>Admin Dashboard</small>
            </div>
        </div>
        
        <nav class="nav flex-column">
            <a class="nav-link active" href="#overview-section">
                <i class="fas fa-tachometer-alt me-2"></i>Overview
            </a>
            <a class="nav-link" href="#complaints-section">
                <i class="fas fa-list me-2"></i>Complaints
            </a>
            <a class="nav-link" href="#users-section">
                <i class="fas fa-users me-2"></i>User Management
            </a>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="col-md-9 col-lg-10 p-4">
        <!-- Welcome Header -->
        <div class="row mb-4">
            <div class="col-12">
                <h2><i class="fas fa-home me-2"></i>Admin Dashboard</h2>
                <p class="text-muted">Complete system administration and oversight</p>
            </div>
        </div>

        <!-- Overview Stats -->
        <section id="overview-section" class="mb-5">
            <div class="row mb-4">
                <div class="col-md-3">
                    <div class="dashboard-stat text-center">
                        <i class="fas fa-file-alt"></i>
                        <h3 id="total-complaints">0</h3>
                        <p>Total Complaints</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="dashboard-stat text-center">
                        <i class="fas fa-users"></i>
                        <h3 id="total-users">${allUsers != null ? allUsers.size() : 0}</h3>
                        <p>Total Users</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="dashboard-stat text-center">
                        <i class="fas fa-clock"></i>
                        <h3 id="pending-complaints">0</h3>
                        <p>Pending Issues</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="dashboard-stat text-center">
                        <i class="fas fa-check-circle"></i>
                        <h3 id="resolved-complaints">0</h3>
                        <p>Resolved Today</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- Complaints Management -->
        <section id="complaints-section" class="mb-5">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="fas fa-list me-2"></i>Complaint Management</h5>
                    <div>
                        <select class="form-select form-select-sm d-inline-block w-auto me-2" id="status-filter">
                            <option value="">All Status</option>
                            <option value="PENDING">Pending</option>
                            <option value="IN_PROGRESS">In Progress</option>
                            <option value="RESOLVED">Resolved</option>
                        </select>
                        <button class="btn btn-outline-primary btn-sm" onclick="loadComplaints()">
                            <i class="fas fa-refresh me-1"></i>Refresh
                        </button>
                    </div>
                </div>
                <div class="card-body">
                    <c:if test="${not empty complaintError}">
                        <div class="alert alert-warning">
                            <i class="fas fa-exclamation-triangle me-2"></i>${complaintError}
                        </div>
                    </c:if>
                    
                    <div class="table-responsive">
                        <table class="table table-hover" id="complaints-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Category</th>
                                    <th>Description</th>
                                    <th>Status</th>
                                    <th>Department</th>
                                    <th>Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="complaints-tbody">
                                <!-- Complaints will be loaded here -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>

        <!-- User Management -->
        <section id="users-section">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0"><i class="fas fa-users me-2"></i>User Management</h5>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th>Created</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${allUsers}">
                                    <tr>
                                        <td><strong>#${user.id}</strong></td>
                                        <td>${user.username}</td>
                                        <td>${user.email}</td>
                                        <td><span class="badge bg-primary">${user.role}</span></td>
                                        <td>
                                            <span class="badge ${user.isActive ? 'bg-success' : 'bg-danger'}">
                                                ${user.isActive ? 'Active' : 'Inactive'}
                                            </span>
                                        </td>
                                        <td><script>document.write(formatDate('${user.createdDate}'));</script></td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <c:if test="${user.isActive}">
                                                    <button class="btn btn-outline-warning" onclick="toggleUserStatus(${user.id}, false)" title="Deactivate">
                                                        <i class="fas fa-ban"></i>
                                                    </button>
                                                </c:if>
                                                <c:if test="${!user.isActive}">
                                                    <button class="btn btn-outline-success" onclick="toggleUserStatus(${user.id}, true)" title="Activate">
                                                        <i class="fas fa-check"></i>
                                                    </button>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>

<!-- Status Update Modal -->
<div class="modal fade" id="statusModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Update Complaint Status</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="status-form">
                    <input type="hidden" id="complaint-id" name="complaintId">
                    <div class="mb-3">
                        <label for="new-status" class="form-label">New Status</label>
                        <select class="form-select" id="new-status" name="status" required>
                            <option value="PENDING">Pending</option>
                            <option value="IN_PROGRESS">In Progress</option>
                            <option value="RESOLVED">Resolved</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="updateStatus()">Update Status</button>
            </div>
        </div>
    </div>
</div>

<!-- Department Assignment Modal -->
<div class="modal fade" id="departmentModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Assign Department</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="department-form">
                    <input type="hidden" id="dept-complaint-id" name="complaintId">
                    <div class="mb-3">
                        <label for="assigned-department" class="form-label">Department</label>
                        <select class="form-select" id="assigned-department" name="assignedDepartment" required>
                            <option value="">Select Department</option>
                            <option value="Water Department">Water Department</option>
                            <option value="Sanitation Department">Sanitation Department</option>
                            <option value="Public Works Department">Public Works Department</option>
                            <option value="Municipal Engineering">Municipal Engineering</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="assignDepartment()">Assign Department</button>
            </div>
        </div>
    </div>
</div>

<script>
$(document).ready(function() {
    loadComplaints();
    updateStats();
    
    // Handle status filter change
    $('#status-filter').on('change', function() {
        filterComplaints();
    });
});

function loadComplaints() {
    // Use server-side complaints data
    const complaints = ${complaints != null ? complaints : '[]'};
    window.allComplaints = complaints; // Store for filtering
    displayComplaints(complaints);
    updateStats();
}

function displayComplaints(complaints) {
    const tbody = $('#complaints-tbody');
    tbody.empty();
    
    if (!complaints || complaints.length === 0) {
        tbody.html('<tr><td colspan="7" class="text-center text-muted">No complaints found</td></tr>');
        return;
    }
    
    complaints.forEach(function(complaint) {
        const statusClass = getStatusBadgeClass(complaint.status);
        const row = `
            <tr>
                <td><strong>#${complaint.id}</strong></td>
                <td><span class="badge bg-info">${complaint.category}</span></td>
                <td>${complaint.description.length > 50 ? complaint.description.substring(0, 50) + '...' : complaint.description}</td>
                <td><span class="badge ${statusClass}">${complaint.status}</span></td>
                <td>${complaint.assignedDepartment || 'Not Assigned'}</td>
                <td>${formatDate(complaint.createdDate)}</td>
                <td>
                    <div class="btn-group btn-group-sm">
                        <button class="btn btn-outline-primary" onclick="showStatusModal(${complaint.id}, '${complaint.status}')" title="Update Status">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-outline-success" onclick="showDepartmentModal(${complaint.id}, '${complaint.assignedDepartment || ''}')" title="Assign Department">
                            <i class="fas fa-building"></i>
                        </button>
                        <button class="btn btn-outline-danger" onclick="deleteComplaint(${complaint.id})" title="Delete Complaint">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `;
        tbody.append(row);
    });
}

function filterComplaints() {
    const selectedStatus = $('#status-filter').val();
    if (!window.allComplaints) return;
    
    let filteredComplaints = window.allComplaints;
    if (selectedStatus) {
        filteredComplaints = window.allComplaints.filter(c => c.status === selectedStatus);
    }
    
    displayComplaints(filteredComplaints);
}

function updateStats() {
    const complaints = window.allComplaints || [];
    const total = complaints.length;
    const pending = complaints.filter(c => c.status === 'PENDING').length;
    const resolved = complaints.filter(c => c.status === 'RESOLVED').length;
    
    $('#total-complaints').text(total);
    $('#pending-complaints').text(pending);
    $('#resolved-complaints').text(resolved);
}

function showStatusModal(complaintId, currentStatus) {
    $('#complaint-id').val(complaintId);
    $('#new-status').val(currentStatus);
    new bootstrap.Modal($('#statusModal')).show();
}

function showDepartmentModal(complaintId, currentDepartment) {
    $('#dept-complaint-id').val(complaintId);
    $('#assigned-department').val(currentDepartment);
    new bootstrap.Modal($('#departmentModal')).show();
}

function updateStatus() {
    const complaintId = $('#complaint-id').val();
    const status = $('#new-status').val();
    
    $.ajax({
        url: `/staff/complaints/${complaintId}/status`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ status: status }),
        success: function(response) {
            if (response.success) {
                showToast(response.message, 'success');
                bootstrap.Modal.getInstance($('#statusModal')).hide();
                setTimeout(() => location.reload(), 1000);
            } else {
                showToast(response.message, 'danger');
            }
        },
        error: handleAjaxError
    });
}

function assignDepartment() {
    const complaintId = $('#dept-complaint-id').val();
    const department = $('#assigned-department').val();
    
    $.ajax({
        url: `/staff/complaints/${complaintId}/assign`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ assignedDepartment: department }),
        success: function(response) {
            if (response.success) {
                showToast(response.message, 'success');
                bootstrap.Modal.getInstance($('#departmentModal')).hide();
                setTimeout(() => location.reload(), 1000);
            } else {
                showToast(response.message, 'danger');
            }
        },
        error: handleAjaxError
    });
}

function deleteComplaint(complaintId) {
    if (!confirm('Are you sure you want to delete this complaint? This action cannot be undone.')) {
        return;
    }
    
    $.ajax({
        url: `/admin/complaints/${complaintId}`,
        method: 'DELETE',
        success: function(response) {
            if (response.success) {
                showToast(response.message, 'success');
                setTimeout(() => location.reload(), 1000);
            } else {
                showToast(response.message, 'danger');
            }
        },
        error: handleAjaxError
    });
}

function toggleUserStatus(userId, activate) {
    const action = activate ? 'activate' : 'deactivate';
    if (!confirm(`Are you sure you want to ${action} this user?`)) {
        return;
    }
    
    // For now, just show a message as we haven't implemented the backend endpoint
    showToast(`User ${action}d successfully! (Feature coming soon)`, 'info');
}
</script>

<%@ include file="../layout/footer.jsp" %>