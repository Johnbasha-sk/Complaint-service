<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
<h4>Admin Dashboard</h4>
<div class="alert alert-info">Includes Staff features plus deletion and user management.</div>
<div class="row g-3">
  <div class="col-12">
    <div class="card shadow-sm">
      <div class="card-header bg-white fw-semibold">User Management</div>
      <div class="card-body">
        <a class="btn btn-outline-secondary btn-sm" href="/auth/register">Create User</a>
      </div>
    </div>
  </div>
  <div class="col-12">
    <div class="card shadow-sm">
      <div class="card-header bg-white fw-semibold d-flex justify-content-between align-items-center">
        <span>All Complaints</span>
        <button class="btn btn-sm btn-outline-secondary" onclick="loadAll()">Refresh</button>
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
              <th>Assigned</th>
              <th>Actions</th>
            </tr>
            </thead>
            <tbody id="allTable"></tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
function getCookie(name){return document.cookie.split('; ').find(r=>r.startsWith(name+'='))?.split('=')[1];}
function authHeaders(){ const t = getCookie('jwt'); return t ? { 'Authorization': 'Bearer ' + t, 'Content-Type':'application/json' } : { 'Content-Type':'application/json'} }
async function loadAll(){
  const res = await fetch('http://localhost:8082/staff/complaints', { headers: authHeaders() });
  const tbody = document.getElementById('allTable'); tbody.innerHTML='';
  if(!res.ok){ tbody.innerHTML = '<tr><td colspan="6">Failed ('+res.status+')</td></tr>'; return; }
  const data = await res.json();
  data.forEach(c => {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${c.id}</td><td>${c.category}</td><td>${c.description}</td><td><span class="badge bg-${c.status==='RESOLVED'?'success':(c.status==='IN_PROGRESS'?'warning text-dark':'secondary')}">${c.status}</span></td><td>${c.assignedDepartment||''}</td><td>
      <div class="d-flex gap-2">
        <input class="form-control form-control-sm" placeholder="Dept" id="dept-${c.id}" style="width:120px;"/>
        <button class="btn btn-sm btn-outline-primary" onclick="assign(${c.id})">Assign</button>
        <select class="form-select form-select-sm" id="st-${c.id}" style="width:140px;">
          <option>PENDING</option><option>IN_PROGRESS</option><option>RESOLVED</option>
        </select>
        <button class="btn btn-sm btn-outline-success" onclick="upd(${c.id})">Update</button>
        <button class="btn btn-sm btn-outline-danger" onclick="del(${c.id})">Delete</button>
      </div>
    </td>`;
    tbody.appendChild(tr);
  });
}
async function assign(id){ const dept = document.getElementById('dept-'+id).value; const r = await fetch('http://localhost:8082/staff/complaints/'+id+'/assign',{method:'PUT', headers: authHeaders(), body: JSON.stringify({assignedDepartment: dept})}); if(r.ok) loadAll(); }
async function upd(id){ const st = document.getElementById('st-'+id).value; const r = await fetch('http://localhost:8082/staff/complaints/'+id+'/status',{method:'PUT', headers: authHeaders(), body: JSON.stringify({status: st})}); if(r.ok) loadAll(); }
async function del(id){ const r = await fetch('http://localhost:8082/admin/complaints/'+id,{method:'DELETE', headers: authHeaders()}); if(r.ok) loadAll(); }
window.addEventListener('load', loadAll);
</script>
<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>