/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Formation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author yahia
 */
public class FormationServices {
     public void ajouterFormationCv(Formation newFormation) {
        
         
      
            
         try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             
             String sql = "INSERT INTO formations"
                     + "(idcv,formation,annee) VALUES"
                     + "(?,?,?)";
             
             PreparedStatement pst=connection.prepareStatement(sql);
             pst.setInt(1, newFormation.getIdCv());
             pst.setString(2, newFormation.getFormation());
             pst.setDate(3, java.sql.Date.valueOf(newFormation.getAnnee()));
            // pst.setDate(3,java.sql.Date.valueOf(newFormation.getAnnee()));
             
             // pst.setDate(2, newFormation.getAnnee());

             pst.execute();
         } catch (SQLException ex) {
             System.out.println("Essayez encore !");
         }
           
       
     
    
       
    
}
     public List<Formation> AfficherFormation(int id) {
         List<Formation> formation=new ArrayList<>();
         
         try {
             
             
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             
             String sql = "select * from formations where idcv="+id;
             
             
             PreparedStatement pst=connection.prepareStatement(sql);
             
             ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            Formation f =new Formation();
            f.setId(resultSet.getInt("id"));
            f.setIdCv(resultSet.getInt("idcv"));
            f.setFormation(resultSet.getString("formation"));
         //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            f.setAnnee(LocalDate.parse(resultSet.getString("annee")));
            formation.add(f);
        }
             
             
             
         } catch (SQLException ex) {
             System.out.println("Affichage annulé\n"+ex.getMessage());
         }
         
        return formation;
    }
     public void updateFormation(int id,String f){
         try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             String req="update formations set formation=? where id=?";
             PreparedStatement pst = connection.prepareStatement(req);
             pst.setString(1,f);
             pst.setInt(2, id);
             pst.execute();
         } catch (SQLException ex) {
             Logger.getLogger(FormationServices.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
    
              public void updateFormationDate(int id,LocalDate f){
         try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             String req="update formations set annee=? where id=?";
             PreparedStatement pst = connection.prepareStatement(req);
             pst.setDate(1,java.sql.Date.valueOf(f));
             pst.setInt(2, id);
             pst.execute();
         } catch (SQLException ex) {
             Logger.getLogger(FormationServices.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
