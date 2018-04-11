/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Soutenance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.util.ArrayList;


/**
 *
 * @author Melek OUERTANI
 */
public class SoutenanceServices {
    Connection  conn;

    public SoutenanceServices() {
         conn= ConnectionToDataBase.getInstance().getConnection();
    }
    
    
     public void ajouterDisponibilite(Soutenance S) {
         try {
            String query = "insert into soutenance(iduser,type,dateSoutenanceCommercial,dateSoutenanceTechnique,salle,heuredeb,heurefin)"
                    + "Values(?,?,?,?,?,?,?);";
            PreparedStatement stm;
            stm= conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setInt(1, S.getIduser());
            stm.setString(2,S.getType());
            stm.setDate(3,(Date) S.getDatesoutenancecom());
            stm.setDate(4,(Date) S.getDatesoutenancetec());
            stm.setString(5, S.getSalle());
            stm.setTime(6, S.getHeuredeb());
            stm.setTime(7, S.getHeurefin());
            stm.execute();
             ResultSet keyResultSet = stm.getGeneratedKeys();
            int id = 0;
            if (keyResultSet.next()) {
                id = (int) keyResultSet.getInt(1);
                S.setId(id);

                System.out.println(id);
            }
           
            System.out.println(" ok");
        } catch (SQLException ex) {
            Logger.getLogger(SoutenanceServices.class.getName()).log(Level.SEVERE, null, ex);
        }} 
        
    public void ajouterDispo_user(int p,int cv) {
         try {
            String query = "insert into affectation(idenseignant,idsoutenance)"
                    + "Values(?,?);";
            PreparedStatement stm;
            stm= conn.prepareStatement(query);
            stm.setInt(2,cv);
            stm.setInt(1,p);
           
            stm.execute();
            
            System.out.println("ok");
           
            }
           
         catch (SQLException ex) {
            Logger.getLogger(SoutenanceServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
     public ArrayList<Soutenance> selectDisponibilites(){
        
        
        ArrayList<Soutenance> o = new ArrayList<>();

        try {
            String query = "select * from soutenance ;";
            PreparedStatement stm = conn.prepareStatement(query);
            
            ResultSet rest = stm.executeQuery();
            while (rest.next()) {
                Soutenance sou = new Soutenance();
               
                
                sou.setType(rest.getString("type"));
                sou.setDatesoutenancecom(rest.getDate("dateSoutenanceCommercial"));
                sou.setDatesoutenancetec(rest.getDate("dateSoutenanceTechnique"));
               
                sou.setHeuredeb(rest.getTime("heuredeb"));
                sou.setHeurefin(rest.getTime("heurefin"));
              


                o.add(sou);
              
            }

        } catch (SQLException ex) {
            Logger.getLogger(SoutenanceServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
     
      public int Verif(Soutenance d){
        
        
        
        int i = 0;
        try {
            String query = "select count(*) as nbr from affectation where idsoutenance = ? ;";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1,d.getId());
            
            ResultSet rest = stm.executeQuery();
            while (rest.next()) {
                
                i = rest.getInt("nbr");
               
              
            }

        } catch (SQLException ex) {
            Logger.getLogger(SoutenanceServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
      
}
