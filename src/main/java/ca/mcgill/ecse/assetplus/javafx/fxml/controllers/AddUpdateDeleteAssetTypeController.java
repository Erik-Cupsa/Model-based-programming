package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOAssetTypeController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


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
    /**
    * initializes the asset type by setting the type of values the table should display and then setting them
   * @author David Marji.
   */
    public void initialize() {
        expectedLifeSpanColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> Integer.toString(data.getValue().getExpectedLifeSpan())));
        assetTypeNameColumn.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getName()));
        showAllAssetTypes();
    }

    /**
    * a method that sets all the rows of a table with the values of asset types stored
   * @author David Marji.
   */
    private void showAllAssetTypes() {
        ObservableList<TOAssetType> assetTypeList = FXCollections.observableArrayList(TOAssetTypeController.getAssetTypes());
        assetTypeTable.setItems(assetTypeList);
        assetTypeTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> assetTypeTable.setItems(assetTypeList));
    }

    /**
    * a method that clears all the data from the textfields
   * @author David Marji.
   */
    private void clearData() {
        assetTypeExpectedLifeSpanTextField.clear();
        assetTypeNameTextField.clear();
    }


    @FXML
    /**
    * add's the chosen asset type to the list of asset types stored
    *  @param an event click on the add button
   * @author David Marji.
   */
    void addAssetTypeClicked(ActionEvent event) {
        String name = assetTypeNameTextField.getText();
        String expectedLifeSpan = assetTypeExpectedLifeSpanTextField.getText();

        String err = AssetPlusFeatureSet2Controller.addAssetType(name, Integer.parseInt(expectedLifeSpan));
        if (err.isEmpty()) {
            clearData();
            showAllAssetTypes();
        } else {
            ViewUtils.showError(err);
        }
    }

    @FXML
    /**
    * deletes the chosen (clicked on) asset type from the table
    *  @param an event click on the delete button
   * @author David Marji.
   */
    void deleteAssetTypeClicked(ActionEvent event) {
        TOAssetType selected = assetTypeTable.getSelectionModel().getSelectedItem();
        AssetPlusFeatureSet2Controller.deleteAssetType(selected.getName());
        showAllAssetTypes();
        AddUpdateDeleteAssetController.getInstance().showAllAssets();
        ViewStatusPageController.getInstance().showAllTickets();
    }

    @FXML
    /**
    * updates the chosen (clicked on) asset type from the table with the information from the textfields if it is proper
    *  @param an event click on the update button
   * @author David Marji.
   */
    void updateAssetTypeClicked(ActionEvent event) {
        int newExpectedLifeSpan = Integer.parseInt(assetTypeExpectedLifeSpanTextField.getText());
        String newName = assetTypeNameTextField.getText();

        String err = AssetPlusFeatureSet2Controller.updateAssetType(assetTypeTable.getSelectionModel().getSelectedItem().getName(), newName, newExpectedLifeSpan);

        if (!err.isEmpty()) {
            ViewUtils.showError(err);
        }

        showAllAssetTypes();
        AssetPlusFxmlView.getInstance().refresh();
    }

    @FXML
    void clearClicked(ActionEvent event) {
        clearData();
    }

}
