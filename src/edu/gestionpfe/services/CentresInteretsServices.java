/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.CentresInterets;
import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Langues;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahia
 */
public class CentresInteretsServices {

    public void ajouterCentre(ArrayList<CentresInterets> centres) {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();

        String sql = "INSERT INTO centres_interets"
                + "(idcv,centreInteret) VALUES"
                + "(?,?)";
        for (CentresInterets s : centres) {
            try {
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setInt(1, s.getIdCv());
                pst.setString(2, s.getCentreInteret());

                pst.execute();
            } catch (SQLException ex) {
                System.out.println("centresInteret" + ex.getMessage());;
            }
        }
    }
public List<CentresInterets> AfficherCentre(int id) {
         List<CentresInterets> centre=new ArrayList<>();
         
         try {
             
             
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             
             String sql = "select * from centres_interets where idcv="+id;
             
             
             PreparedStatement pst=connection.prepareStatement(sql);
             
             ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            CentresInterets l =new CentresInterets();
            l.setId(resultSet.getInt("id"));
            l.setIdCv(resultSet.getInt("idcv"));
            l.setCentreInteret(resultSet.getString("centreInteret"));
         //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
           
            centre.add(l);
        }
             
             
             
         } catch (SQLException ex) {
             System.out.println("Affichage Langue annulé\n"+ex.getMessage());
         }
         
        return centre;
    }
 public void updateCentre(int id,String f){
         try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             String req="update centres_interets set centreInteret=? where id=?";
             PreparedStatement pst = connection.prepareStatement(req);
             pst.setString(1,f);
             pst.setInt(2, id);
             pst.execute();
         } catch (SQLException ex) {
             Logger.getLogger(FormationServices.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
