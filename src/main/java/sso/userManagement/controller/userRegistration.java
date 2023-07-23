/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package sso.userManagement.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sso.userManagement.dao.ConnectionPool;
import sso.userManagement.service.mailService;
import sso.userManagement.service.userService;

/**
 *
 * @author aaditya
 */
public class userRegistration extends HttpServlet {
    
     private static  Logger logger=Logger.getLogger(userRegistration.class.getName());
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        
        logger.info("processRequest() method execution");
        
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("X-XSS-Protection", "1;mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies
        response.setHeader("X-Frame-Options", "DENY");
        Connection conn=null;
        ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
        Map<String,String> errors=new HashMap<>();
        try {
            
              errors.clear();
              conn=cp.getConn(1);
              userService service=new userService(conn);
               final String VALID_EMAIL_PATTERN="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"; 
               String userID=request.getParameter("staffCode");
               String userName=request.getParameter("userName");
               String emailID=request.getParameter("emailID");             
               String password=request.getParameter("password_guest");
               String repeatPassword=request.getParameter("confirmPassword");
               String centreCode = request.getParameter("centreCode");
               
               if(emailID==null || emailID.equals(""))
               {
                   logger.info("EmailID is either Null or empty");
                    errors.put("email", "EmailID is either Null or empty");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
               }
               else
               {
                    Pattern pattern=Pattern.compile(VALID_EMAIL_PATTERN,Pattern.CASE_INSENSITIVE);
                    Matcher matches=pattern.matcher(emailID);
                    if(matches.matches()==false)
                    {
                       logger.info("EmailID is not valid");
                        errors.put("email", "EmailID is not valid");
                         request.setAttribute("error", errors);
                         request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                         return;
                    }
               }
               
             /* if(service.isThisEmailFromVSSC(emailID))
              {
                    errors.put("email", "This Service is not for VSSC Employees.");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
              }*/
              if (service.isThisEmailRegisteredMayNotVerified(emailID)) // check if this email is Registered and Verified
              {
                   logger.info("This EmailID is already registered ");
                   errors.put("email", "This EmailID is already registered");
                   request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
              }
              
             
               if(userID==null || userID.equals("") || userID.length()<4 || userID.length()>8)
               {
                    logger.info("staffCode is either Null/Empty/length is not between [4-8] characters");
                   errors.put("staffCode", "staffCode is either Null/Empty/length is not between [4-8] characters");
                   request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
               }
               else if(service.isThisUserIDRegisteredMayNotVerified(userID))  // check if this UserId is registered and email verified
               {
                    logger.info("This staffCode is Already registered");
                    errors.put("staffCode", "This staffCode is Already registered");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
               }
               
               
               if(userName==null || userName.equals("") || userName.length()<3 || userName.length()>30)
               {
                    logger.info("userName is either Null/Empty/length is not between [3-30] characters");
                   errors.put("userName", "userName is either Null/Empty/length is not between [3-30] characters");
                   request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
               }
               
               if(centreCode==null || centreCode.equals("") || centreCode.length()<3 || centreCode.length()>15)
               {
                   logger.info("Centre Name is either Null/Empty/length is not between [3-15] characters");
                   errors.put("userName", "Centre Name is either Null/Empty/length is not between [3-15] characters");
                   request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
               }
               
             
              // check if email belongs to @vssc.gov.in
              
              if(service.isThisEmailCanbeSent(emailID)==false)
              {
                    logger.info("This EmailID is not Valid");
                    errors.put("email", "This EmailID is not Valid");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
              }
             
              
              
               if(password==null || password.equals(""))
               {
                    logger.info("password is either Null or Empty");
                    errors.put("password", "password is either Null or Empty");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
               }
                             
               else if(repeatPassword==null || repeatPassword.equals("") || !password.equals(repeatPassword))
               {
                   logger.info("RetypePassword is either Null or Empty or do not match");
                    errors.put("repeatPassword", "RetypePassword is either Null or Empty or do not match");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    return;
               }
               checkPasswordPolicy(password,service,errors,request,response);
               
                
                if(errors.isEmpty())
                {
                    String oneTimeTokenForEmailValidation=UUID.randomUUID().toString();
                    boolean flag=service.addNewIsroCentreUser(userID,userName,emailID,password,request.getRemoteAddr(),oneTimeTokenForEmailValidation,centreCode);
                    if(flag)
                    {
                           // String applicationURL =  "https://ssoguest.vssc.dos.gov.in/GuestUserManagementVer2/verifyEmail?task=" + oneTimeTokenForEmailValidation;
                            
                          String applicationURL =  "https://ssoguest.vssc.dos.gov.in/GuestUser/verifyEmail?task=" + oneTimeTokenForEmailValidation;
                            
                          
                        mailService mailService = new mailService();
                            
                            String msg="A request has been Generated to  create an account for you, ignore if you have not generated this Email, otherwise proceed to click the given link.";
                            mailService.sendMail(emailID, "Email Verification ", applicationURL,msg);
                            response.sendRedirect("registerSuccess");
                            //request.getRequestDispatcher("jsp/registerSuccess.jsp").forward(request, response);
                    }
                }
                else
                {
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                }
                
            
        } catch (IOException | SQLException | ServletException e) 
            {
                System.out.println(e.getMessage());
                logger.log(Level.SEVERE,e.toString(),e);
            }
             finally
             {
                 try
                 {
                     if(conn!=null)
                     {
                         conn.close();
                     }
                 }
                 catch(SQLException e)
                 {
                     System.out.println(e.getMessage());
                     logger.log(Level.SEVERE,e.toString(),e);
                 }
             }
           
            
        
        
        
        
        
        
        
        
        
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(userRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(userRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    
   private void checkPasswordPolicy(String password,userService service,Map<String,String> error,HttpServletRequest request,HttpServletResponse response)throws Exception
   {
       final String SPECIAL_CHARACTERS="!,#,$,%,^,&,*,@";       
       password=password.replaceAll("\\s+","");
       if(password.length()<8 && password.length()>15)
       {
           error.put("password_policy", "password length must be betweeb 8 to 15");
           request.setAttribute("error", error);
           request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
           return;
       }
       if(service.isThisUserIDRegisteredMayNotVerified(password))
       {
           error.put("password_policy", "password can not be same as userId");
           request.setAttribute("error", error);
           request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
           return;
       }
       if(service.isThisEmailRegisteredMayNotVerified(password))
       {
            error.put("password_policy", "password can not be same as EmailID");
           request.setAttribute("error", error);
           request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
           return;
       }
       
      final String password_pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,14}$";
      Pattern pattern = Pattern.compile(password_pattern);
      Matcher matcher = pattern.matcher(password);
      if(matcher.find()==false)
      {
           error.put("password_policy", "password policy fail");
           error.put("length", "Length must be between 8 to 14 characters)");
           error.put("upper_case", "must have atleast 1 upper case");
           error.put("lower_case", "must have at least 1 lower case");
           error.put("digit", "must have atleast 1 digit");
           error.put("special_chars", "must have atleast 1 special symbol among[@$!%*?&]");
           request.setAttribute("error", error);
           request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
           return;
      }
      
       
       
     /*  Pattern pattern = Pattern.compile(".+[A-Z]+");
       Matcher matcher = pattern.matcher(password);
       if (matcher.find() == false) {
           error.put("password_policy", "password must have aleast 1 upper case letter");
           request.setAttribute("error", error);
           request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
           return;
       }
       
        pattern = Pattern.compile(".+[a-z]+");
        matcher = pattern.matcher(password);
        if(matcher.find()==false){
           error.put("password_policy", "password must have aleast 1 lower case letter");
           request.setAttribute("error", error);
           request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
           return;
       }
        pattern = Pattern.compile(".+[1-9]+");
        matcher = pattern.matcher(password);
        if(matcher.find()){
        error.put("password_policy", "password must have aleast 1  digit ");
        request.setAttribute("error", error);
        request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        return;
    }
       pattern = Pattern.compile(".+[!#$%^&*@]+");
       matcher = pattern.matcher(password);
       if (matcher.find()) {

           error.put("password_policy", "password must have aleast 1  special symbol out of [" + SPECIAL_CHARACTERS + "]");
           request.setAttribute("error", error);
           request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
           return;
          
       }
*/
     
     
     
     
       
       
   }
    
    
    
}
