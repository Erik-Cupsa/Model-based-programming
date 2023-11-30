package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

//    @FXML
//    private Label labelWorkStatus;

    @FXML
    private TextArea textAreaFeedback;

    @FXML
    private DatePicker disapprovalDatePicker;
    @FXML
    private Label selectedTicketLabel;

    private TOMaintenanceTicket selectedTicket;

    /**
     * initializes UI components
     *
     * @author Philippe Aprahamian
     */
    @FXML
    public void initialize() {
        System.out.println("initialize");
    }

    /**
     * updates the ticket selection
     *
     * @param selectedTicket the String containing the id of the selected ticket
     * @author Philippe Aprahamian
     */
    public void updateTicketSelection(TOMaintenanceTicket selectedTicket) {
        this.selectedTicket = selectedTicket;
        selectedTicketLabel.setText(String.valueOf(selectedTicket.getId()));
    }

    /**
     * Starts the work on the selected ticket
     *
     * @author Philippe Aprahamian
     */
    @FXML
    private void handleApproveWork() {
        System.out.println(selectedTicket.getId());
        Integer selectedTicketId = selectedTicket.getId();
        if (selectedTicket != null) {
            String msg = MaintenanceTicketWorkController.approveWorkOnTicket(selectedTicketId);
            if (msg.equals("")){
                ViewUtils.makePopupWindow("Approve Work on ticket #"+ selectedTicketId,"Work approved successfully, ticket is now closed");
                ViewUtils.returnToTicketStatusPage(getClass(), (Stage) btnApproveWork.getScene().getWindow());
                //labelWorkStatus.setText("Closed");
                //textAreaFeedback.setText("Work on ticket#" + selectedTicket + " has been approved.");
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
        Integer selectedTicketId = selectedTicket.getId();
        if (selectedTicket != null && disapprovalDatePicker.getValue() != null) {
            LocalDate disapprovalLocalDate = disapprovalDatePicker.getValue();
            Date date = Date.valueOf(disapprovalLocalDate);
            String msg = MaintenanceTicketWorkController.disapproveWorkOnTicket(selectedTicketId, date,textAreaFeedback.getText());
            if (msg.equals("")){
                ViewUtils.makePopupWindow("Disapprove Work on ticket #"+ selectedTicketId,"Work disapproved successfully");
                ViewUtils.returnToTicketStatusPage(getClass(), (Stage) btnApproveWork.getScene().getWindow());
                //labelWorkStatus.setText("InProgress");
//                String reason = textAreaFeedback.getText();
//                if (reason.equals("")){
//                    reason="no reason";
//                }
                //textAreaFeedback.setText("Work on ticket " + selectedTicket + " has been disapproved on " + date + "for "+reason);
            }else{
                ViewUtils.showError(msg);
            }
        }else {
            ViewUtils.showError("Please choose a ticket and a feedback date first.");
        }
    }

    @FXML
    private void handleCancel() {
        ViewUtils.returnToTicketStatusPage(getClass(), (Stage) btnApproveWork.getScene().getWindow());
    }
}

