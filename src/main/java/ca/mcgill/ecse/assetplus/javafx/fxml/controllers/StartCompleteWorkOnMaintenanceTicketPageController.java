package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;
import ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class StartCompleteWorkOnMaintenanceTicketPageController {

    @FXML
    private ComboBox<String> comboTicket; // Assuming the ticket identifiers are strings

    @FXML
    private Button btnStartWork;

    @FXML
    private Button btnCompleteWork;

//    @FXML
//    private Label labelWorkStatus;

    @FXML
    private TextArea textAreaFeedback;

    private List<TOMaintenanceTicket> ticketList;

    /**
     * initializes UI components
     *
     * @author Philippe Aprahamian
     */
    @FXML
    public void initialize() {
        populateTickets();
        comboTicket.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTicketSelection(newSelection);
            }
        });
    }

    /**
     * Populates the ticket combo box
     *
     * @author Philippe Aprahamian
     */
    private void populateTickets() {
        ticketList = ViewUtils.getTickets();
        ArrayList<String> ticketIds= new ArrayList<>();
        for (TOMaintenanceTicket ticket:ticketList ) {
            ticketIds.add(String.valueOf(ticket.getId()));
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(ticketIds);
        comboTicket.setItems(observableList);
    }

    /**
     * updates the ticket selection
     *
     * @param selectedTicket the String containing the id of the selected ticket
     * @author Philippe Aprahamian
     */
    private void updateTicketSelection(String selectedTicket) {
        populateTickets();
        textAreaFeedback.setText("Selected: " + selectedTicket);
        for (TOMaintenanceTicket ticket:ticketList) {
            if(ticket.getId()==Integer.parseInt(selectedTicket)){
                //labelWorkStatus.setText(ticket.getStatus());
                break;
            }
        }
    }

    /**
     * Starts the work on the selected ticket
     *
     * @author Philippe Aprahamian
     */
    @FXML
    private void handleStartWork() {
        String selectedTicket = comboTicket.getValue();
        if (selectedTicket != null) {
            // Logic to start work on the selected ticket
            String msg = MaintenanceTicketWorkController.startWorkOnTicket(Integer.parseInt(selectedTicket));
            if (msg.equals("")){
                ViewUtils.makePopupWindow("Start Work on ticket #"+ selectedTicket,"Work started successfully");
                //labelWorkStatus.setText("InProgress");
                textAreaFeedback.setText("Work on ticket " + selectedTicket + " has started.");
            }else{
                ViewUtils.showError(msg);
            }
        }else {
            ViewUtils.showError("Please choose a ticket first");
        }

    }
    /**
     * Completes the work on the selected ticket
     *
     * @author Philippe Aprahamian
     */
    @FXML
    private void handleCompleteWork() {
        String selectedTicket = comboTicket.getValue();
        if (selectedTicket != null) {
            String msg = MaintenanceTicketWorkController.completeWorkOnTicket(Integer.parseInt(selectedTicket));
            if (msg.equals("")){
                ViewUtils.makePopupWindow("Complete Work on ticket #"+ selectedTicket,"Work completed successfully");
                //labelWorkStatus.setText("Resolved");
                textAreaFeedback.setText("Work on ticket " + selectedTicket + " has been completed.");
            }else{
                ViewUtils.showError(msg);
            }
        }else {
            ViewUtils.showError("Please choose a ticket first");
        }

    }
}
