import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class MainPageCustomerController extends LoginPageController {
    @FXML
    private Label customerReceiptLabel;

    @FXML
    private Label customerElectricityLabel;

    @FXML
    private Label customerTapWaterLabel;

    @FXML
    private Label customerGasLabel;

    @FXML
    private Label customerInternetLabel;

    @FXML
    private Button showAmountsButton;

    @FXML
    private Button payButton;

    @FXML
    private Button payAllButton;

    @FXML
    private TextField getAmountField;


    public int checkReceipt(String billField, Label dest){
        JSONParser jsonParser = new JSONParser();
        int check = 0;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();

            while(iterator.hasNext()){
                JSONObject currentUser = (JSONObject) iterator.next();
                if(currentUser.get("username").equals(customer))
                    if(!currentUser.get(billField).toString().equals("0")) {
                        check = 1;
                        dest.setText(currentUser.get(billField).toString());
                    }
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }

    public void receiptNumberDisplay(){
        int sum = checkReceipt("electricity", customerElectricityLabel) + checkReceipt("gas", customerGasLabel) +
                checkReceipt("tap water", customerTapWaterLabel) + checkReceipt("internet", customerInternetLabel);
        if(sum != 0)
            customerReceiptLabel.setText("You have " + sum + " receipts to pay");
        else
            customerReceiptLabel.setText("You have nothing to pay");
    }

    @FXML
    void showAmounts(ActionEvent event) {
        receiptNumberDisplay();
    }

    @FXML
    void payAllButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/userData.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("UserInfo");
            Iterator iterator = jsonArray.iterator();

            while(iterator.hasNext()){
                JSONObject currentUser = (JSONObject) iterator.next();
                if(currentUser.get("username").equals(customer)) {
                    currentUser.replace("electricity", "0");
                    currentUser.replace("gas", "0");
                    currentUser.replace("tap water", "0");
                    currentUser.replace("internet", "0");
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure about this action?");
                    alert.show();
                }
            }
            JSONObject aux = new JSONObject();
            aux.put("UserInfo", jsonArray);
            try {
                FileWriter file = new FileWriter("src/main/resources/userData.json");
                file.write(aux.toJSONString());
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void payButtonAction(ActionEvent event) {                   //the customer introduces the amount he wants to pay
        String payAmount = getAmountField.getText();

    }

}
