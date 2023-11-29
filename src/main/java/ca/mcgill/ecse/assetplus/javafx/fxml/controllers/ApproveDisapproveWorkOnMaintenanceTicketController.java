package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import java.sql.Date;
import java.util.List;

public class ApproveDisapproveWorkOnMaintenanceTicketController {

    @FXML
    private ComboBox<String> comboTicket; // Assuming the ticket identifiers are strings

    @FXML
    private Button btnApproveWork;

    @FXML
    private Button btnDisapproveWork;

    @FXML
    private Label labelWorkStatus;

    @FXML
    private TextArea textAreaFeedback;

    @FXML
    private DatePicker disapprovalDatePicker;


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
        int selectedTicketId = Integer.parseInt(selectedTicket);
        String selectedTicketStatusName ;
        //textAreaFeedback.setText("Selected: " + selectedTicket);
        for (TOMaintenanceTicket ticket:ticketList) {
            if(ticket.getId()==selectedTicketId){
                selectedTicketStatusName=ticket.getStatus();
                labelWorkStatus.setText(selectedTicketStatusName);
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
    private void handleApproveWork() {
        String selectedTicket = comboTicket.getValue();
        if (selectedTicket != null) {
            String msg = MaintenanceTicketWorkController.approveWorkOnTicket(Integer.parseInt(selectedTicket));
            if (msg.equals("")){
                ViewUtils.makePopupWindow("Approve Work on ticket #"+ selectedTicket,"Work approved successfully, ticket is now closed");
                labelWorkStatus.setText("Closed");
                textAreaFeedback.setText("Work on ticket#" + selectedTicket + " has been approved.");
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
    private void handleDisapproveWork() {
        ticketList = ViewUtils.getTickets();
        String selectedTicket = comboTicket.getValue();
        if (selectedTicket != null && disapprovalDatePicker.getValue() != null) {
            LocalDate disapprovalLocalDate = disapprovalDatePicker.getValue();
            Date date = Date.valueOf(disapprovalLocalDate);
            String msg = MaintenanceTicketWorkController.disapproveWorkOnTicket(Integer.parseInt(selectedTicket), date,textAreaFeedback.getText());
            if (msg.equals("")){
                ViewUtils.makePopupWindow("Disapprove Work on ticket #"+ selectedTicket,"Work disapproved successfully");
                labelWorkStatus.setText("InProgress");
                String reason = textAreaFeedback.getText();
                if (reason.equals("")){
                    reason="no reason";
                }
                textAreaFeedback.setText("Work on ticket " + selectedTicket + " has been disapproved on " + date + "for "+reason);
            }else{
                ViewUtils.showError(msg);
            }
        }else {
            ViewUtils.showError("Please choose a ticket and a feedback date first.");
        }

    }


    // Additional methods can be added as needed
}

