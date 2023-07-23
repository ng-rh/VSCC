/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package sso.userManagement.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sso.userManagement.dao.ConnectionPool;
import sso.userManagement.domain.User;
import sso.userManagement.service.userService;

/**
 *
 * @author aaditya
 */
public class PasswordManager extends HttpServlet {
     private static  Logger logger=Logger.getLogger(PasswordManager.class.getName());

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
        response.setHeader("X-XSS-Protection", "1;mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies
        response.setHeader("X-Frame-Options", "DENY");
        HttpSession session=request.getSession(false);
        if(session==null || session.getAttribute("user")==null)
        {
            logger.info("session is Null");
            response.sendRedirect("Login");
        }
        
        else {
            ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
            Connection conn = null;
            try {

                conn = cp.getConn(1);
                Map<String, String> errors = new HashMap<>();
                User user=(User)session.getAttribute("user");
                userService service = new userService(conn);
                String action = request.getParameter("action");
                if (action == null || action.equals("")) {
                    errors.put("invalidAction", "Action is Invalid");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                } else {
                    switch (action) {
                        case "view":

                           
                            request.getRequestDispatcher("jsp/showPasswordChangePage.jsp").forward(request, response);
                            break;
                        case "completeProcess":
                            
                            String recordId=request.getParameter("recordId");
                            String accountStatus=request.getParameter("accountStatus");
                            if(recordId==null || recordId.equals(""))
                            {
                                errors.put("recordId", "Record is not Found");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            }
                            if(accountStatus==null || accountStatus.equals(""))
                            {
                                errors.put("accountInfo", "Action to be Taken is not Found");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            }
                            service.updateUserAccountStatus(recordId,Boolean.parseBoolean(accountStatus),request.getRemoteAddr(),(User)session.getAttribute("user"));
                            response.sendRedirect("Manager?action=listUsers");
                            break;
                            
                        case "updatePassword":
                            String currentPassword=request.getParameter("password_current");
                            String newPassword=request.getParameter("password_reset");
                            String newPasswordRepeat=request.getParameter("repeat_password");
                            if(currentPassword==null || currentPassword.equals(""))
                            {
                                errors.put("passwordError", "Your current Password is Either Empty or Null");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            }
                            if(newPassword==null || newPassword.equals(""))
                            {
                                errors.put("passwordError2", "Your New Password is Either Empty or Null");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            }
                             if(newPasswordRepeat==null || newPasswordRepeat.equals(""))
                            {
                                errors.put("passwordError3", "Your Repeat Password is Either Empty or Null");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            }
                             if(!newPassword.equals(newPasswordRepeat))
                             {
                                errors.put("passwordError4", "Password Mismatch Error");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                             }
                             
                             checkPasswordPolicy(newPassword,service,errors,request,response);
                             
                               // now update the password
                    SecureRandom random=SecureRandom.getInstance("SHA1PRNG", "SUN");
                    
                     BCrypt.Result result=BCrypt.verifyer().verify(currentPassword.toCharArray(),user.getHashCode());
                       if(result.verified)
                       {
                           
                       }
                       else
                       {
                                errors.put("passwordError5", "Your Current Password is Wrong ");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                                return;
                       }
                    
                    String hashString=BCrypt.with(random).hashToString(12, newPassword.toCharArray());
                    boolean status=service.updateUserPassword((User)session.getAttribute("user"),hashString,request.getRemoteAddr());
                    if(status)
                    {
                          response.sendRedirect("PasswordManager?action=actionComplete");
                    }
                    else
                    {
                         logger.info("Encryption operation Failed");
                         errors.put("error", "Operation Failed");
                        request.setAttribute("error", errors);
                        request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    }
                  
                            
                            
                            
                            break;
                            
                        case "actionComplete":
                            session.invalidate();
                            request.getRequestDispatcher("jsp/passwordUpdateComplete.jsp").forward(request, response);
                            
                            break;
                            
                        default:
                            errors.put("invalidAction", "Action is Invalid");
                            request.setAttribute("error", errors);
                            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);

                    }

                }

            } catch (IOException | SQLException | ServletException e) 
            {
                 System.out.println(e.getMessage());
                  logger.log(Level.SEVERE, e.toString(), e);
            }

        }

        
        
        
        
    }
    
    
    
      private void checkPasswordPolicy(String password, userService service, Map<String, String> error, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String SPECIAL_CHARACTERS = "!,#,$,%,^,&,*,@";
        password = password.replaceAll("\\s+", "");
        if (password.length() < 8 && password.length() > 15) {
            error.put("password_policy", "password length must be betweeb 8 to 15");
            request.setAttribute("error", error);
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            return;
        }
        if (service.isThisUserIDRegisteredMayNotVerified(password)) {
            error.put("password_policy", "password can not be same as userId");
            request.setAttribute("error", error);
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            return;
        }
        if (service.isThisEmailRegisteredMayNotVerified(password)) {
            error.put("password_policy", "password can not be same as EmailID");
            request.setAttribute("error", error);
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            return;
        }

        final String password_pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,14}$";
        Pattern pattern = Pattern.compile(password_pattern);
        Matcher matcher = pattern.matcher(password);
        if (matcher.find() == false) {
            error.put("password_policy", "password policy fail");
            error.put("length", "Length must be between 8 to 14 characters)");
            error.put("upper_case", "must have atleast 1 upper case");
            error.put("lower_case", "must have at least 1 lower case");
            error.put("digit", "must have atleast 1 digit");
            error.put("special_chars", "must have atleast 1 special symbol among[@$!%*?&]");
            request.setAttribute("error", error);
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            
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
             Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
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
             Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
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

}


