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

public class RegisterCustomerController extends Register{

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerSurnameField;

    @FXML
    private TextField customerEmailField;

    @FXML
    private PasswordField customerPasswordField;

    @FXML
    private Button customerRegisterButton;

    @FXML
    private Button customerBackButton;

    private String customerHome = "src/main/resources/MainPageCustomer.fxml";

    @FXML
    void clickCustomerBack(ActionEvent event) throws IOException {
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



    public void addingToJSON(){
        JSONObject userInfo = new JSONObject();
        JSONObject userInfoFinal = new JSONObject();
        userInfo.put("name", customerNameField.getText());
        userInfo.put("surname", customerSurnameField.getText());
        userInfo.put("email", customerEmailField.getText());
        userInfo.put("password", customerPasswordField.getText());
        userInfo.put("electricity", 0);
        userInfo.put("gas",0);
        userInfo.put("internet",0);
        userInfo.put("tap water",0);
        userInfo.put("role","customer");

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

    @FXML
    void clickCustomerRegister(ActionEvent event) throws IOException, ParseException {
        String filename = "src/main/resources/userData.json";
        if(checkCorrectForm(customerNameField.getText(), customerSurnameField.getText(), customerEmailField.getText(), customerPasswordField.getText()) == 0) {
            if (checkUniqueUser(customerNameField.getText(), customerSurnameField.getText(), customerEmailField.getText(), filename) == 0) {
                addingToJSON();
                goToHomePage(event, customerHome);
            } else infoBox("Customer already exists!", "Warning");
        }
        else infoBox("Incorrect credentials!", "Warning");
    }
}