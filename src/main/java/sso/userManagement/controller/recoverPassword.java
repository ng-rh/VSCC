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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class recoverPassword extends HttpServlet {
      private static  Logger logger=Logger.getLogger(recoverPassword.class.getName());

        
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
            throws ServletException, IOException, SQLException {
        logger.info("processRequest() execution start");        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("X-XSS-Protection", "1;mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies
        response.setHeader("X-Frame-Options", "DENY");
        String tokenString=request.getParameter("task");
        HttpSession session=request.getSession(true);
        Connection conn=null;
        Map<String,String> errors=new HashMap<>();
        
        if(tokenString==null || tokenString.equalsIgnoreCase(""))
        {
            logger.info("token is Null or empty");
            errors.put("action", "Invalid Request");
            request.setAttribute("error", errors);
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
         else
         {
               ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
              try {
                  
                  conn = cp.getConn(1);
                  userService service = new userService(conn);
                   if (service.isPasswordResetTokenValid(tokenString)) {
                       
                       // token is valid 
                       
                         User  user=service.getUserObjectFromPasswordResetToken(tokenString);
                         session.setAttribute("user", user);
                         session.setAttribute("token", tokenString);
                         request.getRequestDispatcher("jsp/showPasswordUpdatePage.jsp").forward(request, response);
                         
                       
                   }
                   else
                   {
                         logger.info("Invalid Request");
                         request.getRequestDispatcher("jsp/invalidToken.jsp").forward(request, response);
                   }
                  
                  
                  
                  
                  
                  
              }
             catch (SQLException e) {
                System.out.println(e.getMessage());
                logger.log(Level.SEVERE,e.toString(),e);
            } finally {

                try {
                    if (conn != null) {
                        conn.close();
                    }

                } catch (SQLException e) {
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
              Logger.getLogger(recoverPassword.class.getName()).log(Level.SEVERE, null, ex);
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
              Logger.getLogger(recoverPassword.class.getName()).log(Level.SEVERE, null, ex);
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
