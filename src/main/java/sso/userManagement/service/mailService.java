/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sso.userManagement.service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author aaditya
 */
public class mailService {
    
    private static final String SMTP_HOST = "";
    private static final String SMTP_USER = "";
    private static final String SMTP_PASSWD = "";
    private static final String SMTP_FROM = "";
    private static final String TIMEOUT = 2 * 60 * 1000 + "";
    
    public mailService()
    {
        
    }
     public void sendMail(String toAddress, String mailSubject, String mailBody,String extraMsg) throws Exception {

        if (toAddress == null || toAddress.equals("")) {
            throw new Exception("Mail Sending Failed: E-mailID is not found in the Server");
        } else {
            // Get system properties
            Properties props = System.getProperties();

            // Setup mail server
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.connectiontimeout", TIMEOUT);
            props.put("mail.smtp.auth", "false");

            //Set Authentication
            Authenticator authenticator = new MyAuthenticator();
            // Get session
            Session session = Session.getDefaultInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_FROM));
            Transport tr = null;

            // Set the to address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress.trim()));
          
            message.setSubject(mailSubject);
            // Set the content
            String message2String=extraMsg+"  <a rel=\"noopener noreferrer\"  href="+mailBody+ ">Click Here </a>";
            
            message.setContent(message2String, "text/html");
            
            // Save Message
            message.saveChanges();

            //Send Message
            tr = session.getTransport("smtp");
            tr.connect(SMTP_HOST, SMTP_USER, SMTP_PASSWD);
            tr.sendMessage(message, message.getAllRecipients());
            // Define message
            message = new MimeMessage(session);
            // Set the from address
            message.setFrom(new InternetAddress(SMTP_FROM));

            tr.close();
        }
    }//sendMail

  private class MyAuthenticator extends Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "";
            String password = "";
            return new PasswordAuthentication(username, password);
        }
    }
}
