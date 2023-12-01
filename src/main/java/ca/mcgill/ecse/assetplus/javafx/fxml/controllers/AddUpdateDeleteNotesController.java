package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class AddUpdateDeleteNotesController {
    private TOMaintenanceTicket ticket;

    @FXML
    private TextField addNoteText;
    @FXML
    private DatePicker noteDatePicker;
    @FXML
    private TextField userEmailText;
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


    public void setTicket(TOMaintenanceTicket ticket) {
        this.ticket = ticket;
        int id = ticket.getId();
        noteName.setText(String.valueOf(id));
        filltable();
    }

    @FXML
    public void initialize() {
        noteTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void filltable() {
        ticket = findTicket(ticket.getId());
        noteWriter.setCellValueFactory(new PropertyValueFactory<TOMaintenanceNote, String>("noteTakerEmail"));
        noteDate.setCellValueFactory(new PropertyValueFactory<TOMaintenanceNote, Date>("date"));
        noteText.setCellValueFactory(new PropertyValueFactory<TOMaintenanceNote, String>("description"));
        noteTable.setItems(FXCollections.observableList(ticket.getNotes()));
        noteTable.refresh();
    }

    private TOMaintenanceTicket findTicket(int id) {
        for (TOMaintenanceTicket t : ViewUtils.getTickets()) {
            if (t.getId() == id) {
                System.out.println(t.getId());
                return t;
            }
        }
        System.out.println("ERROR");
        return null;
    }

    @FXML
    public void tableClicked(Event event) {
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
        String email = userEmailText.getText();
        String error = AssetPlusFeatureSet7Controller.addMaintenanceNote(openDate, description, ticket.getId(),
                email);
        System.out.println(ticket.getNotes());
        if (!error.isEmpty()) {
            ViewUtils.makePopupWindow("Error", error);
        }
        filltable();
        System.out.println(ticket.getNotes());
    }


    @FXML
    public void noteUpdateClicked(ActionEvent event) {
        if (noteTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (noteDatePicker.getValue() == null) {
            ViewUtils.makePopupWindow("Error", "Enter a Date");
            return;
        }
        String description = addNoteText.getText();
        Date openDate = Date.valueOf(noteDatePicker.getValue());
        String email = userEmailText.getText();
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
