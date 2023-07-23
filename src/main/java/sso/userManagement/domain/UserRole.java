/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sso.userManagement.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author aaditya
 */

@Data
@NoArgsConstructor
@Getter
@Setter

public class UserRole
{
    private int id;
    private String roleName;
    private User user;
    
}
