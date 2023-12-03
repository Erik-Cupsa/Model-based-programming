package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import ca.mcgill.ecse.assetplus.controller.TOUserController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    /**
     * initializes UI components
     *
     * @author Ming Xuan Yue
     */

    @FXML
    public void initialize() {
        email.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getEmail()));
        name.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getName()));
        phone.setCellValueFactory(data -> Bindings.createStringBinding(() -> data.getValue().getPhoneNumber()));
        showAllUsers();
    }

    /**
     * updates the table showing all users (employees, manager, users)
     *
     * @author Ming Xuan Yue
     */
    private void showAllUsers() {
        ObservableList<TOUser> userList = FXCollections.observableArrayList(TOUserController.getUsers());
        employeeTable.setItems(userList);
        employeeTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> employeeTable.setItems(userList));
    }

   
    /**
     * add new guest in the system with provided name, email, phoneNumber and password
     * 
     * @param an event click on addGuest button
     * @author Ming Xuan Yue
     */

    @FXML
    void addGuestClicked(ActionEvent event) {
        String name = employeeNameTextField.getText();
        String email = employeeEmailTextField.getText();
        String phone = employeePhoneTextField.getText();
        String password = employeePasswordTextField.getText();

        String err = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(email, password, name, phone, false);

        if (err.isEmpty()) {
            ViewUtils.makePopupWindow("Add New Guest", "Guest with " + email + " added successfully");
            employeeNameTextField.setText("");
            employeeEmailTextField.setText("");
            employeePhoneTextField.setText("");
            employeePasswordTextField.setText("");
            showAllUsers();
            AssetPlusFxmlView.getInstance().refresh();
        } else {
            ViewUtils.showError(err);
        }
    }

    /**
     * add new employee in the system with provided name, email, phoneNumber and password
     * 
     * @param an event click on addEmployee button
     * @author Ming Xuan Yue
     */
    @FXML
    public void AddEmployeeClicked(ActionEvent event) {
        String name = employeeNameTextField.getText();
        String email = employeeEmailTextField.getText();
        String phone = employeePhoneTextField.getText();
        String password = employeePasswordTextField.getText();

        String err = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(email, password, name, phone, true);

        if (err.isEmpty()) {
            ViewUtils.makePopupWindow("Add New Employee", "Employee with " + email + " added successfully");
            employeeNameTextField.setText("");
            employeeEmailTextField.setText("");
            employeePhoneTextField.setText("");
            employeePasswordTextField.setText("");
            showAllUsers();
            AssetPlusFxmlView.getInstance().refresh();
        } else {
            ViewUtils.showError(err);
        }
    }

    /**
     * delete existing employee or guest in the system with provided name, email, phoneNumber and password
     * 
     * @param an event click on delete button
     * @author Ming Xuan Yue
     */
    @FXML
    public void DeleteEmployeeClicked(ActionEvent event) {
        String email = employeeEmailTextField.getText();
        ObservableList<TOUser> userList = FXCollections.observableArrayList(TOUserController.getUsers());
        for (TOUser user : userList) {
            if (user.getEmail().equals(email)) {
                AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(email);
                ViewUtils.makePopupWindow("Delete An Employee", "Employee with " + email + " deleted successfully");
                showAllUsers();
                AssetPlusFxmlView.getInstance().refresh();
                ViewStatusPageController.getInstance().showAllTickets();
                return;
            }
        }
        ViewUtils.showError("user does not exist");
    }

    /**
     * update an existing emploee or guest in the system with provided name, email, phoneNumber and password
     * 
     * @param an event click on update button
     * @author Ming Xuan Yue
     */
    @FXML
    public void UpdateEmployeeClicked(ActionEvent event) {

        String name = employeeNameTextField.getText();
        String email = employeeEmailTextField.getText();
        String phone = employeePhoneTextField.getText();
        String password = employeePasswordTextField.getText();

        String err = AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(email, password, name, phone);

        if (!err.isEmpty()) {
            ViewUtils.showError(err);
            showAllUsers();
            AssetPlusFxmlView.getInstance().refresh();
        } else {
            ViewUtils.makePopupWindow("Update An Employee", "Employee with " + email + " updated successfully");
            showAllUsers();
            AssetPlusFxmlView.getInstance().refresh();
        }
    }

    /**
     * when a row in the table is selected, fill the corresponding textField with the appropriate information for the guest or employee
     * 
     * @param an event click on an existing row in the table
     * @author 
     */

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
