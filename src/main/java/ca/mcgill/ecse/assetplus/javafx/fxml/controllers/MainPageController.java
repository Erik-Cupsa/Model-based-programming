package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainPageController {
    @FXML
    private TabPane mainTabPane;

    private int selectedTabIndex = 0;

    public void setSelectedTabIndex(int newTabIndex) {
        selectedTabIndex = newTabIndex;
    }

    public void initialize() {
        mainTabPane.getSelectionModel().select(selectedTabIndex);
    }
}
