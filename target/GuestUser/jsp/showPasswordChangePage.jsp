<%-- 
    Document   : showPasswordChangePage
    Created on : 3 Mar, 2023, 11:53:17 AM
    Author     : aaditya
--%>

<%@page import="sso.userManagement.domain.User"%>
<jsp:include page="Sidebar.jsp"></jsp:include>


<script>
    
    function saveForm()
    {
        if(confirm("Do you want to Continue")===true)
        {
             $("#passwordResetFrom").attr('action',"PasswordManager?action=updatePassword");
             $("#passwordResetFrom").submit();
        }
        else return false;
       
    }
    
    
    
    
</script>




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
                  Password Update Page
                </h3>
               
              </div><!-- /.card-header -->
              <div  id="card1" class="card-body">
                <div class="tab-content p-0">
                    
                    <form id="passwordResetFrom" action="" method="POST">
                    <% User user=(User)session.getAttribute("user");                                  %>
                  
                        <input type="hidden" id="actionString" name="actionString" class="form-control" value="${sessionScope.token}"/>
                      
                       <div class="row">
                           <div class="col-md-4">
                        <label>    UserID :-   </label>
                              <%= user.getUserID()  %>
                               
                           </div><!-- comment -->
                           
                            <div class="col-md-4">
                        <label>    Name :-   </label>
                                 <%= user.getName()  %>
                               
                           </div><!-- comment -->
                           
                            <div class="col-md-4">
                        <label>    Centre :-   </label>
                               <%= user.getCentreCode()  %>
                               
                           </div><!-- comment -->
                           
                           
                       </div>
                    
                          <div class="row" style="margin-top: 20px">
                        
                              <div class="col-md-4">
                          
                            <label>Current Password: </label>
                              </div>
                              <div class="col-md-6">
                            <input type="password" id="password_current" name="password_current" class="form-control" autocomplete="off" placeholder="Enter Current Password" required="">
                              </div>
                          </div>
                            
                               <div class="row" style="margin-top: 20px">
                                   <div class="col-md-4">
                                       <label>New Password: </label>
                                   </div>
                                   <div class="col-md-6">
                                       <input type="password" id="password_reset" name="password_reset" class="form-control" autocomplete="off" placeholder="Enter New Password" required="">

                                   </div>
                               </div>
                                    <div class="row" style="margin-top: 20px">

                                       <div class="col-md-4">
                                           <label>Repeat Password: </label>
                                       </div>
                                       <div class="col-md-6">
                                           <input type="password" id="repeat_password" name="repeat_password" class="form-control" autocomplete="off" placeholder="Enter New password Again" required="">


                                       </div>
                                   </div>
                                  
                                   <div class="row" style="margin-top: 20px">

                                       <div class="col-md-6"></div>
                                       
                                       <div class="col-md-6">
                                           <button type="submit" class="btn btn-success col-md-4"  id="btn2" onclick="return saveForm();"  >UPDATE</button>
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