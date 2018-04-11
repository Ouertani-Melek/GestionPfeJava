/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.controllers.ConnectionController;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Demandes;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.Technologie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author yahia
 */
public class OffreServices {

    public static List<Offre> SelectOffreByUser() throws SQLException {
        List<Offre> offres = new ArrayList<>();

        String qql = "select * from offre where iduser=" + usr.getId();
        System.out.println(usr.getId());
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(qql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            Offre o = new Offre();
            o.setId(resultSet.getInt("id"));
            o.setTitre(resultSet.getString("titre"));
            o.setDateCreation(resultSet.getDate("dateCreation"));
            o.setEtat(resultSet.getBoolean("etat"));
            o.setIdUser(resultSet.getInt("iduser"));
            o.setDescription(resultSet.getString("description"));
            o.setNbr_demandes(resultSet.getInt("nbr_demandes"));
            offres.add(o);

        }

        return offres;
    }

    public static List<Offre> selectOffres() throws SQLException {
        ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM offre where etat = ?");
        pstmt.setBoolean(1, true);
        List<Offre> offres = new ArrayList<>();
        resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return offres;
        } else {
            while (resultSet.next()) {
                Offre offre = new Offre();
                offre.setTitre(resultSet.getString("titre"));
                offre.setDateCreation(resultSet.getDate("dateCreation"));
                offre.setId(resultSet.getInt("id"));
                offre.setEtat(resultSet.getBoolean("etat"));
                offre.setIdUser(resultSet.getInt("iduser"));
                offre.setDescription(resultSet.getString("description"));
                offre.setNbr_demandes(resultSet.getInt("nbr_demandes"));
                offres.add(offre);
            }
            return offres;
        }
    }
    public static List<Offre> selectOffresFalse() throws SQLException {
        ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM offre where etat = ?");
        pstmt.setBoolean(1, false);
        List<Offre> offres = new ArrayList<>();
        resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return offres;
        } else {
            while (resultSet.next()) {
                Offre offre = new Offre();
                offre.setTitre(resultSet.getString("titre"));
                offre.setDateCreation(resultSet.getDate("dateCreation"));
                offre.setId(resultSet.getInt("id"));
                offre.setEtat(resultSet.getBoolean("etat"));
                offre.setIdUser(resultSet.getInt("iduser"));
                offre.setDescription(resultSet.getString("description"));
                offre.setNbr_demandes(resultSet.getInt("nbr_demandes"));
                offres.add(offre);
            }
            return offres;
        }
    }

    public static List<Offre> selectConnectedUserOffres() throws SQLException {
        ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM offre where iduser = ?");
        pstmt.setInt(1, usr.getId());
        List<Offre> offres = new ArrayList<>();
        resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return offres;
        } else {
            while (resultSet.next()) {
                Offre offre = new Offre();
                offre.setTitre(resultSet.getString("titre"));
                offre.setDateCreation(resultSet.getDate("dateCreation"));
                offre.setId(resultSet.getInt("id"));
                offre.setEtat(resultSet.getBoolean("etat"));
                offre.setIdUser(resultSet.getInt("iduser"));
                offre.setDescription(resultSet.getString("description"));
                offre.setNbr_demandes(resultSet.getInt("nbr_demandes"));
                offres.add(offre);
            }
            return offres;
        }
    }

    public static Offre getOffreById(int id) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM offre where id = ?");
        pstmt.setInt(1, id);
        Offre o = new Offre();
        resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return o;
        } else {
            while (resultSet.next()) {

                o.setTitre(resultSet.getString("titre"));
                o.setDateCreation(resultSet.getDate("dateCreation"));
                o.setId(resultSet.getInt("id"));
                o.setEtat(resultSet.getBoolean("etat"));
                o.setIdUser(resultSet.getInt("iduser"));
                o.setDuree(resultSet.getInt("duree"));
                o.setDescription(resultSet.getString("description"));
                o.setNbr_demandes(resultSet.getInt("nbr_demandes"));
            }
            return o;
        }
    }

    public static void setTrue(int id) throws SQLException {
       
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("UPDATE offre SET etat = ? where id = ?");
        pstmt.setBoolean(1, true);
        pstmt.setInt(1, id);
        
         pstmt.executeUpdate();
        
    }

    public static void insertOffre(List<String> techs, String titre, String desc, int duree, int nbrDemande) throws SQLException {
        int id = 0;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        String query = "INSERT INTO offre(iduser,etat,titre,description,nbr_demandes,duree,dateCreation)"
                + " VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, ConnectionController.getUser().getId());
        pstmt.setBoolean(2, false);
        pstmt.setString(3, titre);
        pstmt.setString(4, desc);
        pstmt.setInt(5, nbrDemande);
        pstmt.setInt(6, duree);
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        pstmt.setString(7, date.toString());
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        TechnologiesServices.insertTechs(techs, id);
    }

    public static void updateOffre(List<String> techs, Offre o) throws SQLException {

        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        String query = "UPDATE offre SET description = ?, titre = ?, etat = ?, duree = ?, nbr_demandes = ? WHERE id = ?";
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, o.getDescription());
        pstmt.setString(2, o.getTitre());
        pstmt.setBoolean(3, o.isEtat());
        pstmt.setInt(4, o.getDuree());
        pstmt.setInt(5, o.getNbr_demandes());
        pstmt.setInt(6, o.getId());
        pstmt.executeUpdate();
        List<Technologie> t = TechnologiesServices.selectTechnologies(o);
        TechnologiesServices.deleteTechs(t);
        TechnologiesServices.insertTechs(techs, o.getId());
    }

    public static void deleteOffre(int id) throws SQLException {

        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        String query = "DELETE FROM offre WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();

    }

    public static Offre seletMaxPostuledOffre() throws SQLException {

        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        String query = "SELECT o.* ,count(d.`id`) FROM `demandes` d, `offre` o where o.`id`=d.`idoffre` and o.`iduser`= ? GROUP by d.`idoffre` LIMIT 1";
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, usr.getId());
        ResultSet resultSet = pstmt.executeQuery();
        Offre o = new Offre();
        while (resultSet.next()) {
            o.setId(resultSet.getInt("id"));
            o.setTitre(resultSet.getString("titre"));
            o.setDateCreation(resultSet.getDate("dateCreation"));
            o.setEtat(resultSet.getBoolean("etat"));
            o.setIdUser(resultSet.getInt("iduser"));
            o.setDescription(resultSet.getString("description"));
            o.setNbr_demandes(resultSet.getInt("nbr_demandes"));
        }
        return o;
    }

    public static List<Demandes> getDemandesByIdOffre(int i) throws SQLException {

        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        List<Demandes> demandes = new ArrayList<>();

        String sql = "select * from demandes where idoffre =" + String.valueOf(i)+" ORDER BY dateDemande";

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
            java.sql.Time dbSqlTime = resultSet.getTime("dateDemande");
            java.sql.Date dbSqlDate = resultSet.getDate("dateDemande");
            java.sql.Timestamp dbSqlTimestamp = resultSet.getTimestamp("dateDemande");
            d.setDateDemande(dbSqlTimestamp.toLocalDateTime());
            demandes.add(d);

        }
        if (demandes.isEmpty()) {
            Demandes d = new Demandes();
            d.setId(-1);
            demandes.add(d);
            return demandes;

        }
        return demandes;

    }

}
