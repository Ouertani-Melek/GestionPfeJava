/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

/**
 *
 * @author ahmed
 */
public class Technologie {
    int id;
    
    int idOffre;
    
    String Tech;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getTech() {
        return Tech;
    }

    @Override
    public String toString() {
        return "Technologie{" + "id=" + id + ", idOffre=" + idOffre + ", Tech=" + Tech + '}' +"\n";
    }

    public void setTech(String Tech) {
        this.Tech = Tech;
    }
    
}
