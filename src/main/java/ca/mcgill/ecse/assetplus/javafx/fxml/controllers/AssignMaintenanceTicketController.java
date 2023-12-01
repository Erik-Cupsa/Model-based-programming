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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import javafx.stage.Stage;

import java.io.IOException;

import static ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController.assignStaffToTicket;
import static ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicketController.stringPriority;
import static ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicketController.stringTimeEstimate;
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.makePopupWindow;
import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.showError;

public class AssignMaintenanceTicketController {
    private int ticketID;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<TOUser,String> emailColumn;

    @FXML
    private TableView<TOUser> employeeTable;

    @FXML
    private CheckBox managerApprovalCheck;

    @FXML
    private TableColumn<TOUser,String> nameColumn;

    @FXML
    private ChoiceBox<String> priorityDropDown;

    @FXML
    private ChoiceBox<String> resolveDropDown;

    @FXML
    private Button assignButton;



    @FXML
    public void initialize(){
        emailColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getEmail()));
        nameColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getName()));
        priorityDropDown.getItems().addAll("Urgent","Normal","Low");
        resolveDropDown.getItems().addAll("LessThanADay", "OneToThreeDays", "ThreeToSevenDays", "OneToThreeWeeks", "ThreeOrMoreWeeks");
        showAllEmployees();
    }
    @FXML
    private void showAllEmployees(){
        ObservableList<TOUser> userList = FXCollections.observableArrayList(TOUserController.getUsers());
        employeeTable.setItems(userList);
        employeeTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> employeeTable.setItems(userList));
    }
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
    public void setTicketID(int id){
        ticketID=id;
    }

    @FXML
    void assignClicked(ActionEvent event) {
        TOUser selectedUser = employeeTable.getSelectionModel().getSelectedItem();
        String timeEstimate = resolveDropDown.getSelectionModel().getSelectedItem();
        String priority = priorityDropDown.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showError("No Employee selected");
        }
        else if (timeEstimate == null)showError("No time estimate selected");
        else if (priority == null)showError("No priority selected");
        else {
            try {
                String selectedEmail = selectedUser.getEmail();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MainPage.fxml"));
                Parent newRoot = fxmlLoader.load();
                String err = assignStaffToTicket(ticketID, selectedEmail, stringTimeEstimate(resolveDropDown.getValue()), stringPriority(priorityDropDown.getValue()), managerApprovalCheck.isSelected());
                if ("" != err) {
                    showError(err);
                }
                makePopupWindow("Ticket Assignment Successful","Selected ticket is now assigned");
                ViewStatusPageController viewStatusPageController =ViewStatusPageController.getInstance();
                viewStatusPageController.showAllTickets();
                Stage currentStage = (Stage) assignButton.getScene().getWindow();
                currentStage.getScene().setRoot(newRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}