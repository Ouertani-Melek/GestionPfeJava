/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ahmed
 */
public class UserServices {
     public static User selectUser(String username) throws SQLException
   {
           ResultSet resultSet = null;
           Connection connection = ConnectionToDataBase.getInstance().getConnection();
           PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user where username = ? ");
           pstmt.setString(1, username);
           resultSet = pstmt.executeQuery();
           if (resultSet == null)
           {
               return new User();
           }
           else 
           {
           User usr = new User();
            while(resultSet.next()) {
                String pass = resultSet.getString("password");
                usr.setPassword(pass);
                usr.setAge(resultSet.getString("age"));
                usr.setNom(resultSet.getString("nom"));
               
            }
          
           return usr;
           }
   }
}
