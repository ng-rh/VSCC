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
import javax.servlet.http.HttpSession;
import sso.userManagement.dao.ConnectionPool;
import sso.userManagement.domain.User;
import sso.userManagement.service.userService;

/**
 *
 * @author aaditya
 */
public class updateEmail extends HttpServlet {
    
       private static  Logger logger=Logger.getLogger(updateEmail.class.getName());

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
            throws ServletException, IOException {
        
          
        logger.info("processRequest() method execution");
                
        Connection conn=null;
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
            logger.info("session is null");
            response.sendRedirect("Login");
        }
        else
        {
             ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("connPoolInst");
             Map<String,String> errors=new HashMap<>();
            
             try {
                conn=cp.getConn(1);
                userService service=new userService(conn);
                final String VALID_EMAIL_PATTERN="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"; 
                String emailID=request.getParameter("email");
                if(emailID==null || emailID.equalsIgnoreCase(""))
                {
                    logger.info("EmailID can not be Null or Empty");
                    errors.put("email", "EmailID can not be Null or Empty");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                }
                else{
                    Pattern pattern=Pattern.compile(VALID_EMAIL_PATTERN,Pattern.CASE_INSENSITIVE);
                    emailID=emailID.replaceAll("\\s+", "");
                    Matcher matches=pattern.matcher(emailID);
                    if(matches.find())
                    {
                        
                        // check for Uniqueness
                        
                        if (service.isThisEmailCanbeSent(emailID) == false) {
                           logger.info("This EmailID is not Valid");
                           errors.put("email", "This EmailID is not Valid");
                            request.setAttribute("error", errors);
                            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            return;
                        }
             
                        
                        
                        
                        if (service.isThisEmailRegistered(emailID)) 
                        {
                             logger.info("Duplicate EmailID Found");
                            request.getRequestDispatcher("jsp/duplicateEmail.jsp").forward(request, response);
                          

                        } 
                      
                        else 
                        {
                            // now update email id database
                            
                            String oneTimeTokenForEmailValidation=UUID.randomUUID().toString();

                            service.updateEmail((User) session.getAttribute("user"), emailID, request.getRemoteAddr(),oneTimeTokenForEmailValidation);
                            session.invalidate();
                            response.sendRedirect("taskComplete");

                        }


                       
                        
                        
                    }
                    else
                    {
                        // not a valid email
                        errors.put("email", "Entered Email ID is not a valid");   
                        logger.info("Entered Email ID is not a valid");
                        request.setAttribute("error", errors);
                        request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    }
                }
                
                
                
            } catch (Exception e)
            {
                System.err.println(e.getMessage());
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
        processRequest(request, response);
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
        processRequest(request, response);
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
