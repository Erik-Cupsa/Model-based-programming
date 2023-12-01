package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetController;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import ca.mcgill.ecse.assetplus.controller.TOUserController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.controller.TOAsset;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import java.sql.Date;

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

    public AddUpdateDeleteAssetController(){
        instance = this;
    }

    public static AddUpdateDeleteAssetController getInstance(){
        return instance;
    }


    @FXML
    public void initialize(){
        roomNumberColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getRoomNumber())));
        floorNumberColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getFloorNumber())));
        purchaseDateColumn.setCellValueFactory(data -> Bindings.createStringBinding(() ->  data.getValue().getPurchaseDate().toString()));
        assetNumberColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getAssetNumber())));
        assetTypeColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getTypeName()));

        showAllAssets();
    }

    public void showAllAssets(){
        ObservableList<TOAsset> assetList = FXCollections.observableArrayList(TOAssetController.getAssets());
        assetTable.setItems(assetList);
        assetTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> assetTable.setItems(assetList));
    }

    @FXML
    void addClicked(ActionEvent event) {
        String typeName = assetTypeTextField.getText();
        int roomNumber = Integer.parseInt(roomNumberTextField.getText());
        int floorNumber = Integer.parseInt(floorNumberTextField.getText());
        int assetNumber = Integer.parseInt(assetNumberTextField.getText());

        Date purchaseDate = java.sql.Date.valueOf(purchaseDateField.getValue());
        
        String err = AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, typeName);
        if(err.isEmpty()){
            ViewUtils.makePopupWindow("Add An Asset" , "Asset " + assetNumber + " updated successfully");
            showAllAssets();
            AssetPlusFxmlView.getInstance().refresh();
        }
        else{
            ViewUtils.showError(err);
            showAllAssets();
            AssetPlusFxmlView.getInstance().refresh();
        }
    }

    @FXML
    void clearButtonClicked(ActionEvent event) {
        clearData();
    }

    private void clearData(){
        assetTypeTextField.clear();
        assetNumberTextField.clear();
        floorNumberTextField.clear();
        roomNumberTextField.clear();
    }

    @FXML
    void deleteClicked(ActionEvent event) {
        String assetNumber = assetNumberTextField.getText();
        ObservableList<TOAsset> assetList = FXCollections.observableArrayList(TOAssetController.getAssets());
        for (TOAsset asset : assetList ){
            if (asset.getAssetNumber() == Integer.parseInt(assetNumber)){
                AssetPlusFeatureSet3Controller.deleteSpecificAsset(Integer.parseInt(assetNumber));;
                ViewUtils.makePopupWindow("Delete An Asset" , "Asset with " + assetNumber + " deleted successfully");
                showAllAssets();
                AssetPlusFxmlView.getInstance().refresh();
            }else{
                ViewUtils.showError("asset does not exist");
            }
        }    
    }

    @FXML
    void updateClicked(ActionEvent event) {
        String newAssetTypeName = assetTypeTextField.getText();
        int newAssetRoomNumber = Integer.parseInt(roomNumberTextField.getText());
        int newAssetFloorNumber = Integer.parseInt(floorNumberTextField.getText());

        Date newPurchaseDate = java.sql.Date.valueOf(purchaseDateField.getValue());

        int oldAssetNumber = Integer.parseInt(assetNumberTextField.getText());

        String err = AssetPlusFeatureSet3Controller.updateSpecificAsset(oldAssetNumber, newAssetFloorNumber, newAssetRoomNumber, newPurchaseDate, newAssetTypeName);

        if(!err.isEmpty()){
            ViewUtils.showError(err);
            showAllAssets();
            AssetPlusFxmlView.getInstance().refresh();
        }
        ViewUtils.makePopupWindow("Update An Asset" , "Asset " + oldAssetNumber + " updated successfully");
        showAllAssets();
        AssetPlusFxmlView.getInstance().refresh();
    }

}