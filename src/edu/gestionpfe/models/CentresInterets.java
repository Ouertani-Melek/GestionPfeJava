/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

/**
 *
 * @author yahia
 */
public class CentresInterets {
    private int id;
    private String centreInteret;
    private int idCv;

    

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCentreInteret() {
        return centreInteret;
    }

    public void setCentreInteret(String centreInteret) {
        this.centreInteret = centreInteret;
    }

    public int getIdCv() {
        return idCv;
    }

    public void setIdCv(int idCv) {
        this.idCv = idCv;
    }

    @Override
    public String toString() {
        return "CentresInterets{" + "id=" + id + ", centreInteret=" + centreInteret + ", idCv=" + idCv + '}';
    }
    
     
}
