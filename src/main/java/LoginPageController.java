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
        if(checkUserAndPassword() == 1 && checkCorrectForm() == 1)
            goToHomePage(event, mainPageCustomer);
        else if(checkUserAndPassword() == 2 && checkCorrectForm() == 1)
            goToHomePage(event, mainPageAdmin);
        else
            infoBox("Incorrect credentials!", "Warning");
    }

    public int checkUserAndPassword() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
        JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
        Iterator iterator = jsonArray.iterator();
        int correct = 0;
        while(iterator.hasNext())
            if(usernameLoginField.getText().equals(jsonObject.get("name")) && decrypt(passwordLoginField.getText(), secretKey).equals(jsonObject.get("password")) && jsonObject.get("role").equals("customer"))
                correct = 1;
            else if(usernameLoginField.getText().equals(jsonObject.get("name")) && decrypt(passwordLoginField.getText(), secretKey).equals(jsonObject.get("password")) && jsonObject.get("role").equals("admin"))
                correct = 2;
            return correct;
    }

    public int checkCorrectForm(){
        int correct = 0;
        if(usernameLoginField.getText().isEmpty() || passwordLoginField.getText().isEmpty())
            correct = 1;
        return correct;
    }

}
