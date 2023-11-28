package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
        List<TOMaintenanceTicket> drivers = AssetPlusFeatureSet6Controller.getTickets();
        // as javafx works with observable list, we need to convert the java.util.List to
        // javafx.collections.observableList
        return FXCollections.observableList(drivers);
    }

    public static void loadTickets(ListView<TOMaintenanceTicket> ticketsListView) {
        List<TOMaintenanceTicket> tickets = new ArrayList<>(); // TODO: remove after testing
        // TODO: Remove test ticket once loading ap.data works
        TOMaintenanceTicket ticket1 = new TOMaintenanceTicket(
                1,
                new Date(2000, 11, 11),
                "Leaky faucet in the kitchen",
                "user@example.com",
                "Open",
                "",
                "2 days",
                "High",
                false,
                "Kitchen Sink",
                365,
                new Date(2000, 11, 11),
                1,
                101,
                Arrays.asList("url1", "url2")
        );
        tickets.add(ticket1);

        ObservableList<TOMaintenanceTicket> observableTickets = FXCollections.observableArrayList(tickets);
        // TODO: uncomment after ap.data loading works
        //ObservableList<TOMaintenanceTicket> observableTickets = getTickets();

        // Load the tickets into the ListView
        ticketsListView.setItems(observableTickets);

        // Set the TicketGridCell as the cell factory for the ListView
        ticketsListView.setCellFactory(param -> new TicketGridCell());
    }
    // Method to handle the "Edit" button action
    public static void editSelectedTicket(ListView<TOMaintenanceTicket> ticketsListView) {
        TOMaintenanceTicket selectedTicket = ticketsListView.getSelectionModel().getSelectedItem();

        if (selectedTicket != null) {
            openEditPage(selectedTicket);
        } else {
            System.out.println("No ticket selected.");
        }
    }

    // Method to open the edit page and pass the selected ticket
    private static void openEditPage(TOMaintenanceTicket selectedTicket) {
        System.out.println("edit button clicked");
        // TODO: Remove this commented part after EditTicketPage.fxml and its controller has been added.
        /*
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTicketPage.fxml"));
            Parent root = loader.load();

            EditTicketPageController editTicketPageController = loader.getController();
            editTicketController.initialize(selectedTicket);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Ticket");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }
}