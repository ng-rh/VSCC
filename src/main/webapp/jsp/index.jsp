<%-- 
    Document   : index
    Created on : 15 Sep, 2022, 4:10:29 PM
    Author     : aaditya
--%>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>SSO::VSSC </title>

  <!-- Google Font: Source Sans Pro -->

  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <!-- icheck bootstrap -->
  <link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/adminlte.min.css">
  
  <script>
      
    
      
  </script>
  
  
  
  
</head>
<body class="hold-transition login-page">
    
    <div class="row">
        
 <div class="col-lg-4">
<div class="register-box">
  <div class="card card-outline card-primary">
    <div class="card-header text-center">
        <img src="img/isro-logo.jpg" width="50%" height="70%">
        <p style="font-weight: bold">VSSC Single Sign On</p>
    </div>
  </div>
</div>
    </div>
    
  <div class="col-lg-6" style=" margin-left: 90px">
   <div class="login-page">
  <div class="card card-outline card-primary">
    <div class="card-header text-center">
      <a href="#" class="h4"><b>Forgot Password</a>
    </div>
    <div class="card-body">
      <p class="login-box-msg">You forgot your password? Here you can easily retrieve a new password.</p>
      <form action="recoverAccount" method="post">
        <div class="input-group mb-3">
            <input type="text"  name="staffCode" id="staffCode" class="form-control" placeholder="Enter Your StaffCode"   required  autocomplete="off">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-envelope"></span>
            </div>
          </div>
        </div>
        <div class="row">
            <div class="col-md-6"></div>
          <div class="col-6">
            <button type="submit" class="btn btn-success btn-block">Request new password</button>
          </div>
          <!-- /.col -->
        </div>
      </form>
      
       <p class="mt-3 mb-1">
           <button type="button" onclick="window.location.href='Login'" class="btn btn-primary">Go To Login Page</button>
          </p>
    
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
    </div>
    </div>
    

<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>
</body>
</html>