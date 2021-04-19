import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainPageAdminController extends Register {

    @FXML
    private Button displayAllCustomersButton;

    @FXML
    private Button addAmountsButton;

    private String addAmountsPage = "src/main/resources/AddAmountsPage.fxml";
    private String displayCustomerPage = "src/main/resources/DisplayCustomersPage.fxml";

    @FXML
    void changeToAddAmounts(ActionEvent event) throws IOException {
        goToPage(event, addAmountsPage);
    }

    @FXML
    void changeToDisplayAllCustomers(ActionEvent event) throws IOException {
        goToPage(event, displayCustomerPage);
    }

}