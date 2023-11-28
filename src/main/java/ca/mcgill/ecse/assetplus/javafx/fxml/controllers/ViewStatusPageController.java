package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

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

    @FXML
    public void addTicketClicked(ActionEvent event) {
        System.out.println("add ticket button clicked");

    }

    @FXML
    public void editTicketClicked(ActionEvent event) {
        ViewUtils.editSelectedTicket(ticketsListView);
    }


}
