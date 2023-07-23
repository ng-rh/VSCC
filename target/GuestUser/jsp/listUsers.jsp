<%-- 
    Document   : listUsers
    Created on : 2 Mar, 2023, 3:20:52 PM
    Author     : aaditya
--%>


<%@page import="java.util.List"%>
<%@page import="sso.userManagement.domain.User"%>
<jsp:include page="Sidebar.jsp"></jsp:include>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<style>
    
table.dataTable.dt-checkboxes-select tbody tr,
table.dataTable thead .dt-checkboxes-select-all {
  cursor: pointer;
}

table.dataTable thead .dt-checkboxes-select-all {
  text-align: center;
}

div.dataTables_wrapper span.select-info,
div.dataTables_wrapper span.select-item {
  margin-left: 0.5em;
}

@media screen and (max-width: 640px) {
  div.dataTables_wrapper span.select-info,
  div.dataTables_wrapper span.select-item {
    margin-left: 0;
    display: block;
  }
}
    
</style>




<script>
    
     
    function doThis2(id)
    {
        
         $("#recordId2").val(id);        
         $("#labelID2").text("Delete Account");
         $("#accountAction2").val("");
                          
    }
    
    
    
    function doThis(id,isActive)
    {
        $("#recordId").val(id);
        $("#accountStatus").val(isActive);
        $("#accountAction").val("");
       
        if($("#accountStatus").val()==="true")
        {
              $("#labelID").text("Deactivate Account");
        }
        else  if($("#accountStatus").val()==="false")
        {
             $("#labelID").text("Activate Account");
        }
                          
    }
    
    function completeAction(thisForm)
    {
        if(document.getElementById("accountAction").checked===false)
        {
            alert("You did not Select the checkBox");
            return false;
        }
        
        if(confirm("Are you sure to complete this Action")===true)
        {
            $("#adminAction2").attr('action','Manager?action=completeProcess');
            $("#adminAction2").submit();
        }
        else return false;
    }
    
     
    function completeAction2(thisForm)
    {
        if(document.getElementById("accountAction2").checked===false)
        {
            alert("You did not Select the checkBox");
            return false;
        }
        var table=$("#users").DataTable();
        var selectedRows =[];
        var rows=table.column(0).checkboxes.selected();
        $.each(rows,function(index,rowId){
             
          selectedRows.push({id:rowId});
        
        });
        var rowsToSend = [];
        
        for(var i=0; i< selectedRows.length ; i++)
        {
            rowsToSend[i]= selectedRows[i].id;
                        
        }
        rowsToSend.join(",");
        
        $("#selectedRows2").val(rowsToSend);
        
        if(confirm("Are you sure to Delete this User Account")===true)
        {
            $("#adminAction3").attr('action','Manager?action=accountRemoval');
            $("#adminAction3").submit();
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
                 Account Details
                </h3>
               
              </div><!-- /.card-header -->
              <div  id="card1" class="card-body">
                  
                  <form id="migrateData" name="migrateData" action="" method="POST">
                  <div class="row">
                     
                      <div class="col-md-6"></div>
                      <div class="col-md-6">
                          <input type="hidden" id="selectedRows" name="selectedRows" class="form-control" value="">
                          <button class="btn btn-success" type="button" onclick="migrateUsers(event)">User Migration</button>
                      </div>
                         
                  </div>
                      
                  </form>
                  
                  
                <div class="tab-content p-0">
                    
                   
                <table class="table table-bordered table-hover table-responsive-lg" id="users">
                             <thead class="text-primary">
                                
                                  <tr>
                                   <th>Slno</th>
                                   <th>UserID</th>
                                   <th>Name</th>
                                   <th>Email</th>
                                   <th>centreCode</th>
                                   <th>Migrated</th>
                                    <th>accountVerified</th>
                                   <th>createdAt</th>
                                   
                                   <th>EmailVerified</th>
                                     <th>Action</th>
                                   <th>RequestIpAddress</th>
                                   <th>VerifiedAt</th>
                                   <th>approvalIpAddress</th>
                                  
                                   <th>Active</th>
                                  
                                  
                                   </tr>
                                   </thead>
                                   <tbody>
                                       <% if(session.getAttribute("listUsers")!=null) {                              %>
                                       <% List<User> users=(List<User>)session.getAttribute("listUsers");            %>
                                       <% int slno=1;  %>
                                      <%  for(User user: users) {               %>
                                      
                                      <tr>
                                          <td><%= user.getId()  %></td>
                                          <td><%= user.getUserID()   %></td>
                                          <td><%= user.getName()  %></td>
                                           <td><%= user.getEmail()  %></td>
                                           <td><%= user.getCentreCode()  %></td>
                                           <% if(user.isMigrated()) {    %>
                                           <td style="color:green"> Migrated  </td>
                                           
                                           <%}else { %>
                                           
                                           <td style="color:maroon"> Not Migrated  </td>
                                           <%}%>
                                           <% if(user.isAccountVerified()) {   %>
                                           
                                              <td style="color:green"> YES  </td>
                                           <%} else { %>
                                           
                                              <td style="color:red"> NO  </td>
                                           <%}%>
                                           <td><%= user.getCreatedAt()  %></td>
                                           
                                            <td><%= user.isEmailVerified()  %></td>
                                     <td> 
                                         <% if(user.getUserID().equals("vs30217")) {  %>
                                         
                                         <%}else {%>
                                         
                                         <% if(user.isActive())   {  %>
                                         <button class="btn btn-primary"  data-toggle="modal" data-target="#modal-primary"   type="button" onclick="doThis('<%= user.getId() %>','<%=user.isActive() %>')" >DActivate</button>  
                                       
                                          <button class="btn btn-danger"  data-toggle="modal" data-target="#modal-danger"   type="button" onclick="doThis2('<%= user.getId() %>')" >Delete</button>  
                                       
                                         <%}else {%>
                                           <button class="btn btn-primary"  data-toggle="modal" data-target="#modal-primary"   type="button" onclick="doThis('<%= user.getId() %>','<%=user.isActive() %>')" >Activate</button>  
                                           <button class="btn btn-danger"  data-toggle="modal" data-target="#modal-danger"   type="button" onclick="doThis2('<%= user.getId() %>')" >Delete</button>  
                                       
                                         <%}%>
                                         
                                         <%}%>
                                     </td>
                                             <td><%= user.getRequestIpAddress()  %></td>
                                               <td><%= user.getVerifiedAt() %></td>
                                                  <td><%= user.getApprovalIpAddress() %></td>
                                                   
                                           <td><%= user.isActive() %></td>
                                          
                                         
                                      </tr> 
                                      
                                      
                                       <% }}%>
                                   </tbody>
                                                        
                        </table>
                                            
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
                                   
            <div class="modal fade" id="modal-danger">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Administrator</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <form id="adminAction3"  method="post" action="">
                    <input type="hidden" id="recordId2" name="recordId2" class="form-control" value="">
                      <input type="hidden" id="selectedRows2" name="selectedRows2" class="form-control" value="">
                   
                        <label>                           
                            <span id="labelID2"  style="color: maroon" ></span>
                        </label>
                    <input type="checkbox" id="accountAction2" name="accountAction2" class="form-control" value="" required="" >
                        
                    </form>
                    
                </div>
                
                
                
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-success" id="btn2" onclick="completeAction2(event);">Submit</button>
              <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
                                          
                                   
                                   
                                   
       <div class="modal fade" id="modal-primary">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Administrator</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <form id="adminAction2"  method="post" action="">
                    <input type="hidden" id="recordId" name="recordId" class="form-control" value="">
                    <input type="hidden" id="accountStatus" name="accountStatus" class="form-control" value="">
                    
                   
                        <label>                           
                            <span id="labelID"  style="color: maroon" ></span>
                        </label>
                    <input type="checkbox" id="accountAction" name="accountAction" class="form-control" value="" required="" >
                        
                    </form>
                    
                </div>
                
                
                
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-success" id="btn2" onclick="completeAction(event);">Submit</button>
              <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
                                   
                                   
                                   
        <!-- /.row (main row) -->
      </div><!-- /.container-fluid -->
    </section>
                                   
    
                                       
  
                                   
                                   
    <!-- /.content -->
  </div>
 
   <jsp:include page ="footer.jsp"/>
   
   <script>
  
  $(document).ready(function() {
    example=$("#users").DataTable({
        'columnDefs': [
           
            {
                'targets': [0],
                'data': 0,
                'checkboxes': true
            }
           
        ],
        
        order: [
       [1,'asc']
   ],
    "responsive": true, "lengthChange": true, "autoWidth": true,
      "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]
    }).buttons().container().appendTo('#users_wrapper .col-md-6:eq(0)');
    

   
    
   
  });
  
  
   function migrateUsers(thisEvent)
    {
        var table=$("#users").DataTable();
        var selectedRows =[];
        var rows=table.column(0).checkboxes.selected();
        $.each(rows,function(index,rowId){
             
          selectedRows.push({id:rowId});
        
        });
        var rowsToSend = [];
        
        for(var i=0; i< selectedRows.length ; i++)
        {
            rowsToSend[i]= selectedRows[i].id;
                        
        }
        rowsToSend.join(",");
        
       $("#selectedRows").val(rowsToSend);
       if(rowsToSend.length<=0)
       {
           alert("You have not Selected any Row to Perform Migration");
           return false;
       }
       else
       {
           if(confirm("Are you sure to perform User Migration for SSO User Federation")===true)
           {
              
               $("#migrateData").attr('action',"Manager?action=userMigration");
               $("#migrateData").submit();
               
           }
           else return false;
       }
        
       
      
    }
    
    
</script>
