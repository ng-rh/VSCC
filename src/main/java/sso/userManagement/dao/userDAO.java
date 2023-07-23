/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sso.userManagement.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import sso.userManagement.domain.EmailFilter;
import sso.userManagement.domain.User;
import sso.userManagement.domain.UserRole;
import sso.userManagement.service.mailService;

/**
 *
 * @author aaditya
 */
public class userDAO {
    private Connection conn;
    public userDAO(Connection conn)
    {
        this.conn=conn;
    }
    
      final String isThisRecordIdAvailable_And_Verified="select * from Users where id=? and active=? and accountVerified=?";
    
    public boolean isThisRecordIdAvailable_And_Verified(String recordId) throws SQLException
    {
          // List<User> users=new ArrayList<>();
        PreparedStatement stmt=conn.prepareStatement(isThisRecordIdAvailable_And_Verified);
        stmt.setString(1, recordId);
        stmt.setBoolean(2, true);
        stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        return rs.next();
               
    }
    
    
    
    
    final String getUserObjectFromRecordId="select * from Users where id=?";
    public User getUserObjectFromRecordId(String recordId) throws SQLException
    {
        // List<User> users=new ArrayList<>();
        PreparedStatement stmt=conn.prepareStatement(getUserObjectFromRecordId);
        stmt.setString(1, recordId);
       
       // stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            User user=new User();
            user.setUserID(rs.getString("userId"));
            user.setActive(rs.getBoolean("active"));
            user.setCentreCode(rs.getString("centreCode"));
            user.setEmail(rs.getString("email"));
            user.setEmailVerified(rs.getBoolean("emailVerified"));          
            user.setHashCode(rs.getString("password"));           
            user.setName(rs.getString("name"));
            user.setAccountVerified(rs.getBoolean("accountVerified"));
            return user;
            //users.add(user);
           
        }
        return null;
    }
    
  
    
    
    
    
    final String getAllUnVerifiedAccountAgainstThisUserID="select * from Users where userId=? and active=?";
    public User getAllUnVerifiedAccountAgainstThisUserID(String userId) throws SQLException
    {
       // List<User> users=new ArrayList<>();
        PreparedStatement stmt=conn.prepareStatement(getAllUnVerifiedAccountAgainstThisUserID);
        stmt.setString(1, userId);
        stmt.setBoolean(2, true);
       // stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            User user=new User();
            user.setUserID(rs.getString("userId"));
            user.setActive(rs.getBoolean("active"));
            user.setCentreCode(rs.getString("centreCode"));
            user.setEmail(rs.getString("email"));
            user.setEmailVerified(rs.getBoolean("emailVerified"));          
            user.setHashCode(rs.getString("password"));           
            user.setName(rs.getString("name"));
            user.setAccountVerified(rs.getBoolean("accountVerified"));
            return user;
            //users.add(user);
           
        }
        return null;
        
    }
    
    final String getRole="select * from UserRole where userId=?"; 
    public UserRole getRole(String userId) throws  SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(getRole);
        stmt.setString(1, userId);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            UserRole role=new UserRole();
            role.setId(rs.getInt("id"));
            role.setRoleName(rs.getString("name"));
            return role;
        }
        else return null;
        
    }
    
    
    
    
    
    final String getUserObject="select * from Users where userId=? and active=? and accountVerified=?";
    public User getUserObject(String userId) throws SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(getUserObject);
        stmt.setString(1, userId);
        stmt.setBoolean(2, true);
        stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            User user=new User();
            user.setUserID(rs.getString("userId"));
            user.setActive(rs.getBoolean("active"));
            user.setCentreCode(rs.getString("centreCode"));
            user.setEmail(rs.getString("email"));
            user.setEmailVerified(rs.getBoolean("emailVerified"));          
            user.setHashCode(rs.getString("password"));           
            user.setName(rs.getString("name"));
            user.setAccountVerified(rs.getBoolean("accountVerified"));
            return user;
        }
        else return null;
        
    }
     final String createEmailVerificationToken="insert into emailValidation(id,userId,email,createdAt,token,request_ip_address) values(?,?,?,now(),?,?)";
  
       public boolean  insertRecordForEmailValidation(String userId,String emailID,String token,String ipAddress) throws SQLException
       {
           PreparedStatement stmt3 = conn.prepareStatement(createEmailVerificationToken);
           stmt3.setString(1, UUID.randomUUID().toString());
           stmt3.setString(2, userId);
           stmt3.setString(3, emailID);
           stmt3.setString(4, token);
           stmt3.setString(5, ipAddress);
           stmt3.executeUpdate();
           return true;
       }
    
    
    
    
   final String insertRecordForPasswordReset="insert into forgotPassword(id,userId,token,createdAt,requestIpAddress,email,isAccountVerified) values(?,?,?,now(),?,?,?)";
    
    public void insertRecordForPasswordReset(User user,String token,String ipAddress,boolean isAcocuntVerified) throws SQLException
    {
        
        try {
            
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(insertRecordForPasswordReset);   
            String recordId=UUID.randomUUID().toString();
            stmt.setString(1, recordId);
        
            stmt.setString(2,user.getUserID());         
            stmt.setString(3, token);           
            stmt.setString(4, ipAddress);
            stmt.setString(5, user.getEmail());
            stmt.setBoolean(6, isAcocuntVerified);
            stmt.executeUpdate();            
             
           stmt=conn.prepareStatement(addUserRegistrationLog);         
           stmt.setString(1, ipAddress);
           stmt.setString(2, "Forgot Password");
           stmt.setString(3, "New Request to Reset Password");
           stmt.setString(4, user.getUserID());
           stmt.setString(5, user.getEmail());
           stmt.setString(6, recordId);
           stmt.executeUpdate();             
           conn.commit();
                        
            
        } 
        catch (SQLException e)
        {
            conn.rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            conn.setAutoCommit(true);
        }
          
    }
    
    
    
    final String updateEmail="update Users set email=?, token=?, emailVerified=?, requestIpAddress=? where userId=? and email=? and emailVerified=? and active=?";
   // final String createEmailVerificationToken="insert into emailValidation(id,userId,email,createdAt,token,request_ip_address) values(?,?,?,now(),?,?)";
    public void updateEmail(User user,String emailID,String ip,String oneTimeTokenForEmailValidation) throws SQLException, Exception
    {
        
        try {
            conn.setAutoCommit(false);
            
                         
            PreparedStatement stmt = conn.prepareStatement(updateEmail);
            stmt.setString(1, emailID);
            stmt.setString(2, oneTimeTokenForEmailValidation);
            stmt.setBoolean(3, false);
            stmt.setString(4,ip);
            stmt.setString(5, user.getUserID());
            stmt.setString(6, user.getEmail());
            stmt.setBoolean(7, true);
            stmt.setBoolean(8, true);
            stmt.executeUpdate();
            
               String recordId="";
               stmt = conn.prepareStatement(fetchRecordId);
               stmt.setString(1, oneTimeTokenForEmailValidation);
               stmt.setBoolean(2, false);
               stmt.setBoolean(3, true);
               ResultSet rs=stmt.executeQuery();
               if(rs.next())
               {
                   recordId=rs.getString("id");
               }
            
              
          stmt=conn.prepareStatement(addUserRegistrationLog);
         
           stmt.setString(1, ip);
           stmt.setString(2, "User Account Verification");
           stmt.setString(3, "Account Verification Pending");
           stmt.setString(4, user.getUserID());
           stmt.setString(5, user.getEmail());
           stmt.setString(6, recordId);
           stmt.executeUpdate();  
            
          /*  stmt=conn.prepareStatement(createEmailVerificationToken);
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, user.getUserID());
            stmt.setString(3,emailID);
            String token=UUID.randomUUID().toString();
            stmt.setString(4, token);           
            stmt.setString(5, ip);
            stmt.executeUpdate(); */
          
            
            // send mail
            
            String applicationURL="http://10.43.64.65:8080/GuestUserManagementVer2/verifyEmail?task="+oneTimeTokenForEmailValidation;
           String msg="A request to update your Email for VSSC Authentication acocunt has been generated , ignore if you have not done that otherwise proceed to click the given link.";
                         
            mailService mailService=new mailService();
            mailService.sendMail(emailID, "Email Verification", applicationURL,msg);
            
            conn.commit();
            
         
            
            
        } catch (SQLException e) 
        {
            conn.rollback();
            System.out.println(e.getMessage());
            throw e;
        }

        finally{
            conn.setAutoCommit(true);
        }
       
    }
       
    final String isPasswordResetTokenUsed="select tokenUsed from forgotPassword where token=?";
    public boolean isPasswordResetTokenUsed(String token) throws SQLException
    {
        boolean isUsed = false;
        PreparedStatement stmt = conn.prepareStatement(isPasswordResetTokenUsed);
        stmt.setString(1, token);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            isUsed=rs.getBoolean("tokenUsed");
        }
        
       return  isUsed;

    }
    
    
    final String isTokenUsed="select verifiedAt from emailValidation where token=?";
    public boolean isTokenUsed(String token) throws SQLException
    {
        boolean isUsed = false;
        PreparedStatement stmt = conn.prepareStatement(isTokenUsed);
        stmt.setString(1, token);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            if(rs.getTimestamp("verifiedAt")==null)
            {
                isUsed=false;
            }
            else
            {
                isUsed=true;
            }
        }
        
       return  isUsed;

    }
    
         
    final String isPasswordResetTokenValid="select * from forgotPassword where token=? and tokenUsed=?";
    public boolean isPasswordResetTokenValid(String token) throws SQLException
    {
        boolean isValid=false;
        PreparedStatement stmt=conn.prepareStatement(isPasswordResetTokenValid);
        stmt.setString(1, token);
        stmt.setBoolean(2, false);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            Timestamp tokenCreationTime=rs.getTimestamp("createdAt");
            Calendar original=Calendar.getInstance();
            original.setTimeInMillis(tokenCreationTime.getTime());
            original.add(Calendar.MINUTE, 10);
            Timestamp limitTimestamp=new Timestamp(original.getTime().getTime());
            
            // get current and compare
            Calendar currentCalendar=Calendar.getInstance();
            Timestamp currentTimestamp=new Timestamp(currentCalendar.getTime().getTime());
            
            isValid = limitTimestamp.after(currentTimestamp);
          
        }
        return isValid;
        
    }
            
            
    
    final String isTokenValid="select * from Users where token=? and emailVerified=? and active=?";
    public synchronized boolean isTokenValid(String token) throws SQLException
    {
        boolean isValid=false;
        PreparedStatement stmt=conn.prepareStatement(isTokenValid);
        stmt.setString(1, token);
        stmt.setBoolean(2, false);
        stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            Timestamp tokenCreationTime=rs.getTimestamp("createdAt");
            Calendar original=Calendar.getInstance();
            original.setTimeInMillis(tokenCreationTime.getTime());
            original.add(Calendar.MINUTE, 60);
            Timestamp limitTimestamp=new Timestamp(original.getTime().getTime());
            
            // get current and compare
            Calendar currentCalendar=Calendar.getInstance();
            Timestamp currentTimestamp=new Timestamp(currentCalendar.getTime().getTime());
            
            isValid = limitTimestamp.after(currentTimestamp);
          
        }
        return isValid;
        
    }
    final String getUserObjectFromPasswordResetToken="select u1.userId,u1.name,u1.email,u1.accountVerified,u1.centreCode,f1.id from forgotPassword f1 join Users u1 on u1.userId=f1.userId and u1.email=f1.email  where f1.token=?";
    public User getUserObjectFromPasswordResetToken(String token) throws SQLException
    {
         PreparedStatement stmt=conn.prepareStatement(getUserObjectFromPasswordResetToken);
        stmt.setString(1, token);
       // stmt.setBoolean(2, false);
      //  stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            User user=new User();
           
            user.setEmail(rs.getString("email"));           
            user.setUserID(rs.getString("userId"));
            user.setName(rs.getString("name"));
            user.setAccountVerified(rs.getBoolean("accountVerified"));
            user.setCentreCode(rs.getString("centreCode"));
            user.setId(rs.getString("id"));
            return user;
        }
        else return null;
        
    }
    
    
    
  
    
     final String getUserObjectFromEmailValidationToken="select userId,name,email,centreCode,emailVerified,active,accountVerified  from Users  where token=? and emailVerified=? and active=? ";
    public User getUserObjectFromEmailValidationToken(String token) throws SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(getUserObjectFromEmailValidationToken);
        stmt.setString(1, token);
        stmt.setBoolean(2, false);
        stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            User user=new User();
            user.setActive(rs.getBoolean("active"));
            user.setCentreCode(rs.getString("centreCode"));
            user.setEmail(rs.getString("email"));
            user.setEmailVerified(rs.getBoolean("emailVerified"));
            user.setUserID(rs.getString("userId"));
            user.setAccountVerified(rs.getBoolean("accountVerified"));
            return user;
        }
        else return null;
    }
    
    
    
    
    
    final String getUserObjectFromToken="select userId from emailValidation where token=?";
    public User getUserObjectFromToken(String token) throws SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(getUserObjectFromToken);
        stmt.setString(1, token);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            return getUserObject(rs.getString("userId"));
        }
        else return null;
        
    }
    
    final String updateEmailVerificationFlag="update Users set emailVerified=?,verifiedAt=now(),approvalIpAddress=?,accountVerified=? where userId=? and token=? and emailVerified=? and active=?";
    final String fetchRecordId="select id from Users where token =? and emailVerified=? and active=?";
    public void updateEmailVerificationFlag(User user,String tokenString,String ipAddress) throws SQLException
    {
        try {
          
              conn.setAutoCommit(false);
               String recordId="";
               PreparedStatement stmt = conn.prepareStatement(fetchRecordId);
               stmt.setString(1, tokenString);
               stmt.setBoolean(2, false);
               stmt.setBoolean(3, true);
               ResultSet rs=stmt.executeQuery();
               if(rs.next())
               {
                   recordId=rs.getString("id");
               }
            
            stmt = conn.prepareStatement(updateEmailVerificationFlag);
            stmt.setBoolean(1, true);
            stmt.setString(2, ipAddress);
            stmt.setBoolean(3, true);
            stmt.setString(4, user.getUserID());
            stmt.setString(5, tokenString);
            stmt.setBoolean(6, false);
            stmt.setBoolean(7, true);
           
          
            stmt.executeUpdate();
          
         
          stmt=conn.prepareStatement(addUserRegistrationLog);
         
           stmt.setString(1, ipAddress);
           stmt.setString(2, "User Account/Email Verification");
           stmt.setString(3, "Account/Email Verification completed");
           stmt.setString(4, user.getUserID());
           stmt.setString(5, user.getEmail());
           stmt.setString(6, recordId);
           stmt.executeUpdate();  
           conn.commit();
            
            
        }
        catch (SQLException e)
        {
           conn.rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            conn.setAutoCommit(true);
        }
        
       
    }
    
    final String isThisUserIDRegistered="select userId from Users where userId=? and emailVerified=? and active=?";
    public boolean isThisUserIDRegistered(String userID) throws SQLException{
        
        boolean isUserIDFound=false;
        PreparedStatement stmt=conn.prepareStatement(isThisUserIDRegistered);
        stmt.setString(1, userID);
        stmt.setBoolean(2, true);
        stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            String userId=rs.getString("userId");          
            isUserIDFound = !(userId==null || userId.equalsIgnoreCase(""));
        }
        return isUserIDFound;
        
        
    }
    
      final String isThisUserIDRegisteredMayNotVerified="select userId from Users where userId=? and active=?";
    public boolean isThisUserIDRegisteredMayNotVerified(String userID) throws SQLException{
        
        boolean isUserIDFound=false;
        PreparedStatement stmt=conn.prepareStatement(isThisUserIDRegisteredMayNotVerified);
        stmt.setString(1, userID);
        stmt.setBoolean(2, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            String userId=rs.getString("userId");          
            isUserIDFound = !(userId==null || userId.equalsIgnoreCase(""));
        }
        return isUserIDFound;
        
        
    }
    
    
    
       
    final String isThisEmailRegisteredMayNotVerified="select email from Users where email=? and active=?";
    public boolean isThisEmailRegisteredMayNotVerified(String emailID) throws SQLException
    {
        boolean isEmailFound=false;
        PreparedStatement stmt=conn.prepareStatement(isThisEmailRegisteredMayNotVerified);
        stmt.setString(1, emailID);
        stmt.setBoolean(2, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            String email=rs.getString("email");          
            isEmailFound = !(email==null || email.equalsIgnoreCase(""));
        }
        return isEmailFound;
    }
    
    
    
    final String isThisEmailRegistered="select email from Users where email=? and emailVerified=? and active=?";
    public boolean isThisEmailRegistered(String emailID) throws SQLException
    {
        boolean isEmailFound=false;
        PreparedStatement stmt=conn.prepareStatement(isThisEmailRegistered);
        stmt.setString(1, emailID);
        stmt.setBoolean(2, true);
        stmt.setBoolean(3, true);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            String email=rs.getString("email");          
            isEmailFound = !(email==null || email.equalsIgnoreCase(""));
        }
        return isEmailFound;
    }
    
    public boolean updateUserPassword(User user,String hashCode,String ipAddress) throws  SQLException
    {
         boolean flag=false;
        try {
            conn.setAutoCommit(false);
            
                 PreparedStatement stmt = conn.prepareStatement(updatePassword);
                 stmt.setString(1, hashCode);
                 stmt.setString(2, user.getUserID());
                 stmt.setString(3, user.getEmail());
                 stmt.setBoolean(4, true);
                 stmt.executeUpdate();
                 
            stmt = conn.prepareStatement(addUserRegistrationLog);
            stmt.setString(1, ipAddress);
            stmt.setString(2, "Password Update");
            stmt.setString(3, "Successfully Updated Password");
            stmt.setString(4, user.getUserID());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getId());
            stmt.executeUpdate();
            conn.commit();
            flag=true;
                 
                 
        }
        catch(SQLException e)
        {
            conn.rollback();
            System.out.println(e.getMessage());
            flag=false;
        }
        finally
        {
            conn.setAutoCommit(true);
        }
            
        return flag;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    final String updatePassword="update Users set password=? where userId=? and email=? and active=?";
    final String markTokenUsed="update forgotPassword set tokenUsed=?,completionTime=now(),requestCompletedIp=? where token=?";
    final String updatePasswordAndVerifyAccount="update Users set password=?,emailVerified=?,verifiedAt=now(),approvalIpAddress=?,accountVerified=? where userId=? and email=? and active=?";
    public boolean updatePassword(User user, String tokenString,String token,String ipAddress) throws SQLException {
       boolean flag=false;
        try {
            conn.setAutoCommit(false);
             if(user.isAccountVerified())
             {
                 PreparedStatement stmt = conn.prepareStatement(updatePassword);
                 stmt.setString(1, tokenString);
                 stmt.setString(2, user.getUserID());
                 stmt.setString(3, user.getEmail());
                 stmt.setBoolean(4, true);
                 stmt.executeUpdate();
                 
                 
                 
            
             }
             else
             {
                 PreparedStatement stmt = conn.prepareStatement(updatePasswordAndVerifyAccount);
                 stmt.setString(1, tokenString);
                 stmt.setBoolean(2, true);
                 stmt.setString(3, ipAddress);
                 stmt.setBoolean(4, true);
                 stmt.setString(5, user.getUserID());
                 stmt.setString(6, user.getEmail());
                 stmt.setBoolean(7, true);
                 stmt.executeUpdate();
             }
            
           
            PreparedStatement stmt2 = conn.prepareStatement(markTokenUsed);
            stmt2.setBoolean(1, true);
            stmt2.setString(2, ipAddress);
            stmt2.setString(3, token);
            stmt2.executeUpdate();    
            
          
                
           stmt2=conn.prepareStatement(addUserRegistrationLog);
         
           stmt2.setString(1, ipAddress);
           if(user.isAccountVerified())
           {
               stmt2.setString(2, "Password Reset");
               stmt2.setString(3, "Password Reset successfully completed");
           }
           else
           {
               stmt2.setString(2, "Account Verification with Password Reset");
               stmt2.setString(3, "Successfully Verified Account and Changed Password");
           }
           
           stmt2.setString(4, user.getUserID());
           stmt2.setString(5, user.getEmail());
           stmt2.setString(6, user.getId());
           stmt2.executeUpdate();      
            
            
            
            conn.commit();
            flag=true;

        } catch (SQLException e)
        {   
            conn.rollback();
            System.out.println(e.getMessage());
        }
        finally{
            conn.setAutoCommit(true);
        }

return  flag;
    }
    
 
    
    
    
    
    

    private final String addNewIsroCentreUser="insert into Users(id,userId,name,password,centreCode,email,token,createdAt,requestIpAddress) values(?,?,?,?,?,?,?,now(),?)";
    private final String addUserRegistrationLog="insert into Log(actionTime,actionIp,action,actionDetails,userId,email,refID) values(now(),?,?,?,?,?,?)";
 
    
   
    public boolean addNewIsroCentreUser(String userID,String name,String email,String password,String ip,String oneTimeTokenForEmailValidation,String centreCode) throws SQLException
   {
       boolean flag=false;
       try {
            conn.setAutoCommit(false);
           String recordId = UUID.randomUUID().toString();
           SecureRandom secureRandom = new SecureRandom();
           String hashString = BCrypt.with(secureRandom).hashToString(10, password.toCharArray());
           PreparedStatement stmt = conn.prepareStatement(addNewIsroCentreUser);
           stmt.setString(1, recordId);
           stmt.setString(2, userID);
           stmt.setString(3, name);
           stmt.setString(4, hashString);
           stmt.setString(5, centreCode);
           stmt.setString(6, email);
           stmt.setString(7, oneTimeTokenForEmailValidation);
           stmt.setString(8, ip);
           stmt.executeUpdate();   
           
           
           stmt=conn.prepareStatement(addUserRegistrationLog);
         
           stmt.setString(1, ip);
           stmt.setString(2, "User Registration");
           stmt.setString(3, "New Request to Register a user");
           stmt.setString(4, userID);
           stmt.setString(5, email);
           stmt.setString(6, recordId);
           stmt.executeUpdate();          
            
                
         conn.commit();
         flag=true;
           
           
       } catch (SQLException e) 
       {
           conn.rollback();
           System.out.println(e.getMessage());
       }
       
       finally{
           conn.setAutoCommit(true);
       }

        return flag;
       
   } 
    
    final String getEmailForThisUserID="select email from Users where userId=?";
    
    public String getEmailForThisUserID(String userId) throws SQLException{
        String email=null;
        PreparedStatement stmt=conn.prepareStatement(getEmailForThisUserID);
        stmt.setString(1, userId);      
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            email=rs.getString("email");          
           
        }
        return email;
        
    }
    
   final String isUserFound="select userId from Users where userId=?";
    public boolean isUserFound(String userId) throws SQLException
    {
         boolean isUserIDFound=false;
        PreparedStatement stmt=conn.prepareStatement(isUserFound);
        stmt.setString(1, userId);       
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            String userID=rs.getString("userId");          
            isUserIDFound = !(userID==null || userID.equalsIgnoreCase(""));
        }
        return isUserIDFound;
    }
    
    final String getUserIdForThisEmailID="select userId from Users where email=?";
    public String getUserIdForThisEmailID(String email)throws SQLException
    {
        String userId=null;
        PreparedStatement stmt=conn.prepareStatement(getUserIdForThisEmailID);
        stmt.setString(1, email);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            userId=rs.getString("userId");
        }
        return userId;
    }
    
    private final String saveLog="insert into Log(actionTime,actionIp,action,actionDetails,userId,email) values(now(),?,?,?,?,?)";
   
    public void saveLog(User user,String ipaddress,String action,String actionDetails) throws  SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(saveLog);
        stmt.setString(1, ipaddress);
        stmt.setString(2, action);
        stmt.setString(3, actionDetails);
        stmt.setString(4, user.getUserID());
        stmt.setString(5, user.getEmail());
        stmt.executeUpdate();
        
    }
    
    final String getAllUserAccounts="select * from Users";
    public List<User> getAllUserAccounts() throws  SQLException
    {
        List<User> users=new ArrayList<>();
        PreparedStatement stmt=conn.prepareStatement(getAllUserAccounts);
        ResultSet rs=stmt.executeQuery();
        while(rs.next())
        {
            User user=new User();
            user.setId(rs.getString("id"));
            user.setUserID(rs.getString("userId"));
            user.setEmail(rs.getString("email"));
            user.setCentreCode(rs.getString("centreCode"));
            user.setEmailVerified(rs.getBoolean("emailVerified"));
            user.setCreatedAt(rs.getTimestamp("createdAt"));
            user.setRequestIpAddress(rs.getString("requestIpAddress"));
            user.setVerifiedAt(rs.getTimestamp("verifiedAt"));
            user.setApprovalIpAddress(rs.getString("approvalIpAddress"));
            user.setAccountVerified(rs.getBoolean("accountVerified"));
            user.setActive(rs.getBoolean("active"));
            user.setName(rs.getString("name"));
            user.setMigrated(rs.getBoolean("migrated"));
            users.add(user);
        }
        return users;
        
    }
    
    final String updateUserAccountStatus="update Users set active=? where id=?";
    public void updateUserAccountStatus(String recordId,boolean accounStatus,String ip,User user) throws SQLException
    {
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt=conn.prepareStatement(updateUserAccountStatus);
            if(accounStatus==true)
            {
                 stmt.setBoolean(1, false);
            }
            else
            {
                 stmt.setBoolean(1, true);
            }
           
            stmt.setString(2, recordId);
            stmt.executeUpdate();
            
            // check if this account has been migrated then deactivate from ssoUserFederationService
             User fetchedUser=new User();
            stmt=conn.prepareStatement(fetchUserInfoFromRecord);
            stmt.setString(1, recordId);
            ResultSet rs=stmt.executeQuery();
            if(rs.next())
            {
                fetchedUser.setUserID(rs.getString("userId"));
                fetchedUser.setName(rs.getString("name"));
                fetchedUser.setCentreCode(rs.getString("centreCode"));
                fetchedUser.setHashCode(rs.getString("password"));
                fetchedUser.setActive(rs.getBoolean("active"));
                fetchedUser.setEmail(rs.getString("email"));
                fetchedUser.setEmailVerified(rs.getBoolean("emailVerified"));
                fetchedUser.setAccountVerified(rs.getBoolean("accountVerified"));                
            }
                      
            Connection userFederationConnection=getConnectionObject();
            final String deactivateAccount="update Users set active=? where userId=? and email=?";
            stmt=userFederationConnection.prepareStatement(deactivateAccount);
           if(accounStatus==true)
            {
                 stmt.setBoolean(1, false);
            }
            else
            {
                 stmt.setBoolean(1, true);
            }
           
           stmt.setString(2, fetchedUser.getUserID());
           stmt.setString(3, fetchedUser.getEmail());
           stmt.executeUpdate();
            
            stmt=conn.prepareStatement(addUserRegistrationLog);
         
           stmt.setString(1, ip);
           if(accounStatus==false)
           {
                  stmt.setString(2, "User Account Activation");
                  stmt.setString(3, "Admin has Activated a User Account");
           }
           else
           {
                  stmt.setString(2, "User Account Deactivation");
                  stmt.setString(3, "Admin has DeActivated a User Account");
           }
        
           stmt.setString(4, user.getUserID());
           stmt.setString(5,user.getEmail());
           stmt.setString(6, recordId);
           stmt.executeUpdate();  
            
            conn.commit();
            
        } catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            conn.setAutoCommit(true);
        }
    }
    
    final String migrateUserToSSO_User_Federation_Database="update Users set migrated=? where id=? and active=? and accountVerified=?";
    final String fetchUserInfoFromRecord="select userId,name,centreCode,password,active,email,emailVerified,accountVerified from Users where id=?";
    final
    
    public boolean migrateUserToSSO_User_Federation_Database(String recordId,String ipAddress,User user) throws SQLException
    {
        boolean success=false;
        try
        {
            conn.setAutoCommit(false);
            User fetchedUser=new User();
            PreparedStatement stmt=conn.prepareStatement(fetchUserInfoFromRecord);
            stmt.setString(1, recordId);
            ResultSet rs=stmt.executeQuery();
            if(rs.next())
            {
                fetchedUser.setUserID(rs.getString("userId"));
                fetchedUser.setName(rs.getString("name"));
                fetchedUser.setCentreCode(rs.getString("centreCode"));
                fetchedUser.setHashCode(rs.getString("password"));
                fetchedUser.setActive(rs.getBoolean("active"));
                fetchedUser.setEmail(rs.getString("email"));
                fetchedUser.setEmailVerified(rs.getBoolean("emailVerified"));
                fetchedUser.setAccountVerified(rs.getBoolean("accountVerified"));                
            }
                      
            
            stmt=conn.prepareStatement(migrateUserToSSO_User_Federation_Database);
            stmt.setBoolean(1, true);
            stmt.setString(2, recordId);
            stmt.setBoolean(3, true);
            stmt.setBoolean(4, true);
            stmt.executeUpdate();
            
            Connection userFederationConnection=getConnectionObject();
            final String moveUserInfoToUserFederationServiceDatabase="insert into Users(id,userId,name,division,centreCode,password,active,userType,email,emailVerified,accountVerified) values(?,?,?,?,?,?,?,?,?,?,?) on duplicate key update active=? ";
            
            stmt=userFederationConnection.prepareStatement(moveUserInfoToUserFederationServiceDatabase);
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, fetchedUser.getUserID());
            stmt.setString(3, fetchedUser.getName());
            stmt.setString(4, fetchedUser.getCentreCode());
            stmt.setString(5, fetchedUser.getCentreCode());
            stmt.setString(6, fetchedUser.getHashCode());
            stmt.setBoolean(7, fetchedUser.isActive());
            stmt.setString(8, "ISRO_EMPLOYEE");
            stmt.setString(9, fetchedUser.getEmail());
            stmt.setBoolean(10, fetchedUser.isEmailVerified());
            stmt.setBoolean(11, fetchedUser.isAccountVerified());
            stmt.setBoolean(12, true);
            stmt.executeUpdate();
            
            stmt=conn.prepareStatement(addUserRegistrationLog);
         
            stmt.setString(1, ipAddress);
            stmt.setString(2, "User Account Migration");
            stmt.setString(3, "Admin has Migrated Account of "+ fetchedUser.getUserID() +""+fetchedUser.getCentreCode() +" to sso User Federation Database");
            stmt.setString(4, user.getUserID());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, recordId);
            stmt.executeUpdate();
            
            conn.commit();
            success=true;           
            
        }
       catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            conn.setAutoCommit(true);
        }
        
        return success;
    }
    
    public static Connection getConnectionObject() 
    {
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db_user = "sso";       //Username in mysql
            String db_pass = "Sharma@30217"; //Password for the user in mysql
            String url = "jdbc:mysql://10.41.25.7/UserFederation";
            connection = DriverManager.getConnection(url, db_user, db_pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
            connection = null;
        }
        return connection;
    }
    final String deleteUserAccount="delete from Users where id=?";
    final String deleteUserAccountFromUserFederationService="delete from Users where userId=? and email=?";
    public void deleteUserAccount(String rowId,String ip,User user,User admin) throws SQLException
    {
        try
        {
            conn.setAutoCommit(false);
            
            PreparedStatement stmt = conn.prepareStatement(deleteUserAccount);
            stmt.setString(1, rowId);
            stmt.executeUpdate();
            Connection userFederationConnection = getConnectionObject();
            stmt = userFederationConnection.prepareStatement(deleteUserAccountFromUserFederationService);
            stmt.setString(1, user.getUserID());
            stmt.setString(2, user.getEmail());
            int c=stmt.executeUpdate();
            stmt=conn.prepareStatement(addUserRegistrationLog);
         
            stmt.setString(1, ip);
            stmt.setString(2, "User Account Deletion");
            stmt.setString(3, "Admin has Deleted Account of "+ user.getUserID() +""+user.getCentreCode() +" to sso User Federation Database");
            stmt.setString(4, admin.getUserID());
            stmt.setString(5, admin.getEmail());
            stmt.setString(6, rowId);
            stmt.executeUpdate();
            if(c==1)
            {
                  conn.commit();
            }
          
        }
        catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
        }
        finally
        {
            conn.setAutoCommit(true);
        }
    }
    
    final String getAllowedDomains="select * from EmailFilter where active=?";
    public List<EmailFilter> getAllowedDomains() throws SQLException
    {
        List<EmailFilter> allowedDomains=new ArrayList<>();
        PreparedStatement stmt=conn.prepareStatement(getAllowedDomains);
        stmt.setBoolean(1, true);
        ResultSet rs=stmt.executeQuery();
        while(rs.next())
        {
            EmailFilter domain=new EmailFilter();
            domain.setId(rs.getInt("id"));
            domain.setName(rs.getString("name"));
            domain.setActive(rs.getBoolean("active"));
            allowedDomains.add(domain);
        }
        return allowedDomains;
    }
    
    
    
    
}
