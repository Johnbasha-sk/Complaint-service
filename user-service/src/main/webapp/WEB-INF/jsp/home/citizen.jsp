<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<h4>Citizen Dashboard</h4>
<div class="row g-3">
  <div class="col-lg-6">
    <div class="card shadow-sm h-100">
      <div class="card-header bg-white fw-semibold">File a Complaint</div>
      <div class="card-body">
        <form id="complaintForm" onsubmit="submitComplaint(event)">
          <div class="mb-3">
            <label class="form-label">Category</label>
            <select id="category" class="form-select">
              <option>WATER</option>
              <option>SANITATION</option>
              <option>ROADS</option>
            </select>
          </div>
          <div class="mb-3">
            <label class="form-label">Description</label>
            <textarea id="description" class="form-control" rows="4" placeholder="Describe the issue" required></textarea>
          </div>
          <button class="btn btn-primary" type="submit">Submit</button>
          <span class="ms-2" id="submitStatus"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-lg-6">
    <div class="card shadow-sm h-100">
      <div class="card-header bg-white fw-semibold d-flex justify-content-between align-items-center">
        <span>My Complaints</span>
        <button class="btn btn-sm btn-outline-secondary" onclick="loadMyComplaints()">Refresh</button>
      </div>
      <div class="card-body">
        <div class="table-responsive">
          <table class="table table-sm align-middle">
            <thead>
              <tr>
                <th>ID</th>
                <th>Category</th>
                <th>Description</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody id="complaintsTable"></tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
function getCookie(name){return document.cookie.split('; ').find(r=>r.startsWith(name+'='))?.split('=')[1];}
function authHeaders(){ const t = getCookie('jwt'); return t ? { 'Authorization': 'Bearer ' + t, 'Content-Type': 'application/json' } : { 'Content-Type': 'application/json' } }
async function submitComplaint(e){
  e.preventDefault();
  const body = { category: document.getElementById('category').value, description: document.getElementById('description').value };
  const res = await fetch('http://localhost:8082/citizen/complaints', { method:'POST', headers: authHeaders(), body: JSON.stringify(body) });
  document.getElementById('submitStatus').textContent = res.ok ? 'Submitted' : ('Failed: ' + res.status);
  if(res.ok) { loadMyComplaints(); document.getElementById('complaintForm').reset(); }
}
async function loadMyComplaints(){
  const res = await fetch('http://localhost:8082/citizen/complaints', { headers: authHeaders() });
  const table = document.getElementById('complaintsTable');
  table.innerHTML = '';
  if (!res.ok) { table.innerHTML = '<tr><td colspan="4">Failed to load ('+res.status+')</td></tr>'; return; }
  const data = await res.json();
  data.forEach(row => {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${row.id}</td><td>${row.category}</td><td>${row.description}</td><td><span class="badge bg-${row.status==='RESOLVED'?'success':(row.status==='IN_PROGRESS'?'warning text-dark':'secondary')}">${row.status}</span></td>`;
    table.appendChild(tr);
  });
}
window.addEventListener('load', loadMyComplaints);
</script>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>