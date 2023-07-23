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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sso.userManagement.dao.ConnectionPool;
import sso.userManagement.domain.User;
import sso.userManagement.service.mailService;
import sso.userManagement.service.userService;

/**
 *
 * @author aaditya
 */
public class recoverAccount extends HttpServlet {

     private static final  Logger logger=Logger.getLogger(recoverAccount.class.getName());
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @throws ServletException if a servlet-specific error occurs
     */
     
        @Override
    public void init() throws ServletException {

        logger.info("init() method execution");
        
        //To set the locations, datasource map
        ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
       
        try {
            if (cp == null) {
                cp = ConnectionPool.getInstance();
                getServletContext().setAttribute("connPoolInst", cp);
               // cp = ConnectionPool.setDataSource("localhost", "root", "Rhvh@123", "ssoRegisterGuest", 1);//app connection
           
             cp = ConnectionPool.setDataSource("localhost", "admin", "Sharma@30217", "ssoRegisterGuest", 1);//app connection
           
            
            }

           

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.log(Level.SEVERE,ex.toString(),ex);
        }
    }
    
     
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, Exception {
      
         logger.info("processRequest() method execution");
        
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
                conn=cp.getConn(1);
                 userService service=new userService(conn);
                  mailService mailService = new mailService();
                 String staffCode=request.getParameter("staffCode");
                if(staffCode==null || staffCode.equalsIgnoreCase(""))
                {
                    logger.info("StaffCode can not be Null or Empty");
                    errors.put("email", "StaffCode can not be Null or Empty");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                }
                else
                {
                    if(service.isThisUserIDRegistered(staffCode))
                    {
                              User user=service.getUserObject(staffCode);
                              String token=UUID.randomUUID().toString();
                              String applicationURL = "https://ssoguest.vssc.dos.gov.in/GuestUser/recoverPassword?task=" + token;

                                service.insertRecordForPasswordReset(user, token, request.getRemoteAddr(),true);

                               // mailService mailService = new mailService();
                                String msg = "A password change Request has been generated for your account, Ignore if you have not done that and report to System Administrator, otherwise proceed to click the given link.";
                                
                                mailService.sendMail(user.getEmail(), "Forgot Password SSO ", applicationURL, msg);
                               // request.getRequestDispatcher("jsp/tokensent.jsp").forward(request, response);
                                response.sendRedirect("accountFound");
                    }
                    else if(service.isThisUserIDRegisteredMayNotVerified(staffCode))
                    {
                      //  List<User> objects=service.getAllUnVerifiedAccountAgainstThisUserID(staffCode);
                          User user=service.getUserObject(staffCode);
                        
                      
                             String token=UUID.randomUUID().toString();
                              String applicationURL = "https://ssoguest.vssc.dos.gov.in/GuestUser/recoverPassword?task=" + token;

                                service.insertRecordForPasswordReset(user, token, request.getRemoteAddr(),false);

                               // mailService mailService = new mailService();
                                String msg = "A password change Request has been generated for your account, Ignore if you have not done that and report to System Administrator, otherwise proceed to click the given link.";
                                
                                mailService.sendMail(user.getEmail(), "Forgot Password SSO ", applicationURL, msg);
                            
                        
                        
                       response.sendRedirect("accountFound");
                        
                    }
                    else
                    {
                         request.getRequestDispatcher("jsp/emailNotRegistered.jsp").forward(request, response);
                    }
                }
                 
                 
                 
         }
          catch (SQLException e)
            {
                System.out.println(e.getMessage());
                logger.log(Level.SEVERE,e.toString(),e);
            }
             
        finally
             {
                 try {
                     if(conn!=null)
                     {
                         conn.close();
                     }
                 } catch (SQLException e) {
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
         } catch (SQLException ex) {
             Logger.getLogger(recoverAccount.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(recoverAccount.class.getName()).log(Level.SEVERE, null, ex);
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
             Logger.getLogger(recoverAccount.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(recoverAccount.class.getName()).log(Level.SEVERE, null, ex);
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
