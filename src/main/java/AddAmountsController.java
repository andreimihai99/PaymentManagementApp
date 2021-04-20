import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class AddAmountsController extends Register {

    private String mainPageAdmin = "src/main/resources/MainPageAdmin.fxml";

    @FXML
    private TextField searchCustomerField;

    @FXML
    private Button showAccountValuesButton;

    @FXML
    private TextField addElectricityField;

    @FXML
    private TextField addInternetField;

    @FXML
    private TextField addTapWaterField;

    @FXML
    private TextField addGasField;

    @FXML
    private TextArea showAccountValuesField;

    @FXML
    private Button addAmountsButton;

    @FXML
    private Button backButton;


    @FXML
    void addAmountsToAccount(ActionEvent event) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject user = (JSONObject) iterator.next();
//                if(user.get("username").toString().equals(searchCustomerField.getText())) {
//                    try {
//                        FileWriter writer = new FileWriter("src/main/resources/userData.json");
//                        user.put("electricity", addElectricityField.getText());
//                        writer.write(user.toString());
//                        writer.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
    }

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        goToPage(event, mainPageAdmin);
    }

    @FXML
    void showAccountValues(ActionEvent event) throws Exception {
        Stage pr = new Stage();
        start(pr);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollPane root = new ScrollPane();
        Scene scene = new Scene(root, 300, 200);
        JSONParser jsonParser = new JSONParser();
        String searchedUser = "";
        int count = 0;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject user = (JSONObject) iterator.next();
                if(user.get("username").toString().equals(searchCustomerField.getText())) {
                    searchedUser = " Name: " + user.get("name").toString() + "\n" + " Surname: " + user.get("surname").toString() + "\n"
                            + " Electricity: " + user.get("electricity").toString() + "\n" + " Gas: " + user.get("gas").toString() + "\n"
                            + " Tap water: " + user.get("tap water").toString() + "\n" + " Internet: " + user.get("internet").toString() + "\n\n";
                }
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String text = searchedUser;

        Text txt = new Text(text);
        txt.wrappingWidthProperty().bind(scene.widthProperty());
        root.setFitToHeight(true);
        root.setContent(txt);
        primaryStage.setTitle("Customers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
