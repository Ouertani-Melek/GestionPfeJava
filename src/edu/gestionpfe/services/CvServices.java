/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import static edu.gestionpfe.controllers.ConnectionController.usr;
import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Cv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yahia
 */
public class CvServices {
    private static boolean verif;
    private static ResultSet resultSet ;
    private static ResultSet idAjoute;
    public static int AddedId;
    public static int IDCV;
    //test si user a deja un cv
    public static int CVID() throws SQLException{
       
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
            String sql= "select id from cv where iduser ="+usr.getId() ;
            PreparedStatement pst=connection.prepareStatement(sql);
            resultSet=pst.executeQuery();
            while (resultSet.next()) {
                
                 IDCV=resultSet.getInt("id");
                 }
        
        
        return IDCV;
    
    }
    public static int CVIdentifiant(int id) throws SQLException{
       
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
            String sql= "select id from cv where iduser ="+id ;
            PreparedStatement pst=connection.prepareStatement(sql);
            resultSet=pst.executeQuery();
            while (resultSet.next()) {
                
                 IDCV=resultSet.getInt("id");
                 }
        
        
        return IDCV;
    
    }
    public static Cv findCv() throws SQLException{
        
         
      
            
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            String sql= "select * from cv where iduser ="+usr.getId() ;
            PreparedStatement pst=connection.prepareStatement(sql);
            resultSet=pst.executeQuery();
            if(!resultSet.isBeforeFirst()){
                Cv newCv=new Cv();
                return newCv;
            }
            else{
                 Cv cv=new Cv();
                 while (resultSet.next()) {
                
                 cv.setIdUser(resultSet.getInt("iduser"));
                 cv.setDateModif(resultSet.getDate("datemodif"));
                 cv.setId(resultSet.getInt("id"));
                 }
            return cv;
            }

       
     
    
       
    
}
       
    
    //ajouter cv
    public int ajouterCv(Cv newCV) throws SQLException{
        
         
      
            
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            
            
            
            String sql = "INSERT INTO cv"
		+ "(iduser, datemodif) VALUES"
		+ "(?,?)";
            PreparedStatement pst=connection.prepareStatement(sql);
            pst.setInt(1, newCV.getIdUser());
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            pst.setTimestamp(2,date );
            
           
            pst.execute();
            
           String getIDCv="select id from cv where iduser="+newCV.getIdUser();
            PreparedStatement pst2=connection.prepareStatement(getIDCv);
            
           idAjoute=pst2.executeQuery();
           while(idAjoute.next()){
           AddedId=idAjoute.getInt(1);
           break;
           }
        System.out.println(AddedId);
        
    return AddedId;
    
       
    
}
     public void supprimerCv(int id) throws SQLException{
        
         
      
            
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            
            
            
            String sql = "delete from cv where id=?";
            PreparedStatement pst=connection.prepareStatement(sql);
            pst.setInt(1,id);
           
            pst.executeUpdate();
            
          
          
             
    
    
       
    
}
   
}
