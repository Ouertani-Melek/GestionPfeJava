/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

/**
 *
 * @author Melek OUERTANI
 */
public class Affectation {
      private int id;
      private int idenseignant;
      private int idsoutenance;

    public Affectation(int id, int idenseignant, int idsoutenance) {
        this.id = id;
        this.idenseignant = idenseignant;
        this.idsoutenance = idsoutenance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdenseignant() {
        return idenseignant;
    }

    public void setIdenseignant(int idenseignant) {
        this.idenseignant = idenseignant;
    }

    public int getIdsoutenance() {
        return idsoutenance;
    }

    public void setIdsoutenance(int idsoutenance) {
        this.idsoutenance = idsoutenance;
    }
       



}
