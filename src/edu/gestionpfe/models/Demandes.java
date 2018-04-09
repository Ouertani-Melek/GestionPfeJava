/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import edu.gestionpfe.services.DemandesServices;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author yahia
 */
public class Demandes {
    
    private int id;
    private boolean etatDemande;
    private LocalDateTime dateDemande;
    private LocalDateTime dateEntretien;
    private boolean etatEntretien;
    private String methodeCommunication;
    private boolean confirmation;
    private int idOffre;
    private int idUser;
    private String TitreOffre;
    

    
    
    

    
    public int getId() {
        return id;
    }
    public void settitreOffrePrDemande(int idOffre){
        this.TitreOffre=DemandesServices.FindTitreOffre(idOffre);
    }
    public String gettitreOffrePrDemande(){
        return this.TitreOffre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEtatDemande() {
        return etatDemande;
    }

    public void setEtatDemande(boolean etatDemande) {
        this.etatDemande = etatDemande;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public LocalDateTime getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(LocalDateTime dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public boolean isEtatEntretien() {
        return etatEntretien;
    }
    public Boolean getEtatEntretien() {
        return etatEntretien;
    }

    public void setEtatEntretien(boolean etatEntretien) {
        this.etatEntretien = etatEntretien;
    }

    public String getMethodeCommunication() {
        return methodeCommunication;
    }

    public void setMethodeCommunication(String methodeCommunication) {
        this.methodeCommunication = methodeCommunication;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Demandes{" + "id=" + id + ", etatDemande=" + etatDemande + ", dateDemande=" + dateDemande + ", dateEntretien=" + dateEntretien + ", etatEntretien=" + etatEntretien + ", methodeCommunication=" + methodeCommunication + ", confirmation=" + confirmation + ", idOffre=" + idOffre + ", idUser=" + idUser + '}';
    }
    //            c.setOnMouseEntered((MouseEvent t) -> {
//                Delet.setVisible(true);
//                b.setVisible(true);
//                l.setVisible(true);
//
//                c.setOpacity(0.5);
//            });
//            c.setOnMouseExited((MouseEvent t) -> {
//                b.setVisible(false);  //btnsa.setStyle("-fx-background-color:transparent;");
//                l.setVisible(false);
//                 Delet.setVisible(false);
//                c.setOpacity(1);
//            });
//            b.setOnMouseEntered((MouseEvent t) -> {
//                b.setVisible(true);
//               
//
//            });
//            Delet.setOnMouseEntered((MouseEvent t) -> {
//                Delet.setVisible(true);
//               
//
//            });
    
      
}
