/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sso.userManagement.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import sso.userManagement.dao.userDAO;
import sso.userManagement.domain.EmailFilter;
import sso.userManagement.domain.User;
import sso.userManagement.domain.UserRole;

/**
 *
 * @author aaditya
 */
public class userService 
{
    private Connection conn;
    private userDAO dao;
    
    public userService(Connection conn)
    {
        this.conn=conn;
        dao=new userDAO(conn);
    }
    
    public User getUserObject(String userId)throws SQLException{
        return dao.getUserObject(userId);
    }
    
    public UserRole getRole(String userId) throws SQLException
    {
        return dao.getRole(userId);
        
    }
    
    
    public User getAllUnVerifiedAccountAgainstThisUserID(String userId) throws SQLException
    {
        return dao.getAllUnVerifiedAccountAgainstThisUserID(userId);
    }
    
    public void updateEmail(User user,String emailID,String ip,String oneTimeTokenForEmailValidation) throws SQLException, Exception
    {
        dao.updateEmail(user,emailID,ip,oneTimeTokenForEmailValidation);
    }
    public void insertRecordForPasswordReset(User user,String token,String ip,boolean isAcocuntVerified) throws SQLException
    {
        dao.insertRecordForPasswordReset(user,token,ip,isAcocuntVerified);
    }
    
  
    
    public boolean isPasswordResetTokenValid(String token)throws SQLException
    {
        return  dao.isPasswordResetTokenValid(token);
    }
    
    public String getEmailForThisUserID(String userId) throws SQLException
    {
        return dao.getEmailForThisUserID(userId);
    }
    
    public boolean isUserFound(String userId) throws SQLException
    {
        return dao.isUserFound(userId);
    }
    
    
    
    
    public boolean  isTokenValid(String token) throws SQLException
    {
        return  dao.isTokenValid(token);
    }
    
    public boolean isPasswordResetTokenUsed(String token) throws SQLException
    {
        return dao.isPasswordResetTokenUsed(token);
    }
    
    public boolean isTokenUsed(String token) throws SQLException{
        return dao.isTokenUsed(token);
    }
    
    public User getUserObjectFromToken(String token) throws SQLException
    {
        return  dao.getUserObjectFromToken(token);
    }
    
    public User getUserObjectFromPasswordResetToken(String token) throws SQLException
    {
        return dao.getUserObjectFromPasswordResetToken(token);
    }
    
    
    public User UserObjectFromEmailValidationToken(String token) throws SQLException
    {
        return dao.getUserObjectFromEmailValidationToken(token);
    }
    public void updateEmailVerificationFlag(User user,String tokenString,String ip) throws  SQLException
    {
        dao.updateEmailVerificationFlag(user,tokenString,ip);
    }
    
    public boolean isThisEmailRegistered(String emailID) throws SQLException
    {
        return  dao.isThisEmailRegistered(emailID);
    }
      public boolean isThisEmailRegisteredMayNotVerified(String emailID) throws SQLException
    {
        return  dao.isThisEmailRegisteredMayNotVerified(emailID);
    }
    
    public boolean isThisUserIDRegistered(String userID) throws SQLException{
        return dao.isThisUserIDRegistered(userID);
    }
    
    public boolean isThisUserIDRegisteredMayNotVerified(String userID) throws SQLException
    {
        return dao.isThisUserIDRegisteredMayNotVerified(userID);
    }
    
    public boolean isThisEmailFromVSSC(String emailID) throws SQLException
    {
        String parts[]=emailID.trim().split("@");
        return parts[1].equals("vssc.gov.in");
    }
    
    
    public boolean updatePassword(User user,String tokenString,String token,String ipAddress)throws SQLException
    {
        return dao.updatePassword(user,tokenString,token,ipAddress);
    }
    
    
    public boolean updateUserPassword(User user,String hashCode,String ip) throws SQLException
    {
        return dao.updateUserPassword(user,hashCode,ip);
    }
    
    
  public boolean addNewIsroCentreUser(String userID,String name,String email,String password,String ip,String oneTimeTokenForEmailValidation,String centreCode) throws SQLException
  {
      return dao.addNewIsroCentreUser(userID,name,email,password,ip,oneTimeTokenForEmailValidation,centreCode);
  }
    
 public boolean insertRecordForEmailValidation(String userId,String emailID,String token,String ip) throws SQLException
 {
     return dao.insertRecordForEmailValidation(userId,emailID,token,ip);
 }
  
  public String getUserIdForThisEmailID(String email) throws SQLException
  {
      return  dao.getUserIdForThisEmailID(email);
  }
  
  public void saveLog(User user,String ipAddress,String action,String actionDetails) throws SQLException
  {
      dao.saveLog(user,ipAddress,action,actionDetails);
  }
  
    public List<User> getAllUserAccounts() throws SQLException {
        
        return dao.getAllUserAccounts();

    }
    
    public void updateUserAccountStatus(String recordId,boolean accountStatus,String ip,User user) throws SQLException
    {
        dao.updateUserAccountStatus(recordId,accountStatus,ip,user);
    }
  
    
    public User getUserObjectFromRecordId(String recordId) throws SQLException
    {
        return dao.getUserObjectFromRecordId(recordId);
    }
    
    public boolean isThisRecordIdAvailable_And_Verified(String recordId) throws SQLException
    {
        return dao.isThisRecordIdAvailable_And_Verified(recordId);
    }
    
    public boolean migrateUserToSSO_User_Federation_Database(String recordId,String ipAddress,User user) throws SQLException
    {
        return dao.migrateUserToSSO_User_Federation_Database(recordId,ipAddress,user);
    }
    
    public void deleteUserAccount(String rowId,String ip,User user,User admin) throws SQLException
    {
        dao.deleteUserAccount(rowId,ip,user,admin);
    }
    public boolean isThisEmailCanbeSent(String emailID) throws SQLException
    {
        boolean flag=false;
        List<EmailFilter> allowedDomains=dao.getAllowedDomains(); 
        for(EmailFilter domain:allowedDomains)
        {
            if(emailID.contains(domain.getName()))
            {
                 flag=true;
                 break;
                
            }
               
           
        }
         return flag;    
    }
    
    
}
