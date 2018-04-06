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
public class Cv {
    private int id;
    private Date dateModif;
    private int idUser;

  

  

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateModif() {
        return dateModif;
    }

    public void setDateModif(Date dateModif) {
        this.dateModif = dateModif;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

   

    @Override
    public String toString() {
        return "Cv{" + "id=" + id + ", dateModif=" + dateModif + ", idUser=" + idUser + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cv other = (Cv) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
