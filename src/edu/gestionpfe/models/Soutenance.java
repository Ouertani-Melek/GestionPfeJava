/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import java.sql.Date;
import java.sql.Time;


/**
 *
 * @author Melek OUERTANI
 */
public class Soutenance {
    
    private int id;
    private int iduser;
    private String type;
    private Date datesoutenancecom;
    private Date datesoutenancetec ;
    private String salle;
    private Time heuredeb;
    private Time heurefin;
    public Soutenance() {
    }

    public Soutenance(int iduser, String type, Date datesoutenancecom, Date datesoutenancetec, String salle, Time heuredeb, Time heurefin) {
        this.iduser = iduser;
        this.type = type;
        this.datesoutenancecom = datesoutenancecom;
        this.datesoutenancetec = datesoutenancetec;
        this.salle = salle;
        this.heuredeb = heuredeb;
        this.heurefin = heurefin;
    }

    

    

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDatesoutenancecom() {
        return datesoutenancecom;
    }

    public void setDatesoutenancecom(Date datesoutenancecom) {
        this.datesoutenancecom = datesoutenancecom;
    }

    public Date getDatesoutenancetec() {
        return datesoutenancetec;
    }

    public void setDatesoutenancetec(Date datesoutenancetec) {
        this.datesoutenancetec = datesoutenancetec;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public Time getHeuredeb() {
        return heuredeb;
    }

    public void setHeuredeb(Time heuredeb) {
        this.heuredeb = heuredeb;
    }

    public Time getHeurefin() {
        return heurefin;
    }

    public void setHeurefin(Time heurefin) {
        this.heurefin = heurefin;
    }
 
    
}
