package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class AddUpdateDeleteNotesController {
    private TOMaintenanceTicket ticket;
    private ObservableList<TOMaintenanceTicket> tickets;

    @FXML
    private TextField addNoteText;
    @FXML
    private DatePicker noteDatePicker;
    @FXML
    private TextField noteEmailText;
    @FXML
    private TableView<TOMaintenanceNote> noteTable;
    @FXML
    private TableColumn<TOMaintenanceNote, String> noteWriter;
    @FXML
    private TableColumn<TOMaintenanceNote, Date> noteDate;
    @FXML
    private TableColumn<TOMaintenanceNote, String> noteText;
    @FXML
    private Button noteAdd;
    @FXML
    private Button noteUpdate;
    @FXML
    private Button noteDelete;
    @FXML
    private Label noteName;


    public AddUpdateDeleteNotesController(TOMaintenanceTicket ticket) {
        this.ticket = ticket;
    }

    @FXML
    public void initialize() {
        noteTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        int id = ticket.getId();
        noteName.setText(String.valueOf(id));
        filltable();
    }

    private void filltable() {
        noteWriter.setCellValueFactory(new PropertyValueFactory<TOMaintenanceNote, String>("noteTakerEmail"));
        noteDate.setCellValueFactory(new PropertyValueFactory<TOMaintenanceNote, Date>("date"));
        noteText.setCellValueFactory(new PropertyValueFactory<TOMaintenanceNote, String>("description"));
        noteTable.setItems(FXCollections.observableList(ticket.getNotes()));
    }

    @FXML
    public void tableClicked() {
        TOMaintenanceNote note = noteTable.getSelectionModel().getSelectedItem();
        if (note == null) {
            noteUpdate.setStyle("-fx-background-color: #A9A9A9;");
            noteDelete.setStyle("-fx-background-color: #A9A9A9;");
        } else {
            noteUpdate.setStyle(null);
            noteDelete.setStyle(null);
            addNoteText.setText(note.getDescription());
        }
    }

    @FXML
    public void noteAddClicked(ActionEvent event) {
        String description = addNoteText.getText();
        Date openDate = Date.valueOf(noteDatePicker.getValue());
        String email = noteEmailText.getText();
        String error = AssetPlusFeatureSet7Controller.addMaintenanceNote(openDate, description, ticket.getId(),
                email);
        if (!error.isEmpty()) {
            ViewUtils.makePopupWindow("Error", error);
        }
        filltable();
    }


    @FXML
    public void noteUpdateClicked(ActionEvent event) {
        if (noteTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String description = addNoteText.getText();
        Date openDate = Date.valueOf(noteDatePicker.getValue());
        String email = noteEmailText.getText();
        int index = ticket.getNotes().indexOf(noteTable.getSelectionModel().getSelectedItem());
        String error = AssetPlusFeatureSet7Controller.updateMaintenanceNote(ticket.getId(), index, openDate, description,
                email);
        if (!error.isEmpty()) {
            ViewUtils.makePopupWindow("Error", error);
        }
        filltable();
    }

    @FXML
    public void noteDeleteClicked(ActionEvent event) {
        if (noteTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = ticket.getNotes().indexOf(noteTable.getSelectionModel().getSelectedItem());
        AssetPlusFeatureSet7Controller.deleteMaintenanceNote(ticket.getId(), index);
        filltable();
    }
}
