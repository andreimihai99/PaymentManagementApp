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

import java.io.FileInputStream;
import java.io.IOException;

public class RegisterCustomerController{

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

    @FXML
    void clickCustomerRegister(ActionEvent event) {

    }
}