package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import ca.mcgill.ecse.assetplus.controller.TOUserController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController.assignStaffToTicket;
import static ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicketController.stringPriority;
import static ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicketController.stringTimeEstimate;
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.makePopupWindow;
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.showError;

public class AssignMaintenanceTicketController {

    @FXML
    private ComboBox<String> comboTicket;

    @FXML
    private ComboBox<String> comboEmployee;
    @FXML
    private Button cancelButton;

//    @FXML
//    private TableColumn<TOUser,String> emailColumn;

//    @FXML
//    private TableView<TOUser> employeeTable;

    @FXML
    private CheckBox managerApprovalCheck;

//    @FXML
//    private TableColumn<TOUser,String> nameColumn;

    @FXML
    private ChoiceBox<String> priorityDropDown;

    @FXML
    private ChoiceBox<String> resolveDropDown;

    @FXML
    private Button assignButton;
    private List<TOMaintenanceTicket> ticketList;
    private List<TOUser> employeeList;
    private TOUser selectedEmployee;
    private TOMaintenanceTicket selectedTicket;


    @FXML
    public void initialize(){
        populateTickets();
        populateEmployees();
        comboTicket.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTicketSelection(newSelection);
            }
        });
        comboEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateEmployeeSelection(newSelection);
            }
        });
        //emailColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getEmail()));
        //nameColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getName()));
        priorityDropDown.getItems().addAll("Urgent","Normal","Low");
        resolveDropDown.getItems().addAll("LessThanADay", "OneToThreeDays", "ThreeToSevenDays", "OneToThreeWeeks", "ThreeOrMoreWeeks");
        //showAllEmployees();
    }

    private void updateEmployeeSelection(String newSelection) {
        populateEmployees();

        for (TOUser employee:employeeList) {
            if(employee.getEmail().equals(newSelection)){
                //labelWorkStatus.setText(ticket.getStatus());
                selectedEmployee=employee;
                break;
            }
        }
    }

    private void populateEmployees() {
        employeeList = ViewUtils.getEmployees();
        ArrayList<String> EmployeesEmails= new ArrayList<>();
        for (TOUser employee:employeeList ) {
            EmployeesEmails.add(String.valueOf(employee.getEmail()));
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(EmployeesEmails);
        comboEmployee.setItems(observableList);
    }

    private void updateTicketSelection(String selectedTicket) {
        populateTickets();
        for (TOMaintenanceTicket ticket:ticketList) {
            if(ticket.getId()==Integer.parseInt(selectedTicket)){
                this.selectedTicket=ticket;
                //labelWorkStatus.setText(ticket.getStatus());
                break;
            }
        }
    }
    private void populateTickets() {
        ticketList = ViewUtils.getTickets();
        ArrayList<String> ticketIds= new ArrayList<>();
        for (TOMaintenanceTicket ticket:ticketList ) {
            ticketIds.add(String.valueOf(ticket.getId()));
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(ticketIds);
        comboTicket.setItems(observableList);
    }

//    @FXML
//    private void showAllEmployees(){
//        ObservableList<TOUser> userList = FXCollections.observableArrayList(TOUserController.getUsers());
//        employeeTable.setItems(userList);
//        employeeTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> employeeTable.setItems(userList));
//    }
    @FXML
    void cancelClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MainPage.fxml"));
            Parent newRoot = fxmlLoader.load();
            // Access the current stage
            Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            // Replace the content in the current scene with content loaded from the new FXML
            currentStage.getScene().setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    @FXML
    void assignClicked(ActionEvent event) {
        if (selectedEmployee==null){
            showError("Please select an employee.");
        }else if (selectedTicket==null){
            showError("Please select a ticket.");
        } else if (resolveDropDown.getValue()==null || resolveDropDown.getValue().equals("") ) {
            showError("Please select a resolve time.");
        } else if (priorityDropDown.getValue()==null || priorityDropDown.getValue().equals("")) {
            showError("Please select a priority.");
        } else {

            String err=assignStaffToTicket(selectedTicket.getId(),selectedEmployee.getEmail(),
                    stringTimeEstimate(resolveDropDown.getValue()),stringPriority(priorityDropDown.getValue()),managerApprovalCheck.isSelected());
            if (!err.equals("")){
                showError(err);
            }else {
                makePopupWindow("Ticket Assignment Successful","Selected ticket is now assigned");
            }
        }
//        TOUser selectedUser = employeeTable.getSelectionModel().getSelectedItem();
//
//        if (selectedUser != null) {
//            String selectedEmail = selectedUser.getEmail();
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MainPage.fxml"));
//                Parent newRoot = fxmlLoader.load();
////                //String err= assignStaffToTicket(id, selectedEmail, stringTimeEstimate(resolveDropDown.getValue()), stringPriority(priorityDropDown.getValue()), managerApprovalCheck.isSelected());
////                if ("" != err) {
////                    showError(err);
////                }
//                Stage currentStage = (Stage) assignButton.getScene().getWindow();
//                currentStage.getScene().setRoot(newRoot);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else showError("No Employee selected");
    }
}
