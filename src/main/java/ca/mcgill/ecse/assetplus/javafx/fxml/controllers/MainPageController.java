package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class MainPageController {
    @FXML
    private TabPane mainTabPane;

    private int selectedTabIndex = 0;

    public void setSelectedTabIndex(int newTabIndex) {
        selectedTabIndex = newTabIndex;
    }

    public void initialize() {
        mainTabPane.getSelectionModel().select(selectedTabIndex);
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                // Check if the selected tab is the one you want to update
                if (newTab == mainTabPane.getTabs().get(4)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../pages/AddUpdateDeleteAsset.fxml"));
                    try {
                        Parent root = loader.load();
                        AddUpdateDeleteAssetController addUpdateDeleteAssetController = loader.getController();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                // You can add similar conditions for other tabs if needed
            }
        });
    }

}
