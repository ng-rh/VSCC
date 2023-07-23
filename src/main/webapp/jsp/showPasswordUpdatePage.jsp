<%-- 
    Document   : passwordReset
    Created on : 12 Oct, 2022, 11:16:16 AM
    Author     : aaditya
--%>

<%@page import="sso.userManagement.domain.User"%>
<jsp:include page="header2.jsp"></jsp:include>

<script>
    
    $(document).ready(function(){
        
       $("#btn2").prop('disabled',true); 
        
    });
    
    
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
                  Password Reset for VSSC SSO Account
                </h3>
               
              </div><!-- /.card-header -->
              <div  id="card1" class="card-body">
                <div class="tab-content p-0">
                    
                    <form id="passwordResetFrom" action="passwordReset" method="POST">
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
                        
                       
                            
                        <div class="col-md-6">
                            <label>New Password: </label>
                            <input type="password" id="password_reset" name="password_reset" class="form-control" autocomplete="off" placeholder="Enter New Password" required="">
                        </div>
                          
                         <div class="col-md-6">
                                 <label>Repeat Password: </label>
                                 <input type="password" id="repeat_password" name="repeat_password" class="form-control" autocomplete="off" placeholder="Enter above password Again" required="">
                                
                          </div>
                    </div>
                    <div class="row" style="margin-top: 20px">
                        <div class="col-md-6">                           
                            <div class="icheck-primary">
                                <input type="checkbox" id="agreeTerms" name="agreeTerms" value="agree"  onchange="document.getElementById('btn2').disabled=!this.checked;"  >
                                <label for="agreeTerms">
                                   I have Generated This Request to update My Password 
                                </label>
                            </div>
                            
                        </div>
                              
                          <div class="col-md-6" style="margin-top: 29px">
                              <button type="submit" class="btn btn-success col-md-4"  id="btn2" disabled=""  >UPDATE</button>
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