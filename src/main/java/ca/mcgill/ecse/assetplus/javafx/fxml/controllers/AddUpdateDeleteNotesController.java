package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller.*;
import java.sql.Date;

import ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.SelectionMode;

import java.util.ArrayList;
import java.util.List;

public class AddUpdateDeleteNotesController {
	private String email;
	private int ticket;
	private ObservableList<TOMaintenanceTicket> tickets;
	
	@FXML
	private TextField addNoteText;
	@FXML
	private TableView<ToMaintenanceNote> noteTable;
	@FXML
	private TableColumn<ToMaintenanceNote, String> noteWriter;
	@FXML
	private TableColumn<ToMaintenanceNote, Date> noteDate;
	@FXML
	private TableColumn<ToMaintenanceNote, String> noteText;
	@FXML
	private Button noteAdd;
	@FXML
	private Button noteUpdate;
	@FXML
	private Button noteDelete;
	@FXML
	private Label noteName;

	
	public AddUpdateDeleteNotesController(ToMaintenanceTicket ticket, String email) {
		this.ticket = ticket;
		this.email = email;
	}
	
    @FXML
    public void initialize() {
    	noteTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	noteWriter.setCellValueFactory(new PropertyValueFactory<ToMaintenanceNote, String>("noteTakerEmail"));
    	noteDate.setCellValueFactory(new PropertyValueFactory<ToMaintenanceNote, Date>("date"));
    	noteText.setCellValueFactory(new PropertyValueFactory<ToMaintenanceNote, Date>("description"));
    	noteTable.setItems(FXCollections.observableList(ticket.notes));
    }
    
    @FXML
    public void tableClicked() {
    	String text = noteTable.getSelectedItems();
    	if (text.isEmpty()) {
    		noteUpdate.setStyle("-fx-background-color: #A9A9A9;");
    		noteDelete.setStyle("-fx-background-color: #A9A9A9;");
    	}
    	else {
    		noteUpdate.setStyle(null);
    		noteDelete.setStyle(null);
    		addNoteText.setText(text);
    	}
    }
    
	  @FXML
	  public void noteAddClicked(ActionEvent event) {
		String description = addNoteText.getText();
		String error = AssetPlusFeatureSet7Controller.addMaintenanceNote(new Date(System.currentTimeMillis()), description, ticket,
				email);
		if (!error.isEmpty()) {
			ViewUtils.makePopupWindow("Error", error);
		}
	  }

	  
	  @FXML
	  public void noteUpdateClicked(ActionEvent event) {
		if (noteTable.getSelectedItems() == null){
			return;
		}
		String description = addNoteText.getText();
		int index = ticket.notes.indexOf(noteTable.getSelectedItems()[0]);
		String error = AssetPlusFeatureSet7Controller.updateMaintenanceNote(ticket, index, new Date(System.currentTimeMillis()), description,
				email);
		if (!error.isEmpty()) {
			ViewUtils.makePopupWindow("Error", error);
		}
	  }

	  @FXML
	  public void noteDeleteClicked(ActionEvent event) {
		if (noteTable.getSelectedItems() == null){
			return;
		}
		String index = ticket.notes.indexOf(noteTable.getSelectedItems()[0]);
		AssetPlusFeatureSet7Controller.deleteMaintenanceNote(new Date(System.currentTimeMillis()), description, ticket,
					email);
		if (!error.isEmpty()) {
			ViewUtils.makePopupWindow("Error", error);
		}
	  }
	}