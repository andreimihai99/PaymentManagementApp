import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        Alert alert = new Alert(Alert.AlertType.NONE);
        int repl = 0, searched = 0;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject searchedUser = (JSONObject) iterator.next();
                if (searchedUser.get("username").equals(searchCustomerField.getText())) {           //compare the input from the admin with the info in json file
                    if (!addElectricityField.getText().isEmpty()) {
                        searchedUser.replace("electricity", addElectricityField.getText());         //replace the respective fields
                        repl++;                                                                     //if no field is filled, an alert will be displayed
                    }
                    if (!addInternetField.getText().isEmpty()) {
                        searchedUser.replace("internet", addInternetField.getText());
                        repl++;
                    }
                    if (!addTapWaterField.getText().isEmpty()) {
                        searchedUser.replace("tap water", addTapWaterField.getText());
                        repl++;
                    }
                    if (!addGasField.getText().isEmpty()) {
                        searchedUser.replace("gas", addGasField.getText());
                        repl++;
                        break;
                    }
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("You added a new bill.");
                    alert.show();
                    searched = 1;
                }

            }

            if (searched == 0) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("User does not exist!");
                alert.show();
            }

            if (repl == 0) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("You must fill at least one field!");
                alert.show();
            }


                JSONObject aux = new JSONObject();
                aux.put("UserInfo", jsonArray);                             //when information is replaced in the json, the entire modified data gets rewritten into the json file
                try {
                    FileWriter file = new FileWriter("src/main/resources/userData.json");
                    file.write(aux.toJSONString());
                    file.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch(FileNotFoundException fileNotFoundException){
                fileNotFoundException.printStackTrace();
            } catch(IOException ioException){
                ioException.printStackTrace();
            } catch(ParseException parseException){
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
        int searched = 0;
        Alert alert = new Alert(Alert.AlertType.NONE);
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
                    //String text = searchedUser;

                    Text txt = new Text(searchedUser);
                    txt.wrappingWidthProperty().bind(scene.widthProperty());
                    root.setFitToHeight(true);
                    root.setContent(txt);
                    primaryStage.setTitle("Customers");
                    primaryStage.setScene(scene);
                    primaryStage.show();

                    searched = 1;
                }
            }
            if(searched == 0) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("User does not exist!");
                alert.show();
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
