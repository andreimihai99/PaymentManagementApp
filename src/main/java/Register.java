import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;

public class Register extends Client{

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    void clickBackRegister(ActionEvent event, String filename){

    }

    void goToPage(ActionEvent event, String filename) throws IOException {
        Stage newStage = new Stage();
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
        //Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        //Path to the FXML File
        String fxmlDocPath = filename;
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        //Create the Pane and all Details
        Pane root = (Pane) loader.load(fxmlStream);

        //Create the Scene
        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();
    }

    public int checkCorrectEmail(String email) {
        int i, checked = 0;
        for (i = 0; i < email.length(); i++)
            if (email.contains("@"))
                checked = 1;
        return checked;
    }

    public int checkCorrectForm(String nameField, String surnameField, String usernameField, String emailField, String passwordField){
        int value = 0;
        if(nameField.length() < 1)
            value = 1;
        if(surnameField.length() < 1)
            value = 1;
        if(usernameField.length() < 4)
            value = 1;
        if(emailField.length() < 6 || checkCorrectEmail(emailField) == 1)
            value = 1;
        if(passwordField.length() < 6)
            value = 1;
        return value;
    }

    public static Object readJSON(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public int checkUniqueUser(String usernameField) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        int exists = 0;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();

            while(iterator.hasNext()){
                JSONObject user = (JSONObject) iterator.next();
                if(user.get("name").toString().equals(usernameField))
                    exists = 1;
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
