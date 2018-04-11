/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.Technologie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ahmed
 */
public class TechnologiesServices {

    public static List<Technologie> selectTechnologies(Offre o) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM technolgies where idoffre = ?");
        List<Technologie> techs = new ArrayList<>();
        pstmt.setInt(1, o.getId());
        resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return techs;
        } else {
            while (resultSet.next()) {
                Technologie tech = new Technologie();
                tech.setId(resultSet.getInt("id"));
                tech.setIdOffre(resultSet.getInt("idoffre"));
                tech.setTech(resultSet.getString("tech"));

                techs.add(tech);
            }
            return techs;
        }
    }

    public static void insertTechs(List<String> techs, int idOffre) {
        techs.stream().forEach(t -> {
            try {
                Connection connection = ConnectionToDataBase.getInstance().getConnection();
                String query = "INSERT into technolgies(idoffre,tech) values(? , ?)";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, idOffre);
                pstmt.setString(2, t);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        });

    }

    public static void deleteTechs(List<Technologie> techs) {
        techs.stream().forEach(t -> {
            try {
                Connection connection = ConnectionToDataBase.getInstance().getConnection();
                String query = "DELETE FROM technolgies WHERE id = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, t.getId());
                
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        });

      
   }
}
