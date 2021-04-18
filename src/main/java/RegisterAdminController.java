import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;

public class RegisterAdminController extends Register{

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField keyField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    private String loginPage = "src/main/resources/LoginPage.fxml";
    private final String secretKey = "ssshhhhhhhhhhh!!!!";

    @FXML
    void clickBackAdmin(ActionEvent event) throws IOException {
        Stage newStage = new Stage();
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
        //Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        //Path to the FXML File
        String fxmlDocPath = "src/main/resources/StartPage.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        //Create the Pane and all Details
        Pane root = (Pane) loader.load(fxmlStream);

        //Create the Scene
        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();
    }

    @FXML
    public void clickRegisterAdmin(ActionEvent event) throws IOException, ParseException {
        String filename = "src/main/resources/userData.json";
        if(checkCorrectForm(nameField.getText(), surnameField.getText(), emailField.getText(), passwordField.getText()) == 0) {
            if (checkUniqueUser(nameField.getText(), surnameField.getText(), emailField.getText(), filename) == 0) {
                addingToJSON();
                goToHomePage(event, loginPage);
            } else infoBox("Customer already exists!", "Warning");
        }
        else infoBox("Incorrect credentials!", "Warning");
    }

    public void addingToJSON(){
        JSONObject userInfo = new JSONObject();
        JSONObject userInfoFinal = new JSONObject();
        userInfo.put("name", nameField.getText());
        userInfo.put("surname", surnameField.getText());
        userInfo.put("email",emailField.getText());
        userInfo.put("password", encrypt(passwordField.getText(), secretKey));
        userInfo.put("key", keyField.getText());
        userInfo.put("role","admin");

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray userArray = (JSONArray) jsonObject.get("UserInfo");

            userArray.add(userInfo);
            try {
                FileWriter file = new FileWriter("src/main/resources/userData.json");
                userInfoFinal.put("UserInfo", userArray);
                file.write(userInfoFinal.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}