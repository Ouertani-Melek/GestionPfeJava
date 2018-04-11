/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.ids;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author user
 */
public class EncadrementsServices {
    public static int idSt;
    private int idStageEtu ;
    
    
    
    
    public void insertdatereunion(LocalDate f) {
        String req="UPDATE encadrement set dateReunion=? where idstage=?";
        try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             PreparedStatement st= connection.prepareStatement(req);
             st.setDate(1,java.sql.Date.valueOf(f));
             st.setInt(2, ids); 
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EncadrementsServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updatedatereunion(int i) {
        String req="UPDATE encadrement SET dateReunion = DATE_ADD(dateReunion,INTERVAL ? DAY) WHERE idstage=?";
        try {
             Connection connection = ConnectionToDataBase.getInstance().getConnection();
             PreparedStatement st= connection.prepareStatement(req);
             st.setInt(1,i);
             st.setInt(2, ids); 
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EncadrementsServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

    
    
}
