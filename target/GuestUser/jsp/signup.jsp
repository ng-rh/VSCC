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
    </div>
  </div>
</div>
    </div>
        <div class="col-lg-6" style=" margin-left: 60px">
    <div class="card">
    <div class="card-body">
      <p class="login-box-msg">This service is only for Non VSSC ISRO/DOS Employees</p>

      <form action="userRegistration" id="signupForm" name="signupForm" method="post">
        <div class="input-group mb-3">
            <input type="text" class="form-control" id="userName" name="userName" placeholder="Name" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-user"></span>
            </div>
          </div>
        </div>
        <div class="input-group mb-3">
            <input type="email" class="form-control" id="emailID" name="emailID" placeholder="Email" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-envelope"></span>
            </div>
          </div>
        </div>
           <div class="input-group mb-3">
               <input type="text" class="form-control" id="staffCode" name="staffCode" placeholder="StaffCode" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-envelope"></span>
            </div>
          </div>
        </div>
          
           <div class="input-group mb-3">
               <input type="text" class="form-control" id="centreCode" name="centreCode" placeholder="Your Centre Name" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-envelope"></span>
            </div>
          </div>
        </div>
          
          
          
          
        <div class="input-group mb-3">
            <input type="password" class="form-control" id="password_guest" name="password_guest" placeholder="Password" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-lock"></span>
            </div>
          </div>
        </div>
        <div class="input-group mb-3">
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Retype password" required="">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-lock"></span>
            </div>
          </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                
                <button type="button" onclick="window.history.go(-1)" class="btn btn-primary">Back</button>
                
            </div>
          <!-- /.col -->
          <div class="col-4">
            <button type="submit" class="btn btn-success btn-block">Register</button>
          </div>
          <!-- /.col -->
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
