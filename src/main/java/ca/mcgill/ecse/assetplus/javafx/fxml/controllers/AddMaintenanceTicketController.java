package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AddMaintenanceTicketController {
    @FXML
    private TableView<TOMaintenanceTicket> addTicketsTableView;
    @FXML
    private TableColumn<TOMaintenanceTicket, Integer> numberColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> issuerColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> statusColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> dateRaisedColumn;
    @FXML
    private Button cancelAddButton;
    @FXML
    private Button doneAddButton;
    @FXML
    private TextField ticketId;
    @FXML
    private TextField description;
    @FXML
    private TextField email;
    @FXML
    private TextField assetNumber;
    @FXML
    private DatePicker datePicker;

    @FXML
    private void returnToTicketStatusPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MainPage.fxml"));
            Parent newRoot = fxmlLoader.load();

            //ViewStatusPageController viewStatusPageController = fxmlLoader.getController();
            //viewStatusPageController.initialize();

            MainPageController mainPageController = fxmlLoader.getController();
            mainPageController.setSelectedTabIndex(1);
            mainPageController.initialize();

            // Access the current stage
            Stage currentStage = (Stage) doneAddButton.getScene().getWindow();
            // Replace the content in the current scene with content loaded from the new FXML
            currentStage.getScene().setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void doneAddClicked(TextField ticketId, TextField description, TextField email, TextField assetNumber, DatePicker datePicker) {
        // TODO: CHECK IF USER ACTUALLY FILLED IN THE FIELDS
        if (ticketId.getText().equals("")) {
            ViewUtils.showError("Please fill in the id field.");
        }
        Integer id = Integer.parseInt(ticketId.getText());
        String descriptionText = description.getText();
        String userEmail = email.getText();
        String assetNum = assetNumber.getText();
        LocalDate localDate = datePicker.getValue();
        if (descriptionText.equals("")) {
            ViewUtils.showError("Please fill in the description field.");
        } else if (userEmail.equals("")) {
            ViewUtils.showError("Please fill in the email field.");
        } else if (assetNum.equals("")) {
            ViewUtils.showError("Please fill in the asset number field.");
        } else if (localDate == null) {
            ViewUtils.showError("Please fill in the date field.");
        }

        // Create a new TOMaintenanceTicket object using the read values
        String result = AssetPlusFeatureSet4Controller.addMaintenanceTicket(
                id,
                Date.valueOf(localDate),
                descriptionText,
                userEmail,
                Integer.parseInt(assetNum)
        );
        if (!ViewUtils.successful(result)) {
            ViewUtils.showError(result);
        }
    }
}