/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Tache;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author user
 */
public class TachesServices {
    public static int idSt;
    private int idStageEtu ;
    
    
    
    
    public void inserttache(Tache t) {
        String req="INSERT INTO tache (idestage,tache,Etat) VALUES (?,?,?)";
        try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             PreparedStatement st= connection.prepareStatement(req);
             st.setInt(1, t.getIdS());
             
             
            st.setString(2,t.getTache());
            st.setBoolean(3,Boolean.parseBoolean(t.getEtat()));
            
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TachesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void validtache(int id) {
        String req="UPDATE tache set Etat = true where id=?";
        try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             PreparedStatement st= connection.prepareStatement(req);
             st.setInt(1,id);
             
            
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TachesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public void invalidtache(int id) {
        String req="UPDATE tache set Etat = false where id=?";
        try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             PreparedStatement st= connection.prepareStatement(req);
             st.setInt(1,id);
             
            
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TachesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    


    public List<Tache> selectAllTaches(int id) {
            List<Tache> taches=new ArrayList<Tache>();
        try {
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            String req2="select * from Stage where id="+id;
            
            
            Statement statement1=connection.createStatement();
            ResultSet resultSet2 =statement1.executeQuery(req2);
            while(resultSet2.next()){
                idStageEtu=resultSet2.getInt("id");
                idSt=idStageEtu;
                
            }
            
            
            
            try {
                String req="select * from tache where idestage="+idStageEtu;
                Statement statement=connection.createStatement();
                ResultSet resultSet =statement.executeQuery(req);
                while(resultSet.next()){
                    Tache p;
                    p = new Tache(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getBoolean(4));
                    taches.add(p);
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(StagesServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TachesServices.class.getName()).log(Level.SEVERE, null, ex);
        }
                    return taches;

        
    }
    
    public static float selectTachesValid(int id) {
            List<Tache> taches=new ArrayList<Tache>();
       
            
            float total = 0;
            float valid = 0;
            
            try {
                            Connection connection = ConnectionToDataBase.getInstance().getConnection();
                String req="SELECT COUNT(*) AS total FROM tache where idestage="+id;
              
                Statement statement=connection.createStatement();
                ResultSet resultSet =statement.executeQuery(req);
                while(resultSet.next()){
                  total=  resultSet.getInt("total");                    
                }
               //    System.out.println(total);
                if(total == 0)
                    return 0;
                String req3="SELECT COUNT(*) AS valid FROM tache where etat = true AND idestage="+id;
                ResultSet resultSet4 =statement.executeQuery(req3);
                
                while(resultSet4.next()){
                    valid = resultSet4.getInt("valid");             
                }
                //System.out.println(valid);
             
                float pourcentage = (valid/total)*100;
                System.out.println(pourcentage);
                return pourcentage ;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return 0;
            }
                    

        
    }
    
    
    
    
}
