/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idOffreChibani;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idU;
import edu.gestionpfe.models.CompetencesTechniques;
import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Demandes;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahia
 */
public class DemandesServices {
//public static List<Time> TimeEntretien=new ArrayList<>();   
    public List<Offre> DeadOffres=new ArrayList<>();
    public List<String> Dead=new ArrayList<>();
    private LocalDateTime finalDate;
    private long randomEpochDay;
    private long randomEpochTime;
    private LocalDate startDate;
    private LocalTime startTime;
    private long startTim;
    private long start;
    private LocalDate endDate;
    private LocalTime endTime;
    private long endTim;
    private long end;

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
                        Date today = new Date();
                         long diff = today.getTime() - of.getDateCreation().getTime();
                        if((diff / (1000 * 60 * 60 * 24))>60)
                            DeadOffres.add(of);
                    
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
        //System.out.println("id demanaaaaaaaaaaaaaaaaaaaaaaaaade :"+id);

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

    private int idOff;
    private int idUs;
    private float cnt;

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

    public float ChibaniIA() throws SQLException {
        CompetencesTechniquesServices cts = new CompetencesTechniquesServices();
        int idcv = CvServices.CVIdentifiant(idU);
        List<CompetencesTechniques> techEtudiant = new ArrayList<>();
        List<String> techEtu = new ArrayList<>();
        List<String> techOffres = new ArrayList<>();
        techEtudiant = cts.AfficherTechs(idcv);

        String req = "select tech from technolgies where idoffre=?";
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, idOffreChibani);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            techOffres.add(rs.getString("tech"));

        }
        for (CompetencesTechniques ct : techEtudiant) {
            techEtu.add(ct.getCompetence());
        }

        techEtu.stream().forEach(e -> {
            for (String te : techOffres) {
                if (te.equalsIgnoreCase(e)) {
                    cnt++;
                }

            }
//                cnt=Collections.frequency(techOffres, e);
        });

        float percent = (cnt / (float) techOffres.size()) * 100;

        return percent;
    }

    public LocalDateTime ChibaniIDDateEntretien() throws SQLException {
        startDate = LocalDate.now(); //start date
        startTime = LocalTime.of(8, 30);
        startTim = startTime.toSecondOfDay();
        List<LocalDateTime> datesEntretiens = new ArrayList<>();
        start = startDate.toEpochDay();
        //System.out.println(start+":"+startTim);

        endDate = LocalDate.of(2018, Month.MAY, 31); //end date
        endTime = LocalTime.of(15, 00);
        endTim = endTime.toSecondOfDay();
        end = endDate.toEpochDay();
        //System.out.println(start+":"+startTim);

        randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        randomEpochTime = ThreadLocalRandom.current().longs(startTim, endTim).findAny().getAsLong();
        //System.out.println("La Date est :"+LocalDate.ofEpochDay(randomEpochDay)+":"+LocalTime.ofSecondOfDay(randomEpochTime)); // random date between the range
        finalDate = LocalDateTime.of(LocalDate.ofEpochDay(randomEpochDay), LocalTime.ofSecondOfDay(randomEpochTime));
        //System.out.println(finalDate);

        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        String sql = "SELECT `dateEntretien` FROM `demandes` WHERE `dateEntretien` is NOT NULL AND `idoffre`=?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, idOffreChibani);
        ResultSet rs = pst.executeQuery();
//           while(rs.next()){
//               datesEntretiens.add(rs.getDate("dateEntretien"));
//               System.out.println(rs.getDate("dateEntretien"));
//           }
        while (rs.next()) {
            LocalTime dbSqlTime = rs.getTime("dateEntretien").toLocalTime();
            LocalDate dbSqlDate = rs.getDate("dateEntretien").toLocalDate();
//                java.sql. dbSqlTimestamp = rs.getTimestamp("dateEntretien");
            LocalDateTime dateBD = LocalDateTime.of(dbSqlDate.getYear(), dbSqlDate.getMonth(), dbSqlDate.getDayOfMonth(), dbSqlTime.getHour(),
                    dbSqlTime.getMinute(), dbSqlTime.getMinute());
            //System.out.println(dateBD);
            // System.out.println(dbSqlTime);
            //System.out.println(dbSqlDate);
            //TimeEntretien.add(dbSqlTime);
            datesEntretiens.add(dateBD);

        }
        datesEntretiens.stream().forEach(e -> {
            if (e.equals(finalDate)) {

                randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
                randomEpochTime = ThreadLocalRandom.current().longs(startTim, endTim).findAny().getAsLong();
                //System.out.println("La Date est :"+LocalDate.ofEpochDay(randomEpochDay)+":"+LocalTime.ofSecondOfDay(randomEpochTime)); // random date between the range
                finalDate = LocalDateTime.of(LocalDate.ofEpochDay(randomEpochDay), LocalTime.ofSecondOfDay(randomEpochTime));

            }

        });
        System.out.println(finalDate);
        return finalDate;

    }
    public List<String> findDeadOffre(){
        AfficherDemande();
        System.out.println("hani lahnéééé");
        DeadOffres.stream().forEach(e->{
                 Dead.add(FindTitreOffre(e.getId()));
                 System.out.println(FindTitreOffre(e.getId()));
            });
        
        return Dead;
    
    }
    
}
