/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Stage;
import edu.gestionpfe.models.User;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static javafx.embed.swing.SwingFXUtils.toFXImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ahmed
 */
public class UserServices {

    public static User selectUser(String username) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user where username = ? ");
        pstmt.setString(1, username);
        resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return new User();
        } else {
            User usr = new User();
            while (resultSet.next()) {
                String pass = resultSet.getString("password");
                usr.setPassword(pass);
                usr.setAge(resultSet.getString("age"));
                usr.setNom(resultSet.getString("nom"));
                usr.setCodePostal(resultSet.getString("code_postal"));
                usr.setEnabled(resultSet.getBoolean("enabled"));
                usr.setId(resultSet.getInt("id"));
                usr.setNumTel(resultSet.getInt("numero_tel"));
                usr.setImage(resultSet.getString("image"));
                usr.setPrenom(resultSet.getString("prenom"));
                usr.setRue(resultSet.getString("rue"));
                usr.setUsername(resultSet.getString("username"));
                usr.setVille(resultSet.getString("ville"));
                usr.setConfirmer(resultSet.getBoolean("verifier"));
                usr.setEmail(resultSet.getString("email"));
            }
            return usr;
        }
    }

    public static List<String> getAllUserNames() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT username FROM user");
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString("username"));
        }
        return list;
    }

    public static List<String> getAllEmails() throws SQLException {
        List<String> list = new ArrayList<>();
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT email FROM user");
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString("email"));
        }
        return list;
    }

    public static boolean register(String username, String fxPassword, String ville, String rue, String role, String codePostal, String numTel, String fxNom, String fxPrenom, String fxEmail, File image) throws JSONException, ProtocolException, IOException {
        try {
            URL url = new URL("http://localhost/gestionpfe/web/app_dev.php/register/");
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", fxPassword);
            json.put("ville", ville);
            json.put("rue", rue);
            json.put("role", role);
            json.put("codePostal", codePostal);
            json.put("numTel", numTel);
            json.put("email", fxEmail);
            json.put("nom", fxNom);
            json.put("prenom", fxPrenom);
            json.put("java", true);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            JSONObject myResponse = new JSONObject(result);
            conn.disconnect();
            if (myResponse.getBoolean("response")) {
                return sendImage(image, username);
            }
            return false;
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public static boolean sendImage(File image, String username) throws JSONException {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost httppost = new HttpPost("http://localhost/gestionpfe/web/app_dev.php/setImage");
            MultipartEntity mpEntity = new MultipartEntity();
            ContentBody cbFile = new FileBody(image.getAbsoluteFile(), ContentType.DEFAULT_BINARY);
            ContentBody cbUsername = new StringBody(username, ContentType.APPLICATION_JSON);
            mpEntity.addPart("image", cbFile);
            mpEntity.addPart("username", cbUsername);
            httppost.setEntity(mpEntity);
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            return out.toString().equalsIgnoreCase("uploaded");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public static Stage getStageByIdUser(int i) throws SQLException {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM stage where id = ? ");
        pstmt.setInt(1, i);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return new Stage();
        } else {
            Stage usr = new Stage();
            while (resultSet.next()) {
               
                
                usr.setId(resultSet.getInt("id"));
                usr.setTitreStage(resultSet.getString("TitreStage"));
               
            }
            return usr;
        }
    }
    
    public static User getNomByIdUser(int i) throws SQLException {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user where id = ? ");
        pstmt.setInt(1, i);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return new User();
        } else {
            User usr = new User();
            while (resultSet.next()) {
                String pass = resultSet.getString("password");
                usr.setPassword(pass);
                usr.setAge(resultSet.getString("age"));
                usr.setNom(resultSet.getString("nom"));
                usr.setCodePostal(resultSet.getString("code_postal"));
                usr.setEnabled(resultSet.getBoolean("enabled"));
                usr.setId(resultSet.getInt("id"));
                usr.setNumTel(resultSet.getInt("numero_tel"));
                usr.setImage(resultSet.getString("image"));
                usr.setPrenom(resultSet.getString("prenom"));
                usr.setRue(resultSet.getString("rue"));
                usr.setUsername(resultSet.getString("username"));
                usr.setVille(resultSet.getString("ville"));
                usr.setConfirmer(resultSet.getBoolean("verifier"));
            }
            return usr;
        }
    }
    
    
    public static User getMailByIdUser(int i) throws SQLException {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user where id = ? ");
        pstmt.setInt(1, i);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return new User();
        } else {
            User usr = new User();
            while (resultSet.next()) {
                String pass = resultSet.getString("password");
                usr.setPassword(pass);
                usr.setAge(resultSet.getString("age"));
                usr.setNom(resultSet.getString("nom"));
                usr.setEmail(resultSet.getString("email"));

                usr.setCodePostal(resultSet.getString("code_postal"));
                usr.setEnabled(resultSet.getBoolean("enabled"));
                usr.setId(resultSet.getInt("id"));
                usr.setNumTel(resultSet.getInt("numero_tel"));
                usr.setImage(resultSet.getString("image"));
                usr.setPrenom(resultSet.getString("prenom"));
                usr.setRue(resultSet.getString("rue"));
                usr.setUsername(resultSet.getString("username"));
                usr.setVille(resultSet.getString("ville"));
                usr.setConfirmer(resultSet.getBoolean("verifier"));
            }
            return usr;
        }
    }
    
    
    public static User getNomByIdNom(String string) throws SQLException {
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user where nom = ? ");
        pstmt.setString(1, string);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return new User();
        } else {
            User usr = new User();
            while (resultSet.next()) {
                String pass = resultSet.getString("password");
                usr.setPassword(pass);
                usr.setAge(resultSet.getString("age"));
                usr.setNom(resultSet.getString("nom"));
                usr.setCodePostal(resultSet.getString("code_postal"));
                usr.setEnabled(resultSet.getBoolean("enabled"));
                usr.setId(resultSet.getInt("id"));
                usr.setNumTel(resultSet.getInt("numero_tel"));
                usr.setImage(resultSet.getString("image"));
                usr.setPrenom(resultSet.getString("prenom"));
                usr.setRue(resultSet.getString("rue"));
                usr.setUsername(resultSet.getString("username"));
                usr.setVille(resultSet.getString("ville"));
                usr.setConfirmer(resultSet.getBoolean("verifier"));
            }
            return usr;
        }
    }
    
    
    
    

    public static ImageView getUserImage(User user) throws Exception {
        if(user.getImage().contains(" "))
        user.setImage(user.getImage().replaceAll(" ","%20"));
        String imageUrl = "http://localhost/gestionpfe/web/uploads/user/" + user.getImage();
        URL url = new URL(imageUrl);
        BufferedImage c = ImageIO.read(url);
        ImageIcon image = new ImageIcon(c);
        java.awt.Image img = image.getImage();
        Image imgTtoDisplay;
        imgTtoDisplay = toFXImage((BufferedImage) img, null);
        ImageView imageView = new ImageView(imgTtoDisplay);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        return imageView;
    }
    public static User selectUserById(int id) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user where id = ?");
        pstmt.setInt(1, id);
        resultSet = pstmt.executeQuery();
        if (resultSet == null) {
            return new User();
        } else {
            User usr = new User();
            while (resultSet.next()) {
                String pass = resultSet.getString("password");
                usr.setPassword(pass);
                usr.setAge(resultSet.getString("age"));
                usr.setNom(resultSet.getString("nom"));
                usr.setCodePostal(resultSet.getString("code_postal"));
                usr.setEnabled(resultSet.getBoolean("enabled"));
                usr.setId(resultSet.getInt("id"));
                usr.setNumTel(resultSet.getInt("numero_tel"));
                usr.setImage(resultSet.getString("image"));
                usr.setPrenom(resultSet.getString("prenom"));
                usr.setRue(resultSet.getString("rue"));
                usr.setUsername(resultSet.getString("username"));
                usr.setVille(resultSet.getString("ville"));
                usr.setConfirmer(resultSet.getBoolean("verifier"));
                usr.setRoles(resultSet.getString("roles"));
                usr.setEmail(resultSet.getString("email"));
            }
            return usr;
        }
    }
    public static int Totalencad(int id) throws SQLException{
            int i=0;
            ResultSet resultSet = null;
        Connection connection = ConnectionToDataBase.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM stage where idencadrant = ?");
        pstmt.setInt(1, id);
        resultSet = pstmt.executeQuery();
        while(resultSet.next()){
                  i=  resultSet.getInt("total");                    
                }
            
            
            return i;
    }
    
    
}
