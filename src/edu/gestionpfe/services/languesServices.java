/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Formation;
import edu.gestionpfe.models.Langues;
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
public class languesServices {
    public void ajouterLangue(ArrayList<Langues> langue)
    {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
             
             String sql = "INSERT INTO langues"
                     + "(idcv,langue,niveau) VALUES"
                     + "(?,?,?)";
           for (Langues s : langue) {
            try {
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setInt(1, s.getIdCv());
                pst.setString(2, s.getLangue());
                pst.setString(3, s.getNiveau());

                pst.execute();
            } catch (SQLException ex) {
                System.out.println("langue\n" + ex.getMessage());;
            }
             }

    }
    public List<Langues> AfficherLangue(int id) {
         List<Langues> langue=new ArrayList<>();
         
         try {
             
             
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             
             String sql = "select * from langues where idcv="+id;
             
             
             PreparedStatement pst=connection.prepareStatement(sql);
             
             ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            Langues l =new Langues();
            l.setId(resultSet.getInt("id"));
            l.setIdCv(resultSet.getInt("idcv"));
            l.setLangue(resultSet.getString("langue"));
         //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            l.setNiveau(resultSet.getString("niveau"));
            langue.add(l);
        }
             
             
             
         } catch (SQLException ex) {
             System.out.println("Affichage Langue annulé\n"+ex.getMessage());
         }
         
        return langue;
    }
    
             public void updateLangue(int id,String f){
         try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             String req="update langues set langue=? where id=?";
             PreparedStatement pst = connection.prepareStatement(req);
             pst.setString(1,f);
             pst.setInt(2, id);
             pst.execute();
         } catch (SQLException ex) {
             Logger.getLogger(FormationServices.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
             public void updateNiveau(int id,String f){
         try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             String req="update langues set niveau=? where id=?";
             PreparedStatement pst = connection.prepareStatement(req);
             pst.setString(1,f);
             pst.setInt(2, id);
             pst.execute();
         } catch (SQLException ex) {
             Logger.getLogger(FormationServices.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
