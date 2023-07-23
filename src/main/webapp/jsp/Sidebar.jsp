<%-- 
    Document   : Sidebar.jsp
    Created on : 15 Sep, 2022, 3:02:03 PM
    Author     : aaditya
--%>


<%@page import="sso.userManagement.domain.UserRole"%>
<%@page import="sso.userManagement.domain.User"%>
<jsp:include page="header.jsp"></jsp:include>

 <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="Home" class="brand-link">
      <img src="img/isro-logo.jpg" alt="SSO Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
      <span class="brand-text font-weight-light">User Management</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        
       
      </div>

      <!-- SidebarSearch Form -->


      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item menu-open">
            <a href="Home" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Dashboard
                <i class="right fas fa-angle-up"></i>
              </p>
            </a>
            
          </li>
          
        <% UserRole role=(UserRole)session.getAttribute("role");  %>
        
        <%  if(role!=null && role.getRoleName().equals("admin")) {                  %>
          
           <li class="nav-item menu-open">
            <a href="Manager?action=listUsers" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Admin
                <i class="right fas fa-angle-up"></i>
              </p>
            </a>
            
          </li>
          
          <%}%>
        
           <li class="nav-item menu-open">
            <a href="PasswordManager?action=view" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Password Update
                <i class="right fas fa-angle-up"></i>
              </p>
            </a>
            
          </li>
          
        
          
       
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>