/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.CompetencesTechniques;
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
public class CompetencesTechniquesServices {

    public void ajouterCompetenceCv(ArrayList<CompetencesTechniques> techs) {

        Connection connection = ConnectionToDataBase.getInstance().getConnection();

        String sql = "INSERT INTO competences_techniques"
                + "(idcv,competence,level) VALUES"
                + "(?,?,?)";

        for (CompetencesTechniques s : techs) {
            try {
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setInt(1, s.getIdCv());
                pst.setString(2, s.getCompetence());
                pst.setString(3, s.getLevel());

                pst.execute();
            } catch (SQLException ex) {
                System.out.println("competence technique\n" + ex.getMessage());;
            }

        }
    }
     public List<CompetencesTechniques> AfficherTechs(int id) {
         List<CompetencesTechniques> techs=new ArrayList<>();
         
         try {
             
             
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             
             String sql = "select * from competences_techniques where idcv="+id;
             
             
             PreparedStatement pst=connection.prepareStatement(sql);
             
             ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            CompetencesTechniques l =new CompetencesTechniques();
            l.setId(resultSet.getInt("id"));
            l.setIdCv(resultSet.getInt("idcv"));
            l.setCompetence(resultSet.getString("competence"));
         //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            l.setLevel(resultSet.getString("level"));
            techs.add(l);
        }
             
             
             
         } catch (SQLException ex) {
             System.out.println("Affichage Langue annulé\n"+ex.getMessage());
         }
         
        return techs;
    }
}
