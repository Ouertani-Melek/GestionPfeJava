/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import edu.gestionpfe.services.UserServices;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author user
 */
public class EtudiantDemandes {
    private int id;
    private int idoffre;
    private int iduser;
    private boolean etatDemande;
    private Date dateEntretien;
    private boolean etatEntretien;
    private boolean confirmation;

    public EtudiantDemandes(int id, int idoffre, int iduser, boolean etatDemande, Date dateEntretien, boolean etatEntretien, boolean confirmation) {
        this.id = id;
        this.idoffre = idoffre;
        this.iduser = iduser;
        this.etatDemande = etatDemande;
        this.dateEntretien = dateEntretien;
        this.etatEntretien = etatEntretien;
        this.confirmation = confirmation;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdoffre() {
      // us = new UserServices();
        return idoffre;
        
    }
    

    public void setIdoffre(int idoffre) {
        this.idoffre = idoffre;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public boolean isEtatDemande() {
        return etatDemande;
    }

    public void setEtatDemande(boolean etatDemande) {
        this.etatDemande = etatDemande;
    }

    public Date getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(Date dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public boolean isEtatEntretien() {
        return etatEntretien;
    }

    public void setEtatEntretien(boolean etatEntretien) {
        this.etatEntretien = etatEntretien;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final EtudiantDemandes other = (EtudiantDemandes) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idoffre != other.idoffre) {
            return false;
        }
        if (this.iduser != other.iduser) {
            return false;
        }
        if (this.etatDemande != other.etatDemande) {
            return false;
        }
        if (this.etatEntretien != other.etatEntretien) {
            return false;
        }
        if (this.confirmation != other.confirmation) {
            return false;
        }
        if (!Objects.equals(this.dateEntretien, other.dateEntretien)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EtudiantDemandes{" + "id=" + id + ", idoffre=" + idoffre + ", iduser=" + iduser + ", etatDemande=" + etatDemande + ", dateEntretien=" + dateEntretien + ", etatEntretien=" + etatEntretien + ", confirmation=" + confirmation + '}';
    }
    
    
    
}
