package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewStatusPageController{
    @FXML
    private TableView<TOMaintenanceTicket> ticketsTableView;
    @FXML
    private TableColumn<TOMaintenanceTicket, Integer> numberColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> issuerColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> statusColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> dateRaisedColumn;
    @FXML
    private Button addTicketButton;
    @FXML
    private Button editTicketButton;
    @FXML
    private Button deleteTicketButton;
    @FXML
    private Button assignTicketButton;
    @FXML
    private Button solveTicketButton;

    @FXML
    public void initialize() {
        ViewUtils.loadTicketsIntoTableView(ticketsTableView, numberColumn, issuerColumn, statusColumn, dateRaisedColumn);
    }

    @FXML
    public void addTicketClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../pages/AddMaintenanceTicket.fxml"));
            Parent newRoot = fxmlLoader.load();

            AddMaintenanceTicketController addMaintenanceTicketController = fxmlLoader.getController();
            // Access the current stage
            Stage currentStage = (Stage) addTicketButton.getScene().getWindow();

            // Replace the content in the current scene with content loaded from the new FXML
            currentStage.getScene().setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

    }

    @FXML
    public void editTicketClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../pages/EditMaintenanceTicket.fxml"));
            Parent newRoot = fxmlLoader.load();

            EditMaintenanceTicketController editMaintenanceTicketController = fxmlLoader.getController();
            // Access the current stage
            Stage currentStage = (Stage) editTicketButton.getScene().getWindow();

            // Replace the content in the current scene with content loaded from the new FXML
            currentStage.getScene().setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    @FXML
    public void deleteTicketClicked(ActionEvent event) {
        TOMaintenanceTicket selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            System.out.println("No ticket selected.");
            return;
        }

        int id = selectedTicket.getId();
        AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(id);
        ticketsTableView.getItems().remove(selectedTicket);
    }


}
