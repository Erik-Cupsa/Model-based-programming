package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.lang.ModuleLayer.Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.TOAsset;
import ca.mcgill.ecse.assetplus.controller.TOAssetController;
import ca.mcgill.ecse.assetplus.controller.TOAssetTypeController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


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
    void addAssetTypeClicked(ActionEvent event) {
        String name = assetTypeNameTextField.getText();
        String expectedLifeSpan = assetTypeExpectedLifeSpanTextField.getText();
        
        String err = AssetPlusFeatureSet2Controller.addAssetType(name, Integer.parseInt(expectedLifeSpan));
        if(err.isEmpty()){
            clearData();
            showAllAssetTypes();
        }
        else{
            ViewUtils.showError(err);
        }
    }

    @FXML
    void deleteAssetTypeClicked(ActionEvent event) {
        TOAssetType selected = assetTypeTable.getSelectionModel().getSelectedItem();
        AssetPlusFeatureSet2Controller.deleteAssetType(selected.getName());
        showAllAssetTypes();
        AddUpdateDeleteAssetController.getInstance().showAllAssets();
        ViewStatusPageController.getInstance().showAllTickets();
    }

    @FXML
    void updateAssetTypeClicked(ActionEvent event) {
        int newExpectedLifeSpan = Integer.parseInt(assetTypeExpectedLifeSpanTextField.getText());
        String newName = assetTypeNameTextField.getText();

        String oldName = assetTypeTable.getSelectionModel().getSelectedItem().getName();

        String err = AssetPlusFeatureSet2Controller.updateAssetType(assetTypeTable.getSelectionModel().getSelectedItem().getName(), newName, newExpectedLifeSpan);

        if(!err.isEmpty()){
            ViewUtils.makePopupWindow("Update An Asset Type" , "AssetType with name " + oldName + " updated unsuccessfully");
        }

        showAllAssetTypes();
        AssetPlusFxmlView.getInstance().refresh();
    }

    @FXML
    void clearClicked(ActionEvent event) {
        clearData();
    }

}
