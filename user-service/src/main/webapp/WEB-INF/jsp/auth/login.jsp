<%@ include file="../layout/header.jsp" %>

<div class="container">
    <div class="row justify-content-center" style="min-height: 80vh; align-items: center;">
        <div class="col-md-6 col-lg-5">
            <div class="card">
                <div class="card-header text-center">
                    <h4><i class="fas fa-sign-in-alt me-2"></i>Login</h4>
                </div>
                <div class="card-body p-5">
                    <!-- Flash Messages -->
                    <c:if test="${not empty success}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle me-2"></i>${success}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-circle me-2"></i>${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <form action="/auth/login" method="post" modelAttribute="loginRequest">
                        <div class="mb-4">
                            <label for="username" class="form-label">
                                <i class="fas fa-user me-2"></i>Username
                            </label>
                            <input type="text" class="form-control" id="username" name="username" 
                                   placeholder="Enter your username" required 
                                   value="${loginRequest.username}">
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock me-2"></i>Password
                            </label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   placeholder="Enter your password" required>
                        </div>

                        <div class="d-grid mb-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-sign-in-alt me-2"></i>Login
                            </button>
                        </div>

                        <div class="text-center">
                            <p class="mb-0">Don't have an account?</p>
                            <a href="/auth/register" class="btn btn-outline-primary btn-sm mt-2">
                                <i class="fas fa-user-plus me-1"></i>Register Here
                            </a>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Demo Accounts Info -->
            <div class="card mt-4">
                <div class="card-header">
                    <h6 class="mb-0"><i class="fas fa-info-circle me-2"></i>Demo Accounts</h6>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4 text-center">
                            <div class="p-2 bg-light rounded mb-2">
                                <strong>Citizen</strong><br>
                                <small>citizen / password</small>
                            </div>
                        </div>
                        <div class="col-md-4 text-center">
                            <div class="p-2 bg-light rounded mb-2">
                                <strong>Staff</strong><br>
                                <small>staff / password</small>
                            </div>
                        </div>
                        <div class="col-md-4 text-center">
                            <div class="p-2 bg-light rounded mb-2">
                                <strong>Admin</strong><br>
                                <small>admin / password</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>