/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sso.userManagement.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode

/**
 *
 * @author aaditya
 */
public class User  implements  Serializable{
    private String userID;
    private String hashCode;
    private String email;
    private boolean active;   
    private boolean emailVerified;
    private String centreCode;   
    private String name;
    private boolean accountVerified;
    private String id;
    private Date createdAt;
    private String requestIpAddress;
    private Date verifiedAt;
    private String approvalIpAddress;
    private boolean migrated;
    
    
}
