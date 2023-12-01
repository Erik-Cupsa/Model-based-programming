package ca.mcgill.ecse.assetplus.features;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddAndUpdateMaintenanceTicketStepDefinitions {

    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    private String error;

    /**
     * @param dataTable The data table of the employees that must exist in the system.
     * @author Tiffany Miller
     */
    @Given("the following employees exist in the system \\(p16)")
    public void the_following_employees_exist_in_the_system_p16(
            io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            String email = row.get(0);
            String password = row.get(1);
            String name = row.get(2);
            String phoneNumber = row.get(3);
            assetPlus.addEmployee(email, name, password, phoneNumber);
        }
        error = "";

    }

    /**
     * @param dataTable The data table of the manager that must exist in the system.
     * @author Tiffany Miller
     */
    @Given("the following manager exists in the system \\(p16)")
    public void the_following_manager_exists_in_the_system_p16(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);
            String email = row.get("email");
            String password = row.get("password");
            Manager manager = new Manager(email, "manager", password, "(123)456-7890", assetPlus);
            assetPlus.setManager(manager);
        }
        error = "";

    }

    /**
     * @param dataTable The data table of the guests that must exist in the system.
     * @author Tiffany Miller
     */
    @Given("the following guests exist in the system \\(p16)")
    public void the_following_guests_exist_in_the_system_p16(
            io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            String email = row.get(0);
            String password = row.get(1);
            String name = row.get(2);
            String phoneNumber = row.get(3);
            assetPlus.addGuest(email, name, password, phoneNumber);
        }
        error = "";

    }

    /**
     * @param dataTable The data table of the asset types that must exist in the system.
     * @author Tiffany Miller
     */
    @Given("the following asset types exist in the system \\(p16)")
    public void the_following_asset_types_exist_in_the_system_p16(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);
            String name = row.get("name");
            int lifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
            assetPlus.addAssetType(name, lifeSpan);
        }
        error = "";

    }

    /**
     * @param dataTable The data table of the assets that must exist in the system.
     * @author Tiffany Miller
     */
    @Given("the following assets exist in the system \\(p16)")
    public void the_following_assets_exist_in_the_system_p16(
            io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            int assetNumber = Integer.parseInt(row.get(0));
            AssetType type = AssetType.getWithName(row.get(1));
            Date purchaseDate = Date.valueOf(row.get(2));
            int floorNumber = Integer.parseInt(row.get(3));
            int roomNumber = Integer.parseInt(row.get(4));
            assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, type);
        }
        error = "";
    }

    /**
     * @param dataTable The data table of the tickets that must exist in the system.
     * @author Tiffany Miller
     */
    @Given("the following tickets exist in the system \\(p16)")
    public void the_following_tickets_exist_in_the_system_p16(
            io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            int id = Integer.parseInt(row.get(0));
            String ticketRaiser = row.get(1);
            Date raisedOnDate = Date.valueOf(row.get(2));
            String description = row.get(3);
            int assetNumber = Integer.parseInt(row.get(4));
            User raiserUser = User.getWithEmail(ticketRaiser);
            SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
            assetPlus.addMaintenanceTicket(id, raisedOnDate, description, raiserUser);
            MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
            thisTicket.setAsset(asset);
        }
        error = "";
    }

    /**
     * @param email             The email of the user that wants to add a maintenance ticket.
     * @param idString          The new maintenance ticket id.
     * @param dateString        The new maintenance ticket date
     * @param description       The new maintenance ticket description.
     * @param assetNumberString The new maintenance ticket's asset number.
     * @author Tiffany Miller
     */
    @When("the user with email {string} attempts to add a new maintenance ticket to the system with id {string}, date {string}, description {string}, and asset number {string} \\(p16)")
    public void the_user_with_email_attempts_to_add_a_new_maintenance_ticket_to_the_system_with_id_date_description_and_asset_number_p16(
            String email, String idString, String dateString, String description,
            String assetNumberString) {

        int id = Integer.parseInt(idString);
        Date date = Date.valueOf(dateString);
        int assetNumber = Integer.parseInt(assetNumberString);

        callController(AssetPlusFeatureSet4Controller.addMaintenanceTicket(id, date, description, email,
                assetNumber));

    }

    /**
     * @param email       The email of the user that wants to add a maintenance ticket.
     * @param idString    The new maintenance ticket id.
     * @param dateString  The new maintenance ticket date
     * @param description The new maintenance ticket description.
     * @author Tiffany Miller
     */
    @When("the user with email {string} attempts to add a new maintenance ticket to the system with id {string}, date {string}, and description {string} but no asset number \\(p16)")
    public void the_user_with_email_attempts_to_add_a_new_maintenance_ticket_to_the_system_with_id_date_and_description_but_no_asset_number_p16(
            String email, String idString, String dateString, String description) {

        int id = Integer.parseInt(idString);
        Date date = Date.valueOf(dateString);
        int assetNumber = -1;

        callController(AssetPlusFeatureSet4Controller.addMaintenanceTicket(id, date, description, email,
                assetNumber));

    }

    /**
     * @param idString    The id of the maintenance ticket the manager wants to update.
     * @param email       Updated maintenance ticket raiser.
     * @param dateString  Updated maintenance ticket date.
     * @param description Updated maintenance ticket asset description.
     * @author Tiffany Miller
     */
    @When("the manager attempts to update the maintenance ticket with id {string} to ticket raiser {string}, date {string}, and description {string} but no asset number \\(p16)")
    public void the_manager_attempts_to_update_the_maintenance_ticket_with_id_to_ticket_raiser_date_and_description_but_no_asset_number_p16(
            String idString, String email, String dateString, String description) {

        int id = Integer.parseInt(idString);
        Date date = Date.valueOf(dateString);
        int assetNumber = -1;

        callController(AssetPlusFeatureSet4Controller.updateMaintenanceTicket(id, date, description,
                email, assetNumber));

    }

    /**
     * @param idString          The id of the maintenance ticket the manager wants to update.
     * @param email             Updated maintenance ticket raiser.
     * @param dateString        Updated maintenance ticket date.
     * @param description       Updated maintenance ticket description.
     * @param assetNumberString Updated maintenance ticket asset number.
     * @author Tiffany Miller
     */
    @When("the manager attempts to update the maintenance ticket with id {string} to ticket raiser {string}, date {string}, description {string}, and asset number {string} \\(p16)")
    public void the_manager_attempts_to_update_the_maintenance_ticket_with_id_to_ticket_raiser_date_description_and_asset_number_p16(
            String idString, String email, String dateString, String description,
            String assetNumberString) {

        int id = Integer.parseInt(idString);
        Date date = Date.valueOf(dateString);
        int assetNumber = Integer.parseInt(assetNumberString);

        callController(AssetPlusFeatureSet4Controller.updateMaintenanceTicket(id, date, description,
                email, assetNumber));
    }

    /**
     * @param expectedNumTicketsString The number of maintenance tickets that is expected in the
     *                                 system.
     * @author Tiffany Miller
     */
    @Then("the number of tickets in the system shall be {string} \\(p16)")
    public void the_number_of_tickets_in_the_system_shall_be_p16(String expectedNumTicketsString) {
        int numberOfTickets = assetPlus.getMaintenanceTickets().size();
        int expectedNumTickets = Integer.parseInt(expectedNumTicketsString);
        assertEquals(expectedNumTickets, numberOfTickets);

    }

    /**
     * @param dataTable Data table of maintenance tickets that is expected in the system.
     * @author Tiffany Miller
     */
    @Then("the following tickets shall exist in the system \\(p16)")
    public void the_following_tickets_shall_exist_in_the_system_p16(
            io.cucumber.datatable.DataTable dataTable) {

        Map<String, List<String>> expectedTicketsMap = new HashMap<>();
        Map<String, List<String>> actualTicketsMap = new HashMap<>();

        List<List<String>> rows = dataTable.asLists(String.class);

        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            String id = row.get(0);
            expectedTicketsMap.put(id, row);
        }

        List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();

        for (var ticket : maintenanceTickets) {
            List<String> oneTicket = new ArrayList<>();
            String id = String.valueOf(ticket.getId());
            String ticketRaiser = ticket.getTicketRaiser().getEmail();
            String raisedOnDate = ticket.getRaisedOnDate().toString();
            String description = ticket.getDescription();
            String assetNumber = String.valueOf(ticket.getAsset().getAssetNumber());

            oneTicket.add(id);
            oneTicket.add(ticketRaiser);
            oneTicket.add(raisedOnDate);
            oneTicket.add(description);
            oneTicket.add(assetNumber);

            actualTicketsMap.put(id, oneTicket);
        }

        assertEquals(expectedTicketsMap.size(), actualTicketsMap.size());
        assertEquals(expectedTicketsMap, actualTicketsMap);
    }

    /**
     * @param email       The email of the person who raised the ticket.
     * @param idString    The id of the new maintenance ticket raised.
     * @param dateString  The date of the new maintenance ticket raised.
     * @param description The description of the new maintenance ticket raised.
     * @author Tiffany Miller
     */
    @Then("the ticket raised by {string} and with id {string}, date {string}, and description {string} but no asset shall exist in the system \\(p16)")
    public void the_ticket_raised_by_and_with_id_date_and_description_but_no_asset_shall_exist_in_the_system_p16(
            String email, String idString, String dateString, String description) {

        User ticketRaiser = User.getWithEmail(email);
        int id = Integer.valueOf(idString);
        Date date = Date.valueOf(dateString);

        MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
        assertEquals(ticketRaiser, thisTicket.getTicketRaiser());
        assertEquals(date, thisTicket.getRaisedOnDate());
        assertEquals(description, thisTicket.getDescription());
        assertTrue(!thisTicket.hasAsset());

    }

    /**
     * @param email             The email of the person who raised the ticket.
     * @param idString          The id of the new maintenance ticket raised.
     * @param dateString        The date of the new maintenance ticket raised.
     * @param description       The description of the new maintenance ticket raised.
     * @param assetNumberString The asset number associated to the new maintenance ticket raised.
     * @author Tiffany Miller
     */
    @Then("the ticket raised by {string} and with id {string}, date {string}, description {string}, and asset number {string} shall exist in the system \\(p16)")
    public void the_ticket_raised_by_and_with_id_date_description_and_asset_number_shall_exist_in_the_system_p16(
            String email, String idString, String dateString, String description,
            String assetNumberString) {

        User ticketRaiser = User.getWithEmail(email);
        int id = Integer.valueOf(idString);
        Date date = Date.valueOf(dateString);
        int assetNumber = Integer.valueOf(assetNumberString);
        SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);

        MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
        assertEquals(ticketRaiser, thisTicket.getTicketRaiser());
        assertEquals(date, thisTicket.getRaisedOnDate());
        assertEquals(description, thisTicket.getDescription());
        assertEquals(asset, thisTicket.getAsset());
    }

    /**
     * @param string The error string that should be raised.
     * @author Tiffany Miller
     */
    @Then("the system shall raise the error {string} \\(p16)")
    public void the_system_shall_raise_the_error_p16(String string) {
        assertEquals(string, error);
    }

    /**
     * @param result The error result after calling controller method.
     * @author Tiffany Miller
     */
    private void callController(String result) {
        if (!result.isEmpty()) {
            error += result;
        }
    }
}
