package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOUserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import javafx.beans.binding.Bindings;
import javafx.scene.input.MouseEvent;

public class AddUpdateDeleteEmployeePageController {

    @FXML
    private Button AddEmployeeButton;

    @FXML
    private Button DeleteEmployeeButton;

    @FXML
    private Button AddGuestButton;

    @FXML
    private TextField employeePasswordTextField;

    @FXML
    private TextField employeePhoneTextField;

    @FXML
    private Button UpdateEmployeeButton;

    @FXML
    private TableColumn<TOUser, String> name;

    @FXML
    private TextField employeeEmailTextField;

    @FXML
    private TextField employeeNameTextField;

    @FXML
    private TableView<TOUser> employeeTable;

    @FXML
    private TableColumn<TOUser, String> email;

    @FXML
    private TableColumn<TOUser, String> phone;

    @FXML
    public void initialize(){
        email.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getEmail()));
        name.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getName()));
        phone.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getPhoneNumber()));
        showAllEmployees();
    }
    
    private void showAllEmployees(){
        ObservableList<TOUser> userList = FXCollections.observableArrayList(TOUserController.getUsers());
        employeeTable.setItems(userList);
        employeeTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> employeeTable.setItems(userList));
    }

    @FXML
    void addGuestClicked(ActionEvent event) {
        String name = employeeNameTextField.getText();
        String email = employeeEmailTextField.getText();
        String phone = employeePhoneTextField.getText();
        String password = employeePasswordTextField.getText();

        String err = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(email, password, name, phone, false);

        if (err.isEmpty()){
            ViewUtils.makePopupWindow("Add New Guest" , "Guest with " + email + " added successfully");
            employeeNameTextField.setText("");
            employeeEmailTextField.setText("");
            employeePhoneTextField.setText("");
            employeePasswordTextField.setText("");
            showAllEmployees();
            AssetPlusFxmlView.getInstance().refresh();
        }else{
            ViewUtils.showError(err);
        }
    }

    @FXML
    public void AddEmployeeClicked(ActionEvent event) {
        String name = employeeNameTextField.getText();
        String email = employeeEmailTextField.getText();
        String phone = employeePhoneTextField.getText();
        String password = employeePasswordTextField.getText();

        String err = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(email, password, name, phone, true);

        if (err.isEmpty()){
            ViewUtils.makePopupWindow("Add New Employee" , "Employee with " + email + " added successfully");
            employeeNameTextField.setText("");
            employeeEmailTextField.setText("");
            employeePhoneTextField.setText("");
            employeePasswordTextField.setText("");
            showAllEmployees();
            AssetPlusFxmlView.getInstance().refresh();
        }else{
            ViewUtils.showError(err);
        }
    }

    @FXML
    public void DeleteEmployeeClicked(ActionEvent event) {
        String email = employeeEmailTextField.getText();
        ObservableList<TOUser> userList = FXCollections.observableArrayList(TOUserController.getUsers());
        for (TOUser user : userList){
            if (user.getEmail() == email){
                AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(email);
                ViewUtils.makePopupWindow("Delete An Employee" , "Employee with " + email + " deleted successfully");
                showAllEmployees();
                AssetPlusFxmlView.getInstance().refresh();
            }else{
                ViewUtils.showError("user does not exist");
            }
        }    
    }

    @FXML
    public void UpdateEmployeeClicked(ActionEvent event) {
        
        String name = employeeNameTextField.getText();
        String email = employeeEmailTextField.getText();
        String phone = employeePhoneTextField.getText();
        String password = employeePasswordTextField.getText();
        
        String err = AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(email, password, name, phone);

        if (!err.isEmpty()){
            ViewUtils.showError(err);
            showAllEmployees();
            AssetPlusFxmlView.getInstance().refresh();
        }else{
            ViewUtils.makePopupWindow("Update An Employee" , "Employee with " + email + " updated successfully");
            showAllEmployees();
            AssetPlusFxmlView.getInstance().refresh();
        }
    }

    @FXML
    void updateView(MouseEvent event) {
        TOUser selected = employeeTable.getSelectionModel().getSelectedItem();

        employeeNameTextField.setText(selected.getName());
        employeeEmailTextField.setText(selected.getEmail());
        employeePhoneTextField.setText(selected.getPhoneNumber());
        employeePasswordTextField.setText(selected.getPassword());
        AssetPlusFxmlView.getInstance().refresh();
    }
}
