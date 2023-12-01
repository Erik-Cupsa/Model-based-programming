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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<TOMaintenanceTicket, String> fixerColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> timeToResolveColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> priorityColumn;
    @FXML
    private TableColumn<TOMaintenanceTicket, String> approvalRequiredColumn;
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
    private Button startWorkButton;
    @FXML
    private Button completeWorkButton;
    @FXML
    private Button addImageButton;
    @FXML
    private Button deleteImageButton;

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
    private Label imageURLs;

    @FXML
    public void initialize() {
        assetName.setText("");
        assetLifespan.setText("");
        assetPurchaseDate.setText("");
        assetFloorNumber.setText("");
        assetRoomNumber.setText("");
        refresh();
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
        if(text.equals("")){
            text = "Image successfully added";
        }
        ViewUtils.showError(text);
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
        ViewUtils.showError("Image deleted successfully");
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
    void updateView(MouseEvent event) {

        TOMaintenanceTicket selected = ticketsTableView.getSelectionModel().getSelectedItem();

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
        imageURLs.setText(selected.getImageURLs().toString());
        AssetPlusFxmlView.getInstance().refresh();
    }
}
