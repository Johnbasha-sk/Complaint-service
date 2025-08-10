<%@ include file="../layout/header.jsp" %>

<div class="container">
    <div class="row justify-content-center" style="min-height: 80vh; align-items: center;">
        <div class="col-md-6 col-lg-5">
            <div class="card">
                <div class="card-header text-center">
                    <h4><i class="fas fa-user-plus me-2"></i>Register</h4>
                </div>
                <div class="card-body p-5">
                    <!-- Flash Messages -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-circle me-2"></i>${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <form action="/auth/register" method="post" modelAttribute="registerRequest">
                        <div class="mb-3">
                            <label for="username" class="form-label">
                                <i class="fas fa-user me-2"></i>Username
                            </label>
                            <input type="text" class="form-control" id="username" name="username" 
                                   placeholder="Choose a username" required 
                                   value="${registerRequest.username}">
                            <div class="form-text">Username must be between 3-50 characters</div>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">
                                <i class="fas fa-envelope me-2"></i>Email Address
                            </label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   placeholder="Enter your email" required 
                                   value="${registerRequest.email}">
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock me-2"></i>Password
                            </label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   placeholder="Choose a password" required>
                            <div class="form-text">Password must be at least 6 characters long</div>
                        </div>

                        <div class="mb-4">
                            <label for="role" class="form-label">
                                <i class="fas fa-user-tag me-2"></i>Role
                            </label>
                            <select class="form-select" id="role" name="role" required>
                                <option value="">Select your role</option>
                                <c:forEach var="roleOption" items="${roles}">
                                    <option value="${roleOption}" 
                                            ${registerRequest.role == roleOption ? 'selected' : ''}>
                                        ${roleOption}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="form-text">
                                <strong>Citizen:</strong> File and track complaints<br>
                                <strong>Staff:</strong> Manage complaints and assign departments<br>
                                <strong>Admin:</strong> Full system access including user management
                            </div>
                        </div>

                        <div class="d-grid mb-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-user-plus me-2"></i>Register
                            </button>
                        </div>

                        <div class="text-center">
                            <p class="mb-0">Already have an account?</p>
                            <a href="/auth/login" class="btn btn-outline-primary btn-sm mt-2">
                                <i class="fas fa-sign-in-alt me-1"></i>Login Here
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>