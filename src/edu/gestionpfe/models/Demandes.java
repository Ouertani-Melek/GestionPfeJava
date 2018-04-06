/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    
    public int getId() {
        return id;
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
    
    
      
}
