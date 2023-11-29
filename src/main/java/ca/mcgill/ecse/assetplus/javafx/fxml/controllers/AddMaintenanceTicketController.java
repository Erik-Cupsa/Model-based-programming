package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class AddMaintenanceTicketController {
    @FXML
    private ListView<TOMaintenanceTicket> ticketsListView;
    @FXML
    private Button addTicketButton;
    @FXML
    private Button doneTicketButton;
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
        ViewUtils.loadTickets(ticketsListView);
    }

    @FXML
    public void doneTicketClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../pages/ViewStatusPage.fxml"));
            Parent newRoot = fxmlLoader.load();

            ViewStatusPageController viewStatusPageController = fxmlLoader.getController();

            viewStatusPageController.initialize();
            viewStatusPageController.setTicketsListView(this.ticketsListView);
            // Access the current stage
            Stage currentStage = (Stage) doneTicketButton.getScene().getWindow();
            // Replace the content in the current scene with content loaded from the new FXML
            currentStage.getScene().setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addTicketClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(ticketId.getText());
        String descriptionText = description.getText();
        String userEmail = email.getText();
        String assetNum = assetNumber.getText();
        LocalDate localDate = datePicker.getValue();
        Date date = Date.valueOf(localDate);

        // Create a new TOMaintenanceTicket object using the read values
        //fix later to be maintenace ticket instead of TO
        TOMaintenanceTicket newTicket = new TOMaintenanceTicket(
                id,
                date,
                descriptionText,
                userEmail,
                "Open",
                "",
                "2 days",
                "High",
                false,
                "Kitchen Sink",
                365,
                new Date(2000, 11, 11),
                1,
                101,
                Arrays.asList("url1", "url2")
        );
        // Add the new ticket to the ticket list
        ticketsListView.getItems().add(newTicket);
    }
}
