/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package sso.userManagement.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
public class passwordReset extends HttpServlet {
     private static  Logger logger=Logger.getLogger(passwordReset.class.getName());
     

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
            throws ServletException, IOException, SQLException, SQLException, NoSuchAlgorithmException, NoSuchProviderException, Exception {
       
        logger.info("processRequest() method execution");        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("X-XSS-Protection", "1;mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies
        response.setHeader("X-Frame-Options", "DENY");
        Connection conn = null;
        HttpSession session=request.getSession(false);
        
        if(session==null || session.getAttribute("user")==null)
        {
            logger.info("Session not Found");
            response.sendRedirect("Login");
        }
        else{
              ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
        Map<String, String> errors = new HashMap<>();
        try {
            conn = cp.getConn(1);
           
            String token = request.getParameter("actionString");
            String newPassword = request.getParameter("password_reset");
            String newPasswordRepeat = request.getParameter("repeat_password");
            
            userService service=new userService(conn);
            if(token==null || token.equalsIgnoreCase(""))
            {
                    logger.info("token is null or empty");
                    errors.put("emptyToken", "Request is Invalid");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }
            else if(service.isPasswordResetTokenValid(token))
            {
                if(newPassword==null || newPassword.equals("") || newPasswordRepeat==null || newPasswordRepeat.equals(""))
                {
                      logger.info("Password is null or empty");
                    errors.put("Emptypassword", "Entered Password is Null or Empty");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                }
                else if(!newPassword.equalsIgnoreCase(newPasswordRepeat))
                {
                     logger.info("Password do not match");
                    errors.put("password_mismatch", "Entered Password do not match");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                }
                
                else
                {
                      checkPasswordPolicy(newPassword,service,errors,request,response);
                    
                    // now update the password
                    SecureRandom random=SecureRandom.getInstance("SHA1PRNG", "SUN");
                    String hashString=BCrypt.with(random).hashToString(12, newPassword.toCharArray());
                    boolean status=service.updatePassword((User)session.getAttribute("user"),hashString,token,request.getRemoteAddr());
                    if(status)
                    {
                          response.sendRedirect("taskComplete?action=updated");
                    }
                    else
                    {
                         logger.info("Encryption operation Failed");
                        errors.put("error", "Operation Failed");
                        request.setAttribute("error", errors);
                        request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    }
                  
                    
                    
                }
                
            }
            
            else
            {
                 request.getRequestDispatcher("jsp/invalidToken.jsp").forward(request, response);
            }
            
            
            
            
            
            
            
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, e.toString(), e);
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
         } catch (SQLException ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NoSuchAlgorithmException ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NoSuchProviderException ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
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
         } catch (SQLException ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NoSuchAlgorithmException ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NoSuchProviderException ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(passwordReset.class.getName()).log(Level.SEVERE, null, ex);
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

    
    
    
    
    
    
    
    
    
    
}
