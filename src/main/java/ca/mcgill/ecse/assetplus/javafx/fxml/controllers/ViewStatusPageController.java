package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewStatusPageController{
    @FXML
    private ListView<TOMaintenanceTicket> ticketsListView;
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
        ViewUtils.loadTickets(ticketsListView);
    }

    public void setTicketsListView(ListView<TOMaintenanceTicket> tickets){
        this.ticketsListView = tickets;
        initialize();
    }

    @FXML
    public void addTicketClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../pages/AddMaintenanceTicket.fxml"));
            System.out.println("add");
            Parent newRoot = fxmlLoader.load();

            AddMaintenanceTicketController addMaintenanceTicketController = fxmlLoader.getController();
            addMaintenanceTicketController.initialize();
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
        ViewUtils.editSelectedTicket(ticketsListView);
    }


}
