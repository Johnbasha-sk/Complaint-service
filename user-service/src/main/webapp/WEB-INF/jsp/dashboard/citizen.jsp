<%@ include file="../layout/header.jsp" %>

<div class="row">
    <!-- Sidebar -->
    <div class="col-md-3 col-lg-2 sidebar p-3">
        <div class="d-flex align-items-center mb-4">
            <i class="fas fa-user-circle fa-2x me-3"></i>
            <div>
                <h6 class="mb-0">${user.username}</h6>
                <small>Citizen Dashboard</small>
            </div>
        </div>
        
        <nav class="nav flex-column">
            <a class="nav-link active" href="#complaints-section">
                <i class="fas fa-file-alt me-2"></i>My Complaints
            </a>
            <a class="nav-link" href="#file-complaint-section">
                <i class="fas fa-plus-circle me-2"></i>File New Complaint
            </a>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="col-md-9 col-lg-10 p-4">
        <!-- Welcome Header -->
        <div class="row mb-4">
            <div class="col-12">
                <h2><i class="fas fa-home me-2"></i>Welcome, ${user.username}!</h2>
                <p class="text-muted">Manage your complaints and track their progress</p>
            </div>
        </div>

        <!-- Stats Cards -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="dashboard-stat text-center">
                    <i class="fas fa-file-alt"></i>
                    <h3 id="total-complaints">0</h3>
                    <p>Total Complaints</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dashboard-stat text-center">
                    <i class="fas fa-clock"></i>
                    <h3 id="pending-complaints">0</h3>
                    <p>Pending</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dashboard-stat text-center">
                    <i class="fas fa-check-circle"></i>
                    <h3 id="resolved-complaints">0</h3>
                    <p>Resolved</p>
                </div>
            </div>
        </div>

        <!-- File New Complaint Section -->
        <section id="file-complaint-section" class="mb-5">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0"><i class="fas fa-plus-circle me-2"></i>File New Complaint</h5>
                </div>
                <div class="card-body">
                    <form id="complaint-form">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="category" class="form-label">
                                        <i class="fas fa-tag me-2"></i>Category
                                    </label>
                                    <select class="form-select" id="category" name="category" required>
                                        <option value="">Select Category</option>
                                        <option value="WATER">Water Supply</option>
                                        <option value="SANITATION">Sanitation</option>
                                        <option value="ROADS">Roads & Infrastructure</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        
                        <div class="mb-3">
                            <label for="description" class="form-label">
                                <i class="fas fa-file-text me-2"></i>Description
                            </label>
                            <textarea class="form-control" id="description" name="description" 
                                      rows="4" placeholder="Please describe your complaint in detail..." required></textarea>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-paper-plane me-2"></i>Submit Complaint
                        </button>
                    </form>
                </div>
            </div>
        </section>

        <!-- My Complaints Section -->
        <section id="complaints-section">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="fas fa-list me-2"></i>My Complaints</h5>
                    <button class="btn btn-outline-primary btn-sm" onclick="loadComplaints()">
                        <i class="fas fa-refresh me-1"></i>Refresh
                    </button>
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
    </div>
</div>

<script>
$(document).ready(function() {
    loadComplaints();
    updateStats();
    
    // Handle complaint form submission
    $('#complaint-form').on('submit', function(e) {
        e.preventDefault();
        
        const formData = {
            category: $('#category').val(),
            description: $('#description').val()
        };
        
        $.ajax({
            url: '/citizen/complaints',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                if (response.success) {
                    showToast(response.message, 'success');
                    $('#complaint-form')[0].reset();
                    loadComplaints();
                    updateStats();
                } else {
                    showToast(response.message, 'danger');
                }
            },
            error: handleAjaxError
        });
    });
});

function loadComplaints() {
    $.ajax({
        url: '/citizen/home',
        method: 'GET',
        success: function(data) {
            // Extract complaints data from the page model
            const complaints = ${complaints != null ? complaints : '[]'};
            displayComplaints(complaints);
        },
        error: function() {
            $('#complaints-tbody').html('<tr><td colspan="6" class="text-center">Failed to load complaints</td></tr>');
        }
    });
}

function displayComplaints(complaints) {
    const tbody = $('#complaints-tbody');
    tbody.empty();
    
    if (!complaints || complaints.length === 0) {
        tbody.html('<tr><td colspan="6" class="text-center text-muted">No complaints found</td></tr>');
        return;
    }
    
    complaints.forEach(function(complaint) {
        const statusClass = getStatusBadgeClass(complaint.status);
        const row = `
            <tr>
                <td><strong>#${complaint.id}</strong></td>
                <td><span class="badge bg-info">${complaint.category}</span></td>
                <td>${complaint.description}</td>
                <td><span class="badge ${statusClass}">${complaint.status}</span></td>
                <td>${complaint.assignedDepartment || 'Not Assigned'}</td>
                <td>${formatDate(complaint.createdDate)}</td>
            </tr>
        `;
        tbody.append(row);
    });
}

function updateStats() {
    const complaints = ${complaints != null ? complaints : '[]'};
    const total = complaints.length;
    const pending = complaints.filter(c => c.status === 'PENDING').length;
    const resolved = complaints.filter(c => c.status === 'RESOLVED').length;
    
    $('#total-complaints').text(total);
    $('#pending-complaints').text(pending);
    $('#resolved-complaints').text(resolved);
}
</script>

<%@ include file="../layout/footer.jsp" %>