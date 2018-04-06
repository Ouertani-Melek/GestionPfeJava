/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;


import java.time.LocalDate;

/**
 *
 * @author yahia
 */
public class Formation {
    private int id;
    private String formation;
    private LocalDate  annee;
    private int idCv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public LocalDate getAnnee() {
        return annee;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public int getIdCv() {
        return idCv;
    }

    public void setIdCv(int idCv) {
        this.idCv = idCv;
    }

    @Override
    public String toString() {
        return "Formation{" + "id=" + id + ", formation=" + formation + ", annee=" + annee + ", idCv=" + idCv + '}';
    }

   
    
    
}
