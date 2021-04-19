import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class LoginPageController extends Register {

    @FXML
    private PasswordField passwordLoginField;

    @FXML
    private TextField usernameLoginField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backLoginButton;
    private final String secretKey = "ssshhhhhhhhhhh!!!!";
    private String mainPageCustomer = "src/main/resources/MainPageCustomer.fxml";
    private String mainPageAdmin = "src/main/resources/MainPageAdmin.fxml";


    @FXML
    void backLoginButtonAction(ActionEvent event) throws IOException {
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
    void loginButtonAction(ActionEvent event) throws IOException, ParseException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        checkUserAndPassword();
        if(checkUserAndPassword() == 1 && checkCorrectForm() == 1) {
            customer = usernameLoginField.getText();
            goToHomePage(event, mainPageCustomer);
        }
        else if(checkUserAndPassword() == 2 && checkCorrectForm() == 1)
            goToHomePage(event, mainPageAdmin);
        else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect credentials");
            alert.show();
        }

    }

    public int checkUserAndPassword() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        int correct = 0;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();

            while(iterator.hasNext()){
                JSONObject user = (JSONObject) iterator.next();
                if (usernameLoginField.getText().equals(user.get("username")) && encrypt(passwordLoginField.getText(), secretKey).equals(user.get("password")) && user.get("role").equals("customer")) {
                    correct = 1;
                }
                else if (usernameLoginField.getText().equals(user.get("username")) && encrypt(passwordLoginField.getText(), secretKey).equals(user.get("password")) && user.get("role").equals("admin"))
                    correct = 2;
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return correct;
    }

    public int checkCorrectForm(){
        int correct = 1;
        if(usernameLoginField.getText().isEmpty() || passwordLoginField.getText().isEmpty())
            correct = 0;
        return correct;
    }


}
