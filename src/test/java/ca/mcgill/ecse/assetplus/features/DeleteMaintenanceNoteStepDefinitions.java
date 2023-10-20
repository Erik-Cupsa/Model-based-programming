package ca.mcgill.ecse.assetplus.features;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for the DeleteMaintenanceNote feature Checks that an existing maintenance note
 * no longer exists in the system when deleted by a manager
 *
 * @author Team P2
 */

public class DeleteMaintenanceNoteStepDefinitions {

  AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  public static MaintenanceNote deletedNote;

  /**
   * Loads the input dataTable, instantiates and adds the given employees
   *
   * @param dataTable
   */
  @Given("the following employees exist in the system \\(p2)")
  public void the_following_employees_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {
    // Turns the dataTable into a list of lists (each row becomes a list)
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified employees and add them to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      String email = (row.get("email")).toString();
      String password = (row.get("password")).toString();
      String name = (row.get("name")).toString();
      String phoneNumber = (row.get("phoneNumber")).toString();

      // Instantiate and add the specified employees to the AssetPlus application
      new Employee(email, password, name, phoneNumber, assetPlus);


    }
  }

  /**
   * Loads the input dataTable and updates the manager
   *
   * @param dataTable
   */
  @Given("the following manager exists in the system \\(p2)")
  public void the_following_manager_exists_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {

    // Turns the dataTable into a list of lists (each row becomes a list).
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified employees and add them to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      String email = (row.get("email")).toString(); // Not used since the email shouldn't change
      String password = (row.get("password")).toString();

      // Instantiate and add the manager to the AssetPlus application
      new Manager(email, password, "", "", assetPlus);


    }
  }

  /**
   * Loads the input dataTable, instantiates and adds the given asset types
   *
   * @param dataTable
   */
  @Given("the following asset types exist in the system \\(p2)")
  public void the_following_asset_types_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {

    // Turns the dataTable into a list of lists (each row becomes a list).
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified asset types and add them to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      String name = (row.get("name")).toString();
      int expectedLifeSpan = Integer.parseInt((row.get("expectedLifeSpan")).toString());

      // Instantiate and add the specified asset types to the AssetPlus application
      new AssetType(name, expectedLifeSpan, assetPlus);


    }
  }

  /**
   * Loads the input dataTable, instantiates and adds the given assets
   *
   * @param dataTable
   */
  @Given("the following assets exist in the system \\(p2)")
  public void the_following_assets_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {

    // Turns the dataTable into a list of lists (each row becomes a list).
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified asset and add them to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      int assetNumber = Integer.parseInt(row.get("assetNumber").toString());
      String assetTypeName = (row.get("type")).toString();
      int floorNumber = Integer.parseInt(row.get("floorNumber").toString());
      int roomNumber = Integer.parseInt(row.get("roomNumber").toString());
      Date purchaseDate = Date.valueOf(row.get("purchaseDate").toString());

      // Instantiate and add the specified assets to the AssetPlus application.
      AssetType assetType = AssetType.getWithName(assetTypeName);
      new SpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate,
          assetPlus, assetType);


    }
  }

  /**
   * Loads the input dataTable, instantiates and adds the given maintenance tickets
   *
   * @param dataTable
   */
  @Given("the following tickets exist in the system \\(p2)")
  public void the_following_tickets_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {

    // Turns the dataTable into a list of lists (each row becomes a list).
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified tickets and add them to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      int ticketID = Integer.parseInt(row.get("id").toString());
      String ticketRaiser = (row.get("ticketRaiser").toString());
      Date raisedOnDate = Date.valueOf(row.get("raisedOnDate").toString());
      String description = (row.get("description").toString());
      int assetNumber = Integer.parseInt(row.get("assetNumber").toString());

      // Instantiate and add the specified maintenance tickets to the AssetPlus application.

      User aUser = User.getWithEmail(ticketRaiser);
      SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
      MaintenanceTicket ticket =
          new MaintenanceTicket(ticketID, raisedOnDate, description, assetPlus, aUser);
      ticket.setAsset(asset);



    }
  }

  /**
   * Loads the input dataTable, instantiates and adds the given maintenance ticket notes
   *
   * @param dataTable
   */
  @Given("the following notes exist in the system \\(p2)")
  public void the_following_notes_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {

    // Turns the dataTable into a list of lists (each row becomes a list).
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified tickets and add them to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      String noteTaker = (row.get("noteTaker").toString());
      int ticketID = Integer.parseInt(row.get("ticketId").toString());
      Date addedOnDate = Date.valueOf(row.get("addedOnDate").toString());
      String description = (row.get("description").toString());

      // Instantiate and add the specified maintenance ticket notes to the appropriate maintenance
      // ticket.
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
      HotelStaff staff = (HotelStaff) HotelStaff.getWithEmail(noteTaker); // check this
      MaintenanceNote note = new MaintenanceNote(addedOnDate, description, ticket, staff);
      ticket.addTicketNote(note);

    }
  }

  /**
   * Deletes the specified maintenance note
   *
   * @param noteNumberInput
   * @param ticketNumberInput
   */
  @When("the manger attempts to delete note number {string} for maintenance ticket {string} \\(p2)")
  public void the_manger_attempts_to_delete_note_number_for_maintenance_ticket_p2(
      String noteNumberInput, String ticketNumberInput) {
    int noteNumber = Integer.parseInt(noteNumberInput);
    int ticketNumber = Integer.parseInt(ticketNumberInput);

    if (ticketNumber < assetPlus.getMaintenanceTickets().size()) {
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketNumber);
      if (noteNumber < ticket.getTicketNotes().size()) {
        deletedNote = MaintenanceTicket.getWithId(ticketNumber).getTicketNote(noteNumber);
      }
    }
    AssetPlusFeatureSet7Controller.deleteMaintenanceNote(ticketNumber, noteNumber);

  }

  /**
   * Checks that the number of maintenance notes in the AssetPlus application has updated
   *
   * @param expectedNumberInput
   */
  @Then("the number of notes in the system shall be {string} \\(p2)")
  public void the_number_of_notes_in_the_system_shall_be_p2(String expectedNumberInput) {
    int expectedNumber = Integer.parseInt(expectedNumberInput);
    int numberOfMaintenanceNotes = 0;

    for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()) {
      numberOfMaintenanceNotes += ticket.numberOfTicketNotes();
    }

    assertEquals(expectedNumber, numberOfMaintenanceNotes);
  }

  /**
   * Checks that the number of maintenance notes for the specified maintenance ticket has updated
   *
   * @param ticketNumberInput
   * @param expectedNumberInput
   */
  @Then("the number of notes for ticket {string} in the system shall be {string} \\(p2)")
  public void the_number_of_notes_for_ticket_in_the_system_shall_be_p2(String ticketNumberInput,
      String expectedNumberInput) {
    int ticketNumber = Integer.parseInt(ticketNumberInput);
    int expectedNumber = Integer.parseInt(expectedNumberInput);

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketNumber);

    assertEquals(expectedNumber, ticket.numberOfTicketNotes());

  }

  /**
   * Checks that the specified maintenance note has been deleted from the AssetPllus application
   *
   * @param noteNumberInput
   * @param ticketNumberInput
   */
  @Then("the note number {string} for ticket {int} shall not exist in the system \\(p2)")
  public void the_note_number_for_ticket_shall_not_exist_in_the_system_p2(String noteNumberInput,
      Integer ticketNumberInput) {

    int noteNumber = Integer.parseInt(noteNumberInput);
    int ticketNumber = ticketNumberInput;

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketNumber);

    if (noteNumber < ticket.getTicketNotes().size()) {
      assertFalse(deletedNote.equals(ticket.getTicketNote(noteNumber)));
    }
  }
}
