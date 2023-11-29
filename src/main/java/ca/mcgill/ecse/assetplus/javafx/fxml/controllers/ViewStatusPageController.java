package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

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
        TOMaintenanceTicket selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket != null) {
            openEditTicketPage(selectedTicket);
        } else {
            System.out.println("No ticket selected.");
        }
    }

    @FXML
    public void deleteTicketClicked(ActionEvent event) {
        return;
    }

    /**
     * Opens editTicketPage and passes selected ticket to the page.
     * @param selectedTicket Ticket selected in the ListView.
     * @author Manuel Hanna
     */
    // Method to open the edit page and pass the selected ticket
    public void openEditTicketPage(TOMaintenanceTicket selectedTicket) {
        System.out.println("edit button clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTicketPage.fxml"));
            Parent root = loader.load();

            EditTicketPageController editTicketPageController = loader.getController();
            editTicketPageController.initialize(selectedTicket);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Ticket");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
