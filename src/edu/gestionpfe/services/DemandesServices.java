/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Demandes;
import edu.gestionpfe.models.Offre;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yahia
 */
public class DemandesServices {

    public List<Demandes> AfficherDemande() {
        List<Demandes> demandes = new ArrayList<>();
        List<Demandes> demandesEntre = new ArrayList<>();

        try {
                
            Connection connection = ConnectionToDataBase.getInstance().getConnection();

            String sql = "select * from demandes";

            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                Demandes d = new Demandes();
                d.setId(resultSet.getInt("id"));
                
                d.setIdOffre(resultSet.getInt("idoffre"));
                
                d.setIdUser(resultSet.getInt("iduser"));
                d.setEtatDemande(resultSet.getBoolean("etatDemande"));
                
                //d.setDateEntretien(LocalDateTime.parse(resultSet.getString("dateEntretien"),DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                d.setEtatEntretien(resultSet.getBoolean("etatEntretien"));
                d.setConfirmation(resultSet.getBoolean("confirmation"));
                d.setMethodeCommunication(resultSet.getString("methodeCommunication"));
                //d.setDateDemande(LocalDateTime.parse(resultSet.getString("dateDemande"),DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                 
                demandes.add(d);
            }
            for(Offre of : OffreServices.SelectOffreByUser())
            {
                int s=of.getId();
                
                demandes.stream().forEach(e->{
                if(e.getIdOffre()==s)
                { 
                    demandesEntre.add(e);
                    System.out.println("sayé");
                 } 
                });
                 break;
                
            }
            

        } catch (SQLException ex) {
            System.out.println("Affichage annulé\n" + ex.getMessage());
        }

        return demandesEntre;
    }
    
    public void ajouterEntretien(int id,LocalDate d) throws SQLException{
    Connection connection = ConnectionToDataBase.getInstance().getConnection();
    String sql = "update demandes set etatDemande=1 , dateEntretien=? where id="+id;
    
    

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setDate(1,java.sql.Date.valueOf(d));
            pst.execute();
    }

}
