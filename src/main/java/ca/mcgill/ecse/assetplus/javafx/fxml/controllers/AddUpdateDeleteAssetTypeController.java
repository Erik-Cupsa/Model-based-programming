package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetTypeController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class AddUpdateDeleteAssetTypeController {

    @FXML
    private Label statusLabel;

    @FXML
    private Button addAssetTypeButton;

    @FXML
    private TextField assetTypeExpectedLifeSpanTextField;

    @FXML
    private TableColumn<TOAssetType, String> assetTypeNameColumn;

    @FXML
    private TextField assetTypeNameTextField;

    @FXML
    private TableView<TOAssetType> assetTypeTable;

    @FXML
    private Button deleteAssetTypeButton;

    @FXML
    private TableColumn<TOAssetType, String> expectedLifeSpanColumn;

    @FXML
    private Button updateAssetTypeButton;

    @FXML
    private Button clearButton;

    @FXML
    public void initialize(){
        expectedLifeSpanColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getExpectedLifeSpan())));
        assetTypeNameColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getName()));
        showAllAssetTypes();
    }

    private void showAllAssetTypes(){
        ObservableList<TOAssetType> assetTypeList = FXCollections.observableArrayList(TOAssetTypeController.getAssetTypes());
        assetTypeTable.setItems(assetTypeList);
        assetTypeTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> assetTypeTable.setItems(assetTypeList));
    }

    private void clearData(){
        assetTypeExpectedLifeSpanTextField.clear();
        assetTypeNameTextField.clear();
    }


     @FXML
     void updateView(MouseEvent event) {
         TOAssetType selected = assetTypeTable.getSelectionModel().getSelectedItem();
         assetTypeExpectedLifeSpanTextField.setText(String.format("%d", selected.getExpectedLifeSpan()));
         assetTypeNameTextField.setText(selected.getName());
     }

    @FXML
    void addAssetTypeClicked(ActionEvent event) {
        String name = assetTypeNameTextField.getText();
        String expectedLifeSpan = assetTypeExpectedLifeSpanTextField.getText();
        
        String err = AssetPlusFeatureSet2Controller.addAssetType(name, Integer.parseInt(expectedLifeSpan));
        if(err.isEmpty()){
            showMessage("Asset Type with name " + name + " added successfully.", true);
            clearData();
            showAllAssetTypes();
        }
        else{
            ViewUtils.showError(err);
        }
    }

    private void showMessage(String msg, boolean success){
        statusLabel.setText(msg);
        if(success){
            statusLabel.setTextFill(Color.GREEN);
        }
        else{
            statusLabel.setTextFill(Color.RED);
        }
    }

    private TOAssetType getAssetType(){
        TOAssetType a = null;
        try{
            int expectedLifeSpan = Integer.parseInt(assetTypeExpectedLifeSpanTextField.getText());
            String name = assetTypeNameTextField.getText();
            a = new TOAssetType(expectedLifeSpan, name);
        }catch(NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    @FXML
    void deleteAssetTypeClicked(ActionEvent event) {
        TOAssetType selected = assetTypeTable.getSelectionModel().getSelectedItem();
        AssetPlusFeatureSet2Controller.deleteAssetType(selected.getName());
        selected.delete();
        showAllAssetTypes();
    }

    @FXML
    void updateAssetTypeClicked(ActionEvent event) {
        Integer newExpectedLifeSpan = Integer.parseInt(assetTypeExpectedLifeSpanTextField.getText());
        String newName = assetTypeNameTextField.getText();
        try{
            AssetPlusFeatureSet2Controller.addAssetType(newName, newExpectedLifeSpan);
            TOAssetTypeController.setName(assetTypeTable.getSelectionModel().getSelectedItem(), assetTypeNameTextField.getText());
            TOAssetTypeController.setExpectedLifeSpan(assetTypeTable.getSelectionModel().getSelectedItem(), Integer.parseInt(assetTypeExpectedLifeSpanTextField.getText()));
        }catch(Error e){
            showMessage(e.getMessage(), false);
        }
        showAllAssetTypes();
    }

    @FXML
    void clearClicked(ActionEvent event) {
        clearData();
    }

}
