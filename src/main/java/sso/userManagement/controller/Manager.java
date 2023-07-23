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
public class Manager extends HttpServlet {
    
     private static  Logger logger=Logger.getLogger(Manager.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, SQLException, SQLException, SQLException {
       
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
                userService service = new userService(conn);
                String action = request.getParameter("action");
                if (action == null || action.equals("")) {
                    errors.put("invalidAction", "Action is Invalid");
                    request.setAttribute("error", errors);
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                } else {
                    switch (action) {
                        case "listUsers":
                            session.setAttribute("listUsers", service.getAllUserAccounts());
                            request.getRequestDispatcher("jsp/listUsers.jsp").forward(request, response);
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
                        case "userMigration":
                            
                            String selectedRows=request.getParameter("selectedRows");
                            if(selectedRows==null || selectedRows.equals(""))
                            {
                                errors.put("userMigrationAction", "Action to be Taken is not Found");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            }
                            String rows[]=selectedRows.split(",");
                            // verify whether the accounts are verified or not
                          //  List<User> notMigratedUsers=new ArrayList<>();
                            String notMigratedUsers="";
                            for(String rowId:rows)
                            {
                                User user = service.getUserObjectFromRecordId(rowId);
                               /* if(user.getEmail().contains("@vssc.gov.in"))
                                {
                                    continue;
                                }*/
                               if(user.getUserID().equals("vs30217"))
                               {
                                   continue;
                               }
                               
                                if(service.isThisRecordIdAvailable_And_Verified(rowId))
                                {
                                    
                                    service.migrateUserToSSO_User_Federation_Database(rowId,request.getRemoteAddr(),(User)session.getAttribute("user"));
                                }
                                else
                                {
                                   
                                    notMigratedUsers+=user.getUserID() +"-"+ user.getName()+ "-"+user.getCentreCode() +" Not Migrated";
                                    notMigratedUsers+="\t";

                                }
                            }
                            
                            if(notMigratedUsers.length()>0)
                            {
                                 errors.put("notMigratedUsers", notMigratedUsers);
                                 request.setAttribute("error", errors);
                                 request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            }
                            else
                            {
                                response.sendRedirect("Manager?action=listUsers");
                            }
                            
                            
                            
                            break;
                            
                        case "accountRemoval":
                            selectedRows = request.getParameter("selectedRows2");
                            recordId = request.getParameter("recordId2");
                            if ((recordId == null || recordId.equals(""))&& (selectedRows == null || selectedRows.equals(""))) {
                                errors.put("recordId", "Record is not Found");
                                request.setAttribute("error", errors);
                                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            } else 
                            {
                                if (selectedRows != null && !selectedRows.equals("") ) {
                                    rows = selectedRows.split(",");
                                    for (String rowId : rows) {
                                        User user = service.getUserObjectFromRecordId(rowId);
                                        service.deleteUserAccount(rowId, request.getRemoteAddr(), user, (User) session.getAttribute("user"));

                                    }
                                } else {
                                      User user = service.getUserObjectFromRecordId(recordId);
                                    service.deleteUserAccount(recordId, request.getRemoteAddr(), user, (User) session.getAttribute("user"));


                                }
                                
                                response.sendRedirect("Manager?action=listUsers");

                            }


                            break;
                            
                        default:
                            errors.put("invalidAction", "Action is Invalid");
                            request.setAttribute("error", errors);
                            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                            break;

                    }

                }

            } catch (IOException | SQLException | ServletException e) 
            {
                 System.out.println(e.getMessage());
                  logger.log(Level.SEVERE, e.toString(), e);
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
             Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
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
             Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
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
