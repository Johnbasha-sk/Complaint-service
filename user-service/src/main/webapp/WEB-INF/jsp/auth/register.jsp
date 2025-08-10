<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.municipal.user.model.Role" %>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<div class="row justify-content-center">
  <div class="col-md-6">
    <div class="card shadow-sm">
      <div class="card-body">
        <h5 class="card-title mb-3">Register</h5>
        <form method="post" action="/auth/register">
          <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" name="username" class="form-control" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" name="password" class="form-control" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Role</label>
            <select name="role" class="form-select">
              <option value="CITIZEN">Citizen</option>
              <option value="STAFF">Staff</option>
              <option value="ADMIN">Admin</option>
            </select>
          </div>
          <button class="btn btn-primary w-100" type="submit">Create Account</button>
        </form>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>