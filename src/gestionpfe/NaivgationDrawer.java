package gestionpfe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NaivgationDrawer extends Application {
    
    public static Boolean isSplashLoaded = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Default/UserIndex.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Acceuil ");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
