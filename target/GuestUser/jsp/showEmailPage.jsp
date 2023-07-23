<%-- 
    Document   : showEmailPage
    Created on : 11 Oct, 2022, 11:43:10 AM
    Author     : aaditya
--%>

<%@page import="sso.userManagement.domain.User"%>
<%-- 
    Document   : Home
    Created on : 15 Sep, 2022, 3:34:36 PM
    Author     : aaditya
--%>

<jsp:include page="Sidebar.jsp"></jsp:include>





  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
          
          </div><!-- /.col -->
       
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <!-- Small boxes (Stat box) -->
        <div class="row">
          
        
          <!-- ./col -->
         
          <!-- ./col -->

          <!-- ./col -->
        </div>
        <!-- /.row -->
        <!-- Main row -->
        <div class="row">
          <!-- Left col -->
          <section class="col-lg-12 connectedSortable">
            <!-- Custom tabs (Charts with tabs)-->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">
                  <i class="fas fa-store"></i>
                User Details
                </h3>
               
              </div><!-- /.card-header -->
              <div class="card-body">
                <div class="tab-content p-0">
                    
                    <form id="addEmail" action="updateEmail" method="POST">
                    <% User user=(User)session.getAttribute("user");                          %>
                    <div class="row">
                        <div class="col-md-3">
                            <label>UserID: </label>
                            <%= user.getUserID()  %>
                            
                        </div>
                        <div class="col-md-3">
                            <label>Name: </label>
                            <%= user.getName()  %>
                            
                        </div>
                  
                            
                             <div class="col-md-4">
                            <label>Email: </label>
                            <% if(user.getEmail()!=null) {         %>
                            <%= user.getEmail()  %>
                            
                            <%} else { %>
                          
                                
                                 <input type="text" id="email" name="email" class="form-control" autocomplete="off" placeholder="Enter Your Valid Isro Email ID" required="">
                                
                            
                            
                            <%}%>
                             </div>
                             
                             <div class="col-md-2">
                               <% if(user.isEmailVerified()) {   %>
                               <p style="color: green">Email Verified</p>
                               
                               <%} else {%>
                               <p style="color: red">Email Unverified</p>
                               <%}%>
                               
                             </div>
                             
                    </div>
                               
                               
                    
                      <div class="row" style="margin-top: 20px">
                          
                          <div class="col-md-4">
                                <label>Centre: </label>
                                   <%= user.getCentreCode()  %>
                              
                          </div>
                          
                         <div class="col-md-4">
                                 <label>Update Email: </label>
                                 <input type="text" id="email" name="email" class="form-control" autocomplete="off" placeholder="Enter Your Valid Email ID" required="">
                                
                            </div>
                    
                          
                              
                          <div class="col-md-4" style="margin-top: 29px">
                                <button type="submit" class="btn btn-success col-md-4">UPDATE</button>
                            </div>  
                            </div>
                            
                    </form>
                        
                    </div>
                    
                 
                  
                    
                 
                    
                </div>
                  
               
                  
              </div><!-- /.card-body -->
            </div>
            <!-- /.card -->

           
            <!-- /.card -->

          
            <!-- /.card -->

            <!-- /.card -->
          </section>
          <!-- right col -->
        </div>
        <!-- /.row (main row) -->
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
 
   <jsp:include page ="footer.jsp"/>