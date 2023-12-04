package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.TOAsset;
import ca.mcgill.ecse.assetplus.controller.TOAssetController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.time.ZoneId;

public class AddUpdateDeleteAssetController {

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<TOAsset, String> assetNumberColumn;

    @FXML
    private TextField assetNumberTextField;

    @FXML
    private TableView<TOAsset> assetTable;

    @FXML
    private TableColumn<TOAsset, String> assetTypeColumn;

    @FXML
    private TextField assetTypeTextField;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<TOAsset, String> floorNumberColumn;

    @FXML
    private TextField floorNumberTextField;

    @FXML
    private TableColumn<TOAsset, String> purchaseDateColumn;

    @FXML
    private DatePicker purchaseDateField;

    @FXML
    private TableColumn<TOAsset, String> roomNumberColumn;

    @FXML
    private TextField roomNumberTextField;

    @FXML
    private Button updateButton;

    private static AddUpdateDeleteAssetController instance;

    /**
    * sets the value of the assetController to the one used
   * @author David Marji.
   */
    public AddUpdateDeleteAssetController() {
        instance = this;
    }

    /**
    * returns the instance controller so it can be updated in the view asset type controller
   * @return the current asset controller
   * @author David Marji.
   */
    public static AddUpdateDeleteAssetController getInstance() {
        return instance;
    }


    /**
    * initializes all the view aspects (columns of the table) to show specific data
   * @author David Marji.
   */
    @FXML
    public void initialize() {
        roomNumberColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getRoomNumber())));
        floorNumberColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getFloorNumber())));
        purchaseDateColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getPurchaseDate().toString()));
        assetNumberColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getAssetNumber())));
        assetTypeColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getTypeName()));

        showAllAssets();
    }

    /**
    * A method used to show all the assets by updating the tables information
   * @author David Marji.
   */
    public void showAllAssets() {
        ObservableList<TOAsset> assetList = FXCollections.observableArrayList(TOAssetController.getAssets());
        assetTable.setItems(assetList);
        assetTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> assetTable.setItems(assetList));
    }

    @FXML
    /**
    * fills in the text fields with the values of the selected asset from the table
   * @author David Marji.
   */
    void updateView(MouseEvent event) {

        TOAsset selected = assetTable.getSelectionModel().getSelectedItem();

        assetTypeTextField.setText(String.valueOf(selected.getTypeName()));
        assetNumberTextField.setText(String.valueOf(selected.getAssetNumber()));
        floorNumberTextField.setText(String.valueOf(selected.getFloorNumber()));
        roomNumberTextField.setText(String.valueOf(selected.getRoomNumber()));
        Date date = selected.getPurchaseDate();
        purchaseDateField.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

//        if (selected.getTypeName()==null || selected.getTypeName().equals("")){
//            assetTypeTextField.setText("");
//        }
//        if (selected.getAssetNumber()==null || selected.getTypeName().equals("")){
//            assetTypeTextField.setText("");
//        }
//        if (selected.getTypeName()==null || selected.getTypeName().equals("")){
//            assetTypeTextField.setText("");
//        }
//        if (selected.getTypeName()==null || selected.getTypeName().equals("")){
//            assetTypeTextField.setText("");
//        }
        AssetPlusFxmlView.getInstance().refresh();
    }

    @FXML
    /**
    * adds an asset to the list of assets stored if the information filled in is of a proper format
   * @author David Marji.
   */
    void addClicked(ActionEvent event) {
        String typeName = assetTypeTextField.getText();
        int roomNumber = Integer.parseInt(roomNumberTextField.getText());
        int floorNumber = Integer.parseInt(floorNumberTextField.getText());
        int assetNumber = Integer.parseInt(assetNumberTextField.getText());

        Date purchaseDate = java.sql.Date.valueOf(purchaseDateField.getValue());

        String err = AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, typeName);
        if (err.isEmpty()) {
            ViewUtils.makePopupWindow("Add An Asset", "Asset " + assetNumber + " added successfully");
            showAllAssets();
            AssetPlusFxmlView.getInstance().refresh();
        } else {
            ViewUtils.showError(err);
            showAllAssets();
            AssetPlusFxmlView.getInstance().refresh();
        }
    }

    @FXML
    /**
    * clears all the textfields of their information
   * @author David Marji.
   */
    void clearButtonClicked(ActionEvent event) {
        clearData();
    }

    /**
    * clears all the textfields of their information
   * @author David Marji.
   */
    private void clearData() {
        assetTypeTextField.clear();
        assetNumberTextField.clear();
        floorNumberTextField.clear();
        roomNumberTextField.clear();
    }

    @FXML
    /**
    * deletes an asset based on the number inputted to the textfield
   * @author David Marji.
   */
    void deleteClicked(ActionEvent event) {
        String assetNumber = assetNumberTextField.getText();
        ObservableList<TOAsset> assetList = FXCollections.observableArrayList(TOAssetController.getAssets());
        for (TOAsset asset : assetList) {
            int a = asset.getAssetNumber();


            int b = Integer.parseInt(assetNumber);
            if (a == b) {
                AssetPlusFeatureSet3Controller.deleteSpecificAsset(Integer.parseInt(assetNumber));
                ViewUtils.makePopupWindow("Delete An Asset", "Asset with " + assetNumber + " deleted successfully");
                showAllAssets();
                AssetPlusFxmlView.getInstance().refresh();
                return;
            }
        }
        ViewUtils.showError("asset does not exist");
    }

    @FXML
    /**
    * updates a chosen asset's values to the values in the textfield (if they are proper)
   * @author David Marji.
   */
    void updateClicked(ActionEvent event) {
        String newAssetTypeName = assetTypeTextField.getText();
        int newAssetRoomNumber;
        if (roomNumberTextField.getText().equals("")) {
            newAssetRoomNumber = -1;
        } else {
            newAssetRoomNumber = Integer.parseInt(roomNumberTextField.getText());
        }

        int newAssetFloorNumber = Integer.parseInt(floorNumberTextField.getText());

        Date newPurchaseDate = java.sql.Date.valueOf(purchaseDateField.getValue());

        int oldAssetNumber = Integer.parseInt(assetNumberTextField.getText());

        String err = AssetPlusFeatureSet3Controller.updateSpecificAsset(oldAssetNumber, newAssetFloorNumber, newAssetRoomNumber, newPurchaseDate, newAssetTypeName);

        if (!err.isEmpty()) {
            ViewUtils.showError(err);
            showAllAssets();
            AssetPlusFxmlView.getInstance().refresh();
        } else {
            ViewUtils.makePopupWindow("Update An Asset", "Asset " + oldAssetNumber + " updated successfully");
        }

        showAllAssets();
        AssetPlusFxmlView.getInstance().refresh();
    }


}
