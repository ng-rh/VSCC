/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sso.userManagement.domain;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

/**
 *
 * @author aaditya
 */
public class ForgotPassword {
    
    private String id;
    private String token;
    private Date createdAt;
    private String requestIpAddress;
    private boolean tokenUsed;
    private String requestCompletedIp;
    private Date completionTime;
    private List<User> users;
    
    
}
