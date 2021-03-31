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

import java.io.*;

public class RegisterAdminController{

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

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        Stage newStage = new Stage();
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
        //Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        //Path to the FXML File
        String fxmlDocPath = "src/main/resources/HomePage.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        //Create the Pane and all Details
        Pane root = (Pane) loader.load(fxmlStream);

        //Create the Scene
        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();
    }

    @FXML
    public void clickRegisterAdmin(ActionEvent event) throws IOException {
        if(checkCorrectForm() == 4){
            addingToJSON();
        }
        else
            System.out.println("Incorrect form");
    }

    public int checkCorrectForm(){
        int value = 0;
        if(nameField.getText() != null)
            value += 1;
        if(surnameField.getText() != null)
            value += 1;
        if(emailField.getText() != null)
            value += 1;
        if(passwordField.getText() != null)
            value += 1;
        return value;
    }

    public void addingToJSON(){
        int emptyDB = 0;
        JSONObject userInfo = new JSONObject();
        JSONObject jObjFinal = new JSONObject();
        userInfo.put("name", nameField.getText());
        userInfo.put("surname", surnameField.getText());
        userInfo.put("email",emailField.getText());
        userInfo.put("password", passwordField.getText());

        JSONParser jParser = new JSONParser();
        try{
            JSONObject jObject = (JSONObject)jParser.parse(new FileReader("src/main/resources/data.json"));
            JSONArray jArray = (JSONArray)jObject.get("UserInfo");

            if(jArray.isEmpty())
                emptyDB = 1;
            else
                jArray.add(userInfo);

            try {
                FileWriter file = new FileWriter("src/main/resources/data.json");
                jObjFinal.put("UserInfo", jArray);
                file.write(jObjFinal.toJSONString());
                file.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}