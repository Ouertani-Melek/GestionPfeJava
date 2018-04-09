/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Demandes;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahia
 */
public class DemandesServices {
//public static List<Time> TimeEntretien=new ArrayList<>();   

    public List<Demandes> AfficherDemande() {
        List<Demandes> demandes = new ArrayList<>();
        List<Demandes> demandesEntre = new ArrayList<>();
        List<Offre> lofrres = new ArrayList<>();
        try {

            Connection connection = ConnectionToDataBase.getInstance().getConnection();

            String sql = "SELECT * FROM `demandes` WHERE `etatDemande` IS NULL ORDER BY `idoffre` ASC";

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
            List<Offre> lof = OffreServices.SelectOffreByUser();
            for (Offre of : lof) {
                int s = of.getId();

                demandes.stream().forEach(e -> {
                    if (e.getIdOffre() == s) {
                        demandesEntre.add(e);
                        lofrres.add(of);
                    } else {
                        lof.remove(e);
                    }
                });
                break;

            }

            demandesEntre.stream().forEach(e -> {
                lofrres.forEach(o -> e.settitreOffrePrDemande(o.getId()));

            });

        } catch (SQLException ex) {
            System.out.println("Affichage annulé\n" + ex.getMessage());
        }

        return demandesEntre;
    }

    public List<Demandes> AfficherDemandeAccepte() {
        List<Demandes> demandes = new ArrayList<>();
        List<Demandes> demandesEntre = new ArrayList<>();
        List<Offre> lofrres = new ArrayList<>();
        try {

            Connection connection = ConnectionToDataBase.getInstance().getConnection();

            String sql = "SELECT * FROM `demandes` WHERE `etatDemande`=true and dateEntretien is not null ORDER BY `idoffre` ASC";

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
            List<Offre> lof = OffreServices.SelectOffreByUser();
            for (Offre of : lof) {
                int s = of.getId();

                demandes.stream().forEach(e -> {
                    if (e.getIdOffre() == s) {
                        demandesEntre.add(e);
                        lofrres.add(of);
                    } else {
                        lof.remove(e);
                    }
                });
                break;

            }

            demandesEntre.stream().forEach(e -> {
                lofrres.forEach(o -> e.settitreOffrePrDemande(o.getId()));

            });

        } catch (SQLException ex) {
            System.out.println("Affichage annulé\n" + ex.getMessage());
        }

        return demandesEntre;
    }

    public void ajouterEntretien(int id, Timestamp d, String meth) throws SQLException {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        String sql = "update demandes set etatDemande=1 , dateEntretien=? , methodeCommunication=? where id=" + id;

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setTimestamp(1, d);
        pst.setString(2, meth);
        pst.execute();
    }

    public static String FindTitreOffre(int id) {
        String titre = null;
        try {
            String req = "select titre from Offre where id=" + id;
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                titre = rs.getString("titre");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DemandesServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return titre;
    }

    public void RefuserDemande(int id) {
        try {
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            String sql = "update demandes set etatDemande=false  where id=?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DemandesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AccepterEntretien(int id) {
        try {
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            String sql = "update demandes set etatEntretien=true  where id=?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DemandesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void RefusCandidat(int id) throws SQLException {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        String req = "UPDATE `demandes` SET `etatEntretien` = false WHERE `demandes`.`id` = ?";
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, id);
        pst.executeUpdate();
    }

    public List<Timestamp> getAllEntretienDates(int id) {
        List<Timestamp> datesEntretiens = new ArrayList<>();
        try {
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            String sql = "SELECT `dateEntretien` FROM `demandes` WHERE `dateEntretien` is NOT NULL AND `idoffre`=?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
//           while(rs.next()){
//               datesEntretiens.add(rs.getDate("dateEntretien"));
//               System.out.println(rs.getDate("dateEntretien"));
//           }
            while (rs.next()) {
                java.sql.Time dbSqlTime = rs.getTime("dateEntretien");
                java.sql.Date dbSqlDate = rs.getDate("dateEntretien");
                java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp("dateEntretien");
                System.out.println(dbSqlTimestamp);
                // System.out.println(dbSqlTime);
                //System.out.println(dbSqlDate);
                //TimeEntretien.add(dbSqlTime);
                datesEntretiens.add(dbSqlTimestamp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DemandesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datesEntretiens;
    }

//    public Boolean getEtatEnt(int id){
//     Boolean b = null;
//        try {
//       
//        
//        Connection connection = ConnectionToDataBase.getInstance().getConnection();
//        String req="SELECT `etatEntretien` FROM `demandes` WHERE id=?";
//        
//        
//        
//        PreparedStatement pst = connection.prepareStatement(req);
//        pst.setInt(1, id);
//        ResultSet rs=pst.executeQuery();
//        while(rs.next())
//        {
//            switch (rs.getInt("etatEntretien")) {
//                case 0:
//                    
//                    b=false;
//                    break;
//                case 1:
//                    b=true;
//                    break;
//                default:
//                    b=null;
//                    break;
//            }
//        }
//        
//       
//    } catch (SQLException ex) {
//        Logger.getLogger(DemandesServices.class.getName()).log(Level.SEVERE, null, ex);
//    }
//        System.out.println(b);
//     return b;
//    }
    private int idOff;
    private int idUs;

    public User selectPdfEntreprise(int id) throws SQLException {
        User us = new User();

        Connection connection = ConnectionToDataBase.getInstance().getConnection();

        String req1 = "SELECT iduser FROM `offre` WHERE `id`=?";

        PreparedStatement pst1 = connection.prepareStatement(req1);
        pst1.setInt(1, id);
        ResultSet rs1 = pst1.executeQuery();
        while (rs1.next()) {
            idUs = rs1.getInt("iduser");
        }
        //System.out.println("iduser egale a"+idUs);

        String reqUser = "SELECT * FROM `user` WHERE `id`=?";
        PreparedStatement pst2 = connection.prepareStatement(reqUser);
        pst2.setInt(1, idUs);
        ResultSet rs2 = pst2.executeQuery();
        while (rs2.next()) {

            us.setCodePostal(rs2.getString("code_postal"));

            us.setId(rs2.getInt("id"));
            us.setNumTel(rs2.getInt("numero_tel"));
            us.setImage(rs2.getString("image"));

            us.setRue(rs2.getString("rue"));
            us.setUsername(rs2.getString("username"));
            us.setVille(rs2.getString("ville"));

            us.setEmail(rs2.getString("email"));
        }
        //System.out.println(us.toString());
        return us;
    }

}
