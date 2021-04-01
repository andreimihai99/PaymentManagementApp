import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Register {
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    void goToHomePage(ActionEvent event, String filename) throws IOException {
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

    public int checkCorrectForm(String nameField, String surnameField, String emailField, String passwordField){
        int value = 0;
        if(nameField.length() < 1)
            value = 1;
        if(surnameField.length() < 1)
            value = 1;
        if(emailField.length() < 6 || checkCorrectEmail(emailField) == 0)
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

    public int checkUniqueUser(String nameField, String surnameField, String emailField, String filename) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) readJSON(filename);
        int correct = 0;
        for(int i = 0; i < jsonObject.size(); i++)
            if(jsonObject.get("name") == nameField && jsonObject.get("surname") == surnameField && jsonObject.get("email") == emailField)
                correct = 1;
        return correct;
    }
}
