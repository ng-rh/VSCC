/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package sso.userManagement.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
import sso.userManagement.domain.UserRole;
import sso.userManagement.service.userService;

/**
 *
 * @author aaditya
 */
public class authLogin extends HttpServlet {
   private static  Logger logger=Logger.getLogger(authLogin.class.getName());

      @Override
    public void init() throws ServletException {
        logger.info("init() method execution");

        //To set the locations, datasource map
        ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
       
        try {
            if (cp == null) {
                cp = ConnectionPool.getInstance();
                getServletContext().setAttribute("connPoolInst", cp);
               //  cp = ConnectionPool.setDataSource("localhost", "root", "Rhvh@123", "ssoRegisterGuest", 1);//app connection
     
                cp = ConnectionPool.setDataSource("localhost", "admin", "Sharma@30217", "ssoRegisterGuest", 1);//app connection
            }

           

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
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
        logger.info("processRequest() method execution");
        Connection conn=null;
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("X-XSS-Protection", "1;mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies
        response.setHeader("X-Frame-Options", "DENY");
        Map<String,String> errors=new HashMap<>();
        String userId=request.getParameter("userId");
        String password=request.getParameter("password");
        if(userId==null || userId.equalsIgnoreCase(""))
        {
            logger.info("UserID can not be Null or Empty");
            errors.put("userId", "UserID can not be Null or Empty");
        }
        if(password==null || password.equalsIgnoreCase(""))
        {
              logger.info("Password can not be Null or Empty");
            errors.put("password", "Password can not be NUll or Empty");
        }
        
        if(errors.isEmpty())
        {
              ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
              HttpSession session=request.getSession(false);
             try {
                 conn=cp.getConn(1);
                 userService service=new userService(conn);
                 User user=service.getUserObject(userId);
                 UserRole role=service.getRole(user.getUserID());
                 if(user!=null)
                 {
                       BCrypt.Result result=BCrypt.verifyer().verify(password.toCharArray(),user.getHashCode());
                       if(result.verified)
                       {
                           logger.info("UserId/password is correct");
                           if (session != null) {
                               session.invalidate();
                               session=request.getSession(true);
                               session.setAttribute("user", user);
                               session.setAttribute("role", role);
                               
                               service.saveLog(user,request.getRemoteAddr(),"Login","Login Success");
                               
                               response.sendRedirect("Home");
                           }
                       }
                       else
                       {
                            logger.info("UserId/password is Invalid");
                           if (session != null) 
                           {
                               session.invalidate();
                           }
                               errors.put("loginFailed", "UserID/password is not correct");
                               request.setAttribute("error", errors);
                               service.saveLog(user,request.getRemoteAddr(),"Login","Login Failed");
                               request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                           
                       }
                 
                 }
                 else{
                     if(session!=null)
                     {
                         session.invalidate();
                     }
                         errors.put("loginFailed", "UserID/password is not correct");
                       //  session.setAttribute("loginFailed", "UserID/password is not correct");
                       //  response.sendRedirect("Login");
                       request.setAttribute("error", errors);
                         request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                     
                    
                 }
                         
                 
            } 
            catch (IOException | SQLException | ServletException e) 
            {
                logger.log(Level.SEVERE,e.toString(),e);
                System.out.println(e.getMessage());
                
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
        
        else
        {
            request.setAttribute("error", errors);
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
          } catch (SQLException ex) {
              Logger.getLogger(authLogin.class.getName()).log(Level.SEVERE, null, ex);
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
              Logger.getLogger(authLogin.class.getName()).log(Level.SEVERE, null, ex);
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
