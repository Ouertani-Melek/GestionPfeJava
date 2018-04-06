/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import java.util.Date;

/**
 *
 * @author yahia
 */
public class Offre {
    private int id;
    private int idUser;
    private String titre;
    private boolean etat;
    private Date dateCreation;
    private int nbr_demandes;
    private int duree;
    private String description;

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getNbr_demandes() {
        return nbr_demandes;
    }

    public void setNbr_demandes(int nbr_demandes) {
        this.nbr_demandes = nbr_demandes;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Offre{" + "id=" + id + ", idUser=" + idUser + ", titre=" + titre + ", etat=" + etat + ", dateCreation=" + dateCreation + ", nbr_demandes=" + nbr_demandes + ", duree=" + duree + ", description=" + description + '}';
    }
    
}
