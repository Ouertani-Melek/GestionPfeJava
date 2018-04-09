/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import java.sql.Date;

/**
 *
 * @author yahia
 */
public class DemandesPDF {
    private String NomEntreprise;
    private String NomEtudiant;
    private String EmailEntreprise;
    private String EmailEtu;
    private String NumTelEtudiant;
    private String NumTelEntreprise;
    private int MatriculeDemande;
    private String AdresseEtudiant;
    private String AdresseEntreprise;
    private String OffreDemande;
    private Date DateDemande;
    private Date DateEntretien;
    private String EtatD;
    private String EtatE;
    private String ImageUSr;
    private String ImageEntre;
    private int idEtudiant;
    private int idEntreprise;

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public String getImageUSr() {
        return ImageUSr;
    }

    public void setImageUSr( String ImageUSr) {
        this.ImageUSr = ImageUSr;
    }

    public String getImageEntre() {
        return ImageEntre;
    }

    public void setImageEntre(String ImageEntre) {
        this.ImageEntre = ImageEntre;
    }

    @Override
    public String toString() {
        return "DemandesPDF{" + "NomEntreprise=" + NomEntreprise + ", NomEtudiant=" + NomEtudiant + ", EmailEntreprise=" + EmailEntreprise + ", EmailEtu=" + EmailEtu + ", NumTelEtudiant=" + NumTelEtudiant + ", NumTelEntreprise=" + NumTelEntreprise + ", MatriculeDemande=" + MatriculeDemande + ", AdresseEtudiant=" + AdresseEtudiant + ", AdresseEntreprise=" + AdresseEntreprise + ", OffreDemande=" + OffreDemande + ", DateDemande=" + DateDemande + ", DateEntretien=" + DateEntretien + ", EtatD=" + EtatD + ", EtatE=" + EtatE + ", ImageUSr=" + ImageUSr + ", ImageEntre=" + ImageEntre + ", idEtudiant=" + idEtudiant + ", idEntreprise=" + idEntreprise + '}';
    }
    

   

    public String getEtatD() {
        return EtatD;
    }

    public String getEmailEtu() {
        return EmailEtu;
    }

    public void setEmailEtu(String EmailEtu) {
        this.EmailEtu = EmailEtu;
    }

    public void setEtatD(String EtatD) {
        this.EtatD = EtatD;
    }

    public String getEtatE() {
        return EtatE;
    }

    public void setEtatE(String EtatE) {
        this.EtatE = EtatE;
    }
    
    
    public String getNomEntreprise() {
        return NomEntreprise;
    }

    public void setNomEntreprise(String NomEntreprise) {
        this.NomEntreprise = NomEntreprise;
    }

    public String getNomEtudiant() {
        return NomEtudiant;
    }

    public void setNomEtudiant(String NomEtudiant) {
        this.NomEtudiant = NomEtudiant;
    }

    public String getEmailEntreprise() {
        return EmailEntreprise;
    }

    public void setEmailEntreprise(String EmailEntreprise) {
        this.EmailEntreprise = EmailEntreprise;
    }

    public String getNumTelEtudiant() {
        return NumTelEtudiant;
    }

    public void setNumTelEtudiant(String NumTelEtudiant) {
        this.NumTelEtudiant = NumTelEtudiant;
    }

    public String getNumTelEntreprise() {
        return NumTelEntreprise;
    }

    public void setNumTelEntreprise(String NumTelEntreprise) {
        this.NumTelEntreprise = NumTelEntreprise;
    }

    public int getMatriculeDemande() {
        return MatriculeDemande;
    }

    public void setMatriculeDemande(int MatriculeDemande) {
        this.MatriculeDemande = MatriculeDemande;
    }

    public String getAdresseEtudiant() {
        return AdresseEtudiant;
    }

    public void setAdresseEtudiant(String AdresseEtudiant) {
        this.AdresseEtudiant = AdresseEtudiant;
    }

    public String getAdresseEntreprise() {
        return AdresseEntreprise;
    }

    public void setAdresseEntreprise(String AdresseEntreprise) {
        this.AdresseEntreprise = AdresseEntreprise;
    }

    public String getOffreDemande() {
        return OffreDemande;
    }

    public void setOffreDemande(String OffreDemande) {
        this.OffreDemande = OffreDemande;
    }

    public Date getDateDemande() {
        return DateDemande;
    }

    public void setDateDemande(Date DateDemande) {
        this.DateDemande = DateDemande;
    }

    public Date getDateEntretien() {
        return DateEntretien;
    }

    public void setDateEntretien(Date DateEntretien) {
        this.DateEntretien = DateEntretien;
    }
    
}
