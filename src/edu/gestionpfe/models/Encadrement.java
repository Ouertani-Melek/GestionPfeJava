/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author user
 */
public class Encadrement {
    
    private int id;
    private int idstage;
    private int Etat;
    private double pourcentage;
    private LocalDate dateReunion;

    public Encadrement(int id, int idstage, int Etat, double pourcentage, LocalDate dateReunion) {
        this.id = id;
        this.idstage = idstage;
        this.Etat = Etat;
        this.pourcentage = pourcentage;
        this.dateReunion = dateReunion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdstage() {
        return idstage;
    }

    public void setIdstage(int idstage) {
        this.idstage = idstage;
    }

    public int getEtat() {
        return Etat;
    }

    public void setEtat(int Etat) {
        this.Etat = Etat;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public LocalDate getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(LocalDate dateReunion) {
        this.dateReunion = dateReunion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Encadrement other = (Encadrement) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idstage != other.idstage) {
            return false;
        }
        if (this.Etat != other.Etat) {
            return false;
        }
        if (Double.doubleToLongBits(this.pourcentage) != Double.doubleToLongBits(other.pourcentage)) {
            return false;
        }
        if (!Objects.equals(this.dateReunion, other.dateReunion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Encadrement{" + "id=" + id + ", idstage=" + idstage + ", Etat=" + Etat + ", pourcentage=" + pourcentage + ", dateReunion=" + dateReunion + '}';
    }
    
    
    
}
