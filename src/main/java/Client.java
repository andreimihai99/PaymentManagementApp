import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Client extends Application {
    public static String customer;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        String fxmlPath = "src/main/resources/StartPage.fxml";

        FileInputStream fxmlStream = new FileInputStream(fxmlPath);
        Pane root = (Pane)loader.load(fxmlStream);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
