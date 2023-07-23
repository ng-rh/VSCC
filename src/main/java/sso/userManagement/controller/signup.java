/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package sso.userManagement.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sso.userManagement.dao.ConnectionPool;

/**
 *
 * @author aaditya
 */
public class signup extends HttpServlet {
 private static  Logger logger=Logger.getLogger(signup.class.getName());
    
   
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
                
                
                String DATABASE_URL=System.getenv("DATABASE_URL");
                String DATABASE_USER=System.getenv("DATABASE_USER");
                String DATABASE_PASSWORD=System.getenv("DATABASE_PASSWORD");
                String DATABASE_NAME=System.getenv("DATABASE_NAME");
                logger.info("Database parameters are provided Dynamically " + DATABASE_URL + " " + DATABASE_USER + " " + DATABASE_PASSWORD+ " " +DATABASE_NAME );
                cp = ConnectionPool.setDataSource(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD, DATABASE_NAME, 1);//app connection
            }

           

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.log(Level.SEVERE,ex.toString(),ex);
            throw ex;
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
        
        logger.info("doGet() method execution");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("X-XSS-Protection", "1;mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies
        response.setHeader("X-Frame-Options", "DENY");
        request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
        
        

    }

}
