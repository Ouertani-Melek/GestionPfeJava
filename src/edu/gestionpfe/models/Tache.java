/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import edu.gestionpfe.services.UserServices;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 *
 * @author user
 */
public class Tache {
    
    private int id;
    private int idstage;
    private String tache;
    private Boolean etat;
   
    
    
    
    private Alert att;
    
     private ButtonType Valider;
    private ButtonType Devalider;

    public Tache(int id, int idstage, String tache, Boolean etat) {
        this.id = id;
        this.idstage = idstage;
        this.tache = tache;
        this.etat = etat;
        
        
        
    }

   
    
    

    public Tache(int x, String text, boolean b) {
        this.setIdstage(x);
        this.setEtat(b);
        this.setTache(text);
    }

    public Tache(String text, boolean b) {
        this.tache=text;
        this.etat=b;
    }

    
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getIdS() {
        return idstage;
    }

    public String getIdstage() {
       try {
            UserServices us = new UserServices();
            return us.getStageByIdUser(this.idstage).getTitreStage();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setIdstage(int idstage) {
        this.idstage = idstage;
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public String getEtat() {
        if (etat==true)
        {return "Validé";}
        else{return " non Validé";}
        
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
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
        final Tache other = (Tache) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idstage != other.idstage) {
            return false;
        }
        if (!Objects.equals(this.tache, other.tache)) {
            return false;
        }
        if (!Objects.equals(this.etat, other.etat)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tache{" + "id=" + id + ", idstage=" + idstage + ", tache=" + tache + ", etat=" + etat + '}';
    }
    
    
    
    
    
}
