package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class EditMaintenanceTicketController {
    @FXML
    private TableView<TOMaintenanceTicket> editTicketsTableView;
    @FXML
    private TableColumn<TOMaintenanceTicket, Integer> numberColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> issuerColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> statusColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> dateRaisedColumn;
    @FXML
    private Button cancelEditButton;
    @FXML
    private Button doneEditButton;
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
    public void initialize() {
        ViewUtils.loadTicketsIntoTableView(editTicketsTableView, numberColumn, issuerColumn, statusColumn, dateRaisedColumn);
    }

    @FXML
    private void returnToTicketStatusPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MainPage.fxml"));
            Parent newRoot = fxmlLoader.load();

            //ViewStatusPageController viewStatusPageController = fxmlLoader.getController();
            //viewStatusPageController.initialize();

            MainPageController mainPageController = fxmlLoader.getController();
            mainPageController.setSelectedTabIndex(2);
            mainPageController.initialize();

            // Access the current stage
            Stage currentStage = (Stage) doneEditButton.getScene().getWindow();
            // Replace the content in the current scene with content loaded from the new FXML
            currentStage.getScene().setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doneEditClicked(ActionEvent actionEvent) {
        TOMaintenanceTicket selectedTicket = editTicketsTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            System.out.println("No ticket selected.");
            return;
        }

        int id = Integer.parseInt(ticketId.getText());
        String descriptionText = description.getText();
        String userEmail = email.getText();
        String assetNum = assetNumber.getText();
        LocalDate localDate = datePicker.getValue();

        // Create a new TOMaintenanceTicket object using the read values
        String result = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(
                id,
                Date.valueOf(localDate),
                descriptionText,
                userEmail,
                Integer.parseInt(assetNum)
        );
        if (ViewUtils.successful(result)) {
            returnToTicketStatusPage();
        }
        else {
            ViewUtils.showError(result);
        }
    }

    public void cancelEditClicked(ActionEvent event) {
        returnToTicketStatusPage();
    }
}
