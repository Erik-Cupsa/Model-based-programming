package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class StartCompleteWorkOnMaintenanceTicketPageController {

    @FXML
    private ComboBox<String> comboTicket; // Assuming the ticket identifiers are strings

    @FXML
    private Button btnStartWork;

    @FXML
    private Button btnCompleteWork;

    @FXML
    private Label labelWorkStatus;

    @FXML
    private TextArea textAreaFeedback;

    // Sample list of tickets for demonstration. Replace with actual data retrieval method.
    //private final ObservableList<String> ticketList = FXCollections.observableArrayList("Ticket 1", "Ticket 2", "Ticket 3");
    private ObservableList<String> ticketList = FXCollections.observableArrayList("Ticket 1", "Ticket 2", "Ticket 3");


    @FXML
    public void initialize() {
        populateTickets();
        comboTicket.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTicketSelection(newSelection);
            }
        });
    }

    private void populateTickets() {
        // Populate the ComboBox with ticket data
        comboTicket.setItems(ticketList);
    }

    private void updateTicketSelection(String selectedTicket) {
        // Update UI based on the selected ticket
        labelWorkStatus.setText("Selected: " + selectedTicket);
        textAreaFeedback.setText("Current status of ticket " + selectedTicket + " is displayed here.");
        // Additional logic to display the current status of the selected ticket can be added here
    }

    @FXML
    private void handleStartWork() {
        String selectedTicket = comboTicket.getValue();
        if (selectedTicket != null) {
            // Logic to start work on the selected ticket
            labelWorkStatus.setText("Work Started");
            textAreaFeedback.setText("Work on ticket " + selectedTicket + " has started.");
            // More logic can be added to reflect this change in the model
        }
    }

    @FXML
    private void handleCompleteWork() {
        String selectedTicket = comboTicket.getValue();
        if (selectedTicket != null) {
            // Logic to complete work on the selected ticket
            labelWorkStatus.setText("Work Completed");
            textAreaFeedback.setText("Work on ticket " + selectedTicket + " has been completed.");
            // More logic can be added to reflect this change in the model
        }
    }

    // Additional methods can be added as needed
}
