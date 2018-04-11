/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.controllers.ConnectionController;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Stage;
import edu.gestionpfe.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



/**
 *
 * @author user
 */
public class StagesServices {
  
    
    
    public List<Stage> selectAll() {
        List<Stage> stages=new ArrayList<Stage>();
        String req="select * from stage";
        
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet =statement.executeQuery(req);
            while(resultSet.next()){
                Stage p;
                p = new Stage(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDate(7),resultSet.getDate(8));
                stages.add(p);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(StagesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stages;
    }
    
      
     public List<Stage> selectAllEncadrant() {
        List<Stage> stages=new ArrayList<Stage>();
        String req="select * from stage where idencadrant="+usr.getId();
        
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet =statement.executeQuery(req);
            while(resultSet.next()){
                Stage p;
                p = new Stage(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDate(7),resultSet.getDate(8));
                stages.add(p);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(StagesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stages;
    }

   
    
}
