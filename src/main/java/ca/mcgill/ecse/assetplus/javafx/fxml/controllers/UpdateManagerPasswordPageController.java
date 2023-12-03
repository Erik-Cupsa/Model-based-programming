package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateManagerPasswordPageController {

    @FXML
    private Label labelWorkStatus;

    @FXML
    private TextField managerPasswordTextField;

    @FXML
    private TextField managerPasswordConfirm;

    @FXML
    private Button updatePasswordButton;

    /**
     * update the password of the manager
     * 
     * @param an event click on update button
     * @author Ming Xuan Yue
     */

    @FXML
    void updatePasswordClicked(ActionEvent event) {
        String password = managerPasswordTextField.getText();
        if (!password.equals(managerPasswordConfirm.getText())) {
            ViewUtils.makePopupWindow(("Update Manager Password"), "Passwords must match");
            return;
        }
        String err = AssetPlusFeatureSet1Controller.updateManager(password);

        if (!err.isEmpty()) {
            ViewUtils.showError(err);
        } else {
            ViewUtils.makePopupWindow(("Update Manager Password"), "Manager password updated succesfully");
        }
    }

}

