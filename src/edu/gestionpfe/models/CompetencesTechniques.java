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
public class CompetencesTechniques {
    private int id;
   private String level;
   private String competence;
   private int idCv;

   
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public int getIdCv() {
        return idCv;
    }

    public void setIdCv(int idCv) {
        this.idCv = idCv;
    }

    @Override
    public String toString() {
        return "CompetencesTechniques{" + "id=" + id + ", level=" + level + ", competence=" + competence + ", idCv=" + idCv + '}';
    }
   
}
