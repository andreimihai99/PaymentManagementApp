import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class MainPageAdminController extends Register {

    @FXML
    private Button displayAllCustomersButton;

    @FXML
    private Button addAmountsButton;

    @FXML
    private Label displayCustomersField;

    @FXML
    private AnchorPane displayCustomersPane;

    private String addAmountsPage = "src/main/resources/AddAmountsPage.fxml";

    @FXML
    void changeToAddAmounts(ActionEvent event) throws IOException {
        goToPage(event, addAmountsPage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollPane root = new ScrollPane();
        Scene scene = new Scene(root, 300, 400);
        JSONParser jsonParser = new JSONParser();
        String[] currentUser = new String[100];
        int count = 0;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject user = (JSONObject) iterator.next();
                if(user.get("role").toString().equals("customer")) {
                    currentUser[count] = " Name: " + user.get("name").toString() + "\n" + " Surname: " + user.get("surname").toString() + "\n" + " Username: " + user.get("username").toString() + "\n"
                            + " Electricity: " + user.get("electricity").toString() + "\n" + " Gas: " + user.get("gas").toString() + "\n" + " Tap water: " + user.get("tap water").toString() + "\n"
                            + " Internet: " + user.get("internet").toString() + "\n\n";
                    count++;
                }
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String text = "";
        for(int i = 0; i < count; i++){
            text += currentUser[i];
        }

        Text txt = new Text(text);
        txt.wrappingWidthProperty().bind(scene.widthProperty());
        root.setFitToHeight(true);
        root.setContent(txt);
        primaryStage.setTitle("Customers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void displayAllCustomers(ActionEvent actionEvent) throws Exception {
        Stage pr = new Stage();
        start(pr);
    }
}