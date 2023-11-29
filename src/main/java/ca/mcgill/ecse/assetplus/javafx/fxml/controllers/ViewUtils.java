package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.model.AssetType;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {

    /** Calls the controller and shows an error, if applicable. */
    public static boolean callController(String result) {
        if (result.isEmpty()) {
            AssetPlusFxmlView.getInstance().refresh();
            return true;
        }
        showError(result);
        return false;
    }

    /** Calls the controller and returns true on success. This method is included for readability. */
    public static boolean successful(String controllerResult) {
        return callController(controllerResult);
    }

    /**
     * Creates a popup window.
     *
     * @param title: title of the popup window
     * @param message: message to display
     */
    public static void makePopupWindow(String title, String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogPane = new VBox();

        // create UI elements
        Text text = new Text(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(a -> dialog.close());

        // display the popup window
        int innerPadding = 10; // inner padding/spacing
        int outerPadding = 100; // outer padding
        dialogPane.setSpacing(innerPadding);
        dialogPane.setAlignment(Pos.CENTER);
        dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
        dialogPane.getChildren().addAll(text, okButton);
        Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
        dialog.setScene(dialogScene);
        dialog.setTitle(title);
        dialog.show();
    }

    public static void showError(String message) {
        makePopupWindow("Error", message);
    }

    public static ObservableList<TOMaintenanceTicket> getTickets() {
        List<TOMaintenanceTicket> tickets = AssetPlusFeatureSet6Controller.getTickets();
        // as javafx works with observable list, we need to convert the java.util.List to
        // javafx.collections.observableList
        return FXCollections.observableList(tickets);
    }

    public static ObservableList<AssetType> getAssetTypes() {
        //List<AssetType> assetTypes = AssetPlusFeatureSet2Controller.getAssetTypes();
        return FXCollections.observableList(new ArrayList<AssetType>());
    }

    public static void openAddTicketPage() {
    }

    public static void loadTicketsIntoTableView(
            TableView<TOMaintenanceTicket> ticketsTableView,
            TableColumn<TOMaintenanceTicket, Integer> numberColumn,
            TableColumn<TOMaintenanceTicket, String> issuerColumn,
            TableColumn<TOMaintenanceTicket, String> statusColumn,
            TableColumn<TOMaintenanceTicket, String> dateRaisedColumn
            ) {
        numberColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        issuerColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getRaisedByEmail()));
        statusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStatus()));
        dateRaisedColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getRaisedOnDate().toString()));

        System.out.println("update count");
        // load tickets into TableView.
        ObservableList<TOMaintenanceTicket> tickets = ViewUtils.getTickets();
        for (TOMaintenanceTicket ticket : tickets) {
            ticketsTableView.getItems().add(ticket);
        }
    }
}