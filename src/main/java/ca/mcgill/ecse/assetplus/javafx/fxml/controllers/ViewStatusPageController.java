package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.showError;

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
    private TableColumn<TOMaintenanceTicket, String> fixerColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> timeToResolveColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> priorityColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, Boolean> approvalRequiredColumn;
    @FXML
    private Button addTicketButton;
    @FXML
    private Button editTicketButton;
    @FXML
    private Button deleteTicketButton;
    @FXML
    private Button solveTicketButton;
    @FXML
    private Button startWorkButton;
    @FXML
    private Button completeWorkButton;

    @FXML
    private Button assignButton;
    @FXML
    private GridPane assetGridPane;
    @FXML
    private Button addImageButton;
    @FXML
    private Button deleteImage;
    @FXML
    private Button approveDisapproveWorkButton;
    @FXML
    private Button editNotesButton;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField ticketRaiserTextField;
    @FXML
    private TextField assetNumberTextField;
    @FXML
    private DatePicker dateRaisedOnPicker;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField image;

    @FXML
    private Label assetName;
    @FXML
    private Label assetLifespan;
    @FXML
    private Label assetPurchaseDate;
    @FXML
    private Label assetFloorNumber;
    @FXML
    private Label assetRoomNumber;

    @FXML
    private ComboBox<String> filterDropdown;
    @FXML
    private TextField filterField;
    @FXML
    private ListView imageUrlListView;
    @FXML
    private ListView imageListView;

    @FXML
    public void initialize() {
        assetName.setText("");
        assetLifespan.setText("");
        assetPurchaseDate.setText("");
        assetFloorNumber.setText("");
        assetRoomNumber.setText("");

        filterDropdown.getItems().add("Ticket Date");
        filterDropdown.getItems().add("Ticket Raiser");

        disableButtonsOption(true);
        refresh();
    }

    private void disableButtonsOption(Boolean disabled) {
        startWorkButton.setDisable(disabled);
        completeWorkButton.setDisable(disabled);
        approveDisapproveWorkButton.setDisable(disabled);
        assignButton.setDisable(disabled);
        editNotesButton.setDisable(disabled);
        addImageButton.setDisable(disabled);
        deleteImage.setDisable(disabled);
        editTicketButton.setDisable(disabled);
        deleteTicketButton.setDisable(disabled);
    }

    private void refresh() {
        ViewUtils.loadTicketsIntoTableView(
                ticketsTableView,
                numberColumn,
                issuerColumn,
                statusColumn,
                dateRaisedColumn,
                fixerColumn,
                timeToResolveColumn,
                priorityColumn,
                approvalRequiredColumn
        );
    }

    @FXML
    public void addTicketClicked(ActionEvent event) {

        AddMaintenanceTicketController.doneAddClicked(idTextField, descriptionTextField, ticketRaiserTextField, assetNumberTextField, dateRaisedOnPicker);
        refresh();

    }
    @FXML
    void assignClicked(ActionEvent event) {
        TOMaintenanceTicket selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            try {
                int tickedID= selectedTicket.getId();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../pages/AssignMaintenanceTicket.fxml"));
                Parent newRoot = fxmlLoader.load();
                AssignMaintenanceTicketController assignMaintenanceTicketController = fxmlLoader.getController();
                assignMaintenanceTicketController.setTicketID(tickedID);
                Stage currentStage = (Stage) assignButton.getScene().getWindow();
                currentStage.getScene().setRoot(newRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else showError("No ticket selected");
    }
    public void showAllTickets(){
        ObservableList<TOMaintenanceTicket> ticketList = FXCollections.observableArrayList(AssetPlusFeatureSet6Controller.getTickets());
        ticketsTableView.setItems(ticketList);
        ticketsTableView.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> ticketsTableView.setItems(ticketList));
    }
    @FXML
    public void addImageClicked(ActionEvent event) {
        TOMaintenanceTicket selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            ViewUtils.showError("Please choose a ticket first");
            return;
        }
        String text = AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(image.getText(), selectedTicket.getId());
        if(!text.equals("")){
            ViewUtils.showError(text);
        }
        refresh();
    }
    @FXML
    public void deleteImageClicked(ActionEvent event) {
        TOMaintenanceTicket selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            ViewUtils.showError("Please choose a ticket first");
            return;
        }

        AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(image.getText(), selectedTicket.getId());
        refresh();
    }


    @FXML
    public void editTicketClicked(ActionEvent event) {
        TOMaintenanceTicket selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            ViewUtils.showError("Please choose a ticket first");
            return;
        }

        if (dateRaisedOnPicker.getValue() == null) {
            ViewUtils.showError("Please fill in the date raised field.");
        }
        else if (assetNumberTextField.getText().equals("")) {
            ViewUtils.showError("Please fill in the asset number field.");
        }
        EditMaintenanceTicketController.doneEditClicked(idTextField, descriptionTextField, ticketRaiserTextField, assetNumberTextField, dateRaisedOnPicker);
        refresh();
    }
    private static ViewStatusPageController instance;
    public ViewStatusPageController(){
        instance=this;
    }

    public static ViewStatusPageController getInstance(){
        return  instance;
    }
    @FXML
    private void editNotesButtonClicked(ActionEvent event) {
        TOMaintenanceTicket ticket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (ticket == null) {
            ViewUtils.showError("Please choose a ticket first");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../pages/AddUpdateDeleteNotes.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 630, 400);
            AddUpdateDeleteNotesController notescontroller = fxmlLoader.getController();
            notescontroller.setTicket(ticket);

            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void deleteTicketClicked(ActionEvent event) {
        TOMaintenanceTicket selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            ViewUtils.showError("Please choose a ticket first");
            return;
        }

        int id = selectedTicket.getId();
        AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(id);
        refresh();
    }

    @FXML
    public void startWorkClicked(ActionEvent event) {
        TOMaintenanceTicket ticket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            StartCompleteWorkOnMaintenanceTicketPageController.handleStartWork(ticket);
            refresh();
        }
        else {
            ViewUtils.showError("Please choose a ticket first");
        }
    }

    @FXML
    public void completeWorkClicked(ActionEvent event) {
        TOMaintenanceTicket ticket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            StartCompleteWorkOnMaintenanceTicketPageController.handleCompleteWork(ticket);
            refresh();
        }
        else {
            ViewUtils.showError("Please choose a ticket first");
        }
    }

    @FXML
    public void approveDisapproveWorkClicked(ActionEvent event) {
        TOMaintenanceTicket ticket = ticketsTableView.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../pages/ApproveDisapproveOnMaintenanceTicket.fxml"));
                Parent newRoot = fxmlLoader.load();

                ApproveDisapproveWorkOnMaintenanceTicketController approveDisapproveWorkOnMaintenanceTicketController = fxmlLoader.getController();
                approveDisapproveWorkOnMaintenanceTicketController.updateTicketSelection(ticket);
                // Access the current stage
                Stage currentStage = (Stage) editTicketButton.getScene().getWindow();

                // Replace the content in the current scene with content loaded from the new FXML
                currentStage.getScene().setRoot(newRoot);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception as needed
            }
        } else {
            ViewUtils.showError("Please choose a ticket first");
        }
    }

    @FXML
    private void filterClicked() {
        String text = filterField.getText();
        if (text.equals("")) {refresh();}
        else {
            ticketsTableView.getItems().clear();
            String selectedOption = filterDropdown.getValue();
            if (selectedOption == null) {
                ViewUtils.showError("Please select a filter option first");
            }
            ObservableList<TOMaintenanceTicket> ticketsToFilter = ViewUtils.getTickets();
            ObservableList<TOMaintenanceTicket> filteredTickets = FXCollections.observableList(new ArrayList<TOMaintenanceTicket>());
            for (TOMaintenanceTicket ticket : ticketsToFilter) {
                if (selectedOption.equals("Ticket Date")) {
                    System.out.println("check:" + text);
                    System.out.println("in list:" + ticket.getRaisedOnDate().toString());
                    if (text.equals(ticket.getRaisedOnDate().toString())) {
                        filteredTickets.add(ticket);
                    }
                }
                else if (selectedOption.equals("Ticket Raiser")) {
                    if (text.equals(ticket.getRaisedByEmail())) {
                        filteredTickets.add(ticket);
                    }
                }
            }
            ViewUtils.loadTicketsIntoTableView(ticketsTableView, numberColumn, issuerColumn, statusColumn, dateRaisedColumn, fixerColumn, timeToResolveColumn, priorityColumn, approvalRequiredColumn, filteredTickets);
        }
    }

    @FXML
    private void clearFilterClicked() {
        filterField.clear();
        refresh();
    }

    @FXML
    void clickOutsideOfView() {
        disableButtonsOption(true);
    }
    @FXML
    void updateView(MouseEvent event) {

        TOMaintenanceTicket selected = ticketsTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            disableButtonsOption(true);
        }
        else {
            disableButtonsOption(false);
        }

        idTextField.setText(String.valueOf(selected.getId()));
        ticketRaiserTextField.setText(selected.getRaisedByEmail());
        descriptionTextField.setText(selected.getDescription());

        assetName.setText(selected.getAssetName());
        if (selected.getAssetName()==null || selected.getAssetName().equals("")){
            assetName.setText("N/A");
        }
        assetLifespan.setText(String.valueOf(selected.getExpectLifeSpanInDays()));
        if (String.valueOf(selected.getExpectLifeSpanInDays()).equals("-1")){
            assetLifespan.setText("N/A");
        }
        try {
            assetPurchaseDate.setText(selected.getPurchaseDate().toString());
        }catch (Exception e){
            assetPurchaseDate.setText("N/A");
        }
        assetFloorNumber.setText(String.valueOf(selected.getFloorNumber()));
        if (String.valueOf(selected.getFloorNumber()).equals("-1")){
            assetFloorNumber.setText("N/A");
        }

        assetRoomNumber.setText(String.valueOf(selected.getRoomNumber()));
        if (String.valueOf(selected.getRoomNumber()).equals("-1")){
            assetRoomNumber.setText("N/A");
        }

        imageListView.getItems().clear();
        for (String path: selected.getImageURLs()) {
            imageListView.getItems().add(new ImageView(new Image(path, 80, 80, false, false)));
        }
        imageUrlListView.setItems(FXCollections.observableArrayList(selected.getImageURLs()));
        //AssetPlusFxmlView.getInstance().refresh();
    }
}