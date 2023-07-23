<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>SSO::VSSC</title>

  <!-- Google Font: Source Sans Pro -->
 
  <!-- Font Awesome -->
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <!-- icheck bootstrap -->
  <link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/adminlte.min.css">
</head>
<body class="hold-transition register-page">
    <div class="row">
    <div class="col-lg-4">
<div class="register-box">
  <div class="card card-outline card-primary">
    <div class="card-header text-center">
        <img src="img/isro-logo.jpg" width="50%" height="70%">
        <p style="font-weight: bold">VSSC Single Sign On</p>
        <p style="font-weight: bold">You Should only Enter Valid ISRO/DOS EmailID for Registration. </p>
    </div>
      
      
      
      
  </div>
</div>
    </div>
   <div class="col-lg-6" style=" margin-left: 120px">
    <div class="card ">
    <div class="card-body">
      <p class="login-box-msg">This Service is Only for ISRO/DOS Employees Except VSSC/IISU  </p>

      
      <div  id="error"  style="color: red"  >${sessionScope.loginFailed}</div>
      
      <form action="authLogin"  method="post" style="margin-top: 20px">
        <div class="input-group mb-3">
            <input type="text" class="form-control" id="userId" name="userId" placeholder="StaffCode" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-user"></span>
            </div>
          </div>
        </div>
        
          
       
        <div class="input-group mb-3">
            <input type="password" class="form-control" id="password" name="password" placeholder="Password" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-lock"></span>
            </div>
          </div>
        </div>
          
           <div class="row">
               <div class="col-md-4">
                   <button type="button" class="btn btn-primary btn-block" onclick="window.location.href='signup'"   >Sign Up</button>
                   
               </div>
               <div class="col-md-4"></div>
                     <!-- /.col -->
                     <div class="col-4">
                         <button type="submit" class="btn btn-success btn-block">Login</button>
                     </div>
                     <!-- /.col -->
           </div>
          <div class="row" style=" margin-top: 20px">
              
              <div class="col-md-4"></div>
              
               <div class="col-md-6">
                   <button type="button" class="btn btn-danger " onclick="window.location.href='password_lost'"   >Forgot Password</button>
                   
               </div>
              <div class="col-md-2"></div>
              
            
               
           </div>
      
       
      </form>


    
    </div>
    <!-- /.form-box -->
  </div><!-- /.card -->
</div>
    </div>
<!-- /.register-box -->

<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>
</body>
</html>
