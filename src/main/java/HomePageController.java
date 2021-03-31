import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private Button registerCustomerButton;

    @FXML
    private Button registerAdminButton;


    @FXML
    void changeToLoginPage(ActionEvent event) throws IOException {
        Stage newStage = new Stage();
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
        //Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        //Path to the FXML File
        String fxmlDocPath = "src/main/resources/LoginPage.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        //Create the Pane and all Details
        Pane root = (Pane) loader.load(fxmlStream);

        //Create the Scene
        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();
    }

    @FXML
    void changeToRegisterCustomerPage(ActionEvent event) throws IOException {
        Stage newStage = new Stage();
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
        //Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        //Path to the FXML File
        String fxmlDocPath = "src/main/resources/RegisterCustomer.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        //Create the Pane and all Details
        Pane root = (Pane) loader.load(fxmlStream);

        //Create the Scene
        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();
    }

    @FXML
    void changeToRegisterAdminPage(ActionEvent event) throws IOException {
        Stage newStage = new Stage();
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
        //Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        //Path to the FXML File
        String fxmlDocPath = "src/main/resources/RegisterAdmin.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        //Create the Pane and all Details
        Pane root = (Pane) loader.load(fxmlStream);

        //Create the Scene
        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();
    }


    public void initialize(URL location, ResourceBundle resources) {
    }
}