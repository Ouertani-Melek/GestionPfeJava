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
public class Langues {
    private int id;
    private String langue;
    private String niveau;
    private int idCv;

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getIdCv() {
        return idCv;
    }

    public void setIdCv(int idCv) {
        this.idCv = idCv;
    }

   
    @Override
    public String toString() {
        return "Langues{" + "id=" + id + ", langue=" + langue + ", niveau=" + niveau + ", idCv=" + idCv + '}';
    }
    
    
}
