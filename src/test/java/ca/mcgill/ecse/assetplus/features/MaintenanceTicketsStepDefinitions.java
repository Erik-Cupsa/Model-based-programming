package ca.mcgill.ecse.assetplus.features;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.gherkin.Main;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;


/**
   * Gherkin step definition method to create and add employees to the AssetPlus application.
   *
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the email, password, name and phoneNumber of the employees that must exist in the system. 
   */
public class MaintenanceTicketsStepDefinitions {
  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private List<TOMaintenanceTicket> tickets;

  private String error;

  @Given("the following employees exist in the system")
  public void the_following_employees_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
      assetPlus.addEmployee(row.get("email"), row.get("name"), row.get("password"),
          row.get("phoneNumber"));      
    }
  }


  /**
   * Gherkin step definition method to create and add a manager to the AssetPlus application.
   * 
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the email and password of the manager that must exist in the system. 
   */
  @Given("the following manager exists in the system")
  public void the_following_manager_exists_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    Manager manager = new Manager(rows.get(0).get("email"), null, rows.get(0).get("password"), null, assetPlus);
    assetPlus.setManager(manager);
  }


  /**
   * Gherkin step definition method to create and add asset types to the AssetPlus application.
   *
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the name and expectedLifespan of the asset types that must exist in the system. 
   */
  @Given("the following asset types exist in the system")
  public void the_following_asset_types_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
      assetPlus.addAssetType(row.get("name"), Integer.parseInt(row.get("expectedLifeSpan")));
    }
  }
  /**
   * Gherkin step definition method to create and add assets to the AssetPlus application.
   * 
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the assetNumber, type, purchaseDate, floorNumber and roomNumber of the assets that must exist in the system
   */
  @Given("the following assets exist in the system")
  public void the_following_assets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      int assetNumber = Integer.parseInt(row.get("assetNumber"));
      Date purchaseDate = Date.valueOf(row.get("purchaseDate"));
      int floorNumber = Integer.parseInt(row.get("floorNumber"));
      int roomNumber = Integer.parseInt(row.get("roomNumber"));
      AssetType type = AssetType.getWithName(row.get("type"));
      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, type);
    }
  }

  /**
   * Gherkin step definition method to create and add tickets to the AssetPlus application.
   * 
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the id, ticketRaiser, addedOnDate and description of the tickets that must exist in the system.
   */
  @Given("the following tickets exist in the system")
  public void the_following_tickets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      int id = Integer.parseInt(row.get("id"));
      Date raisedOnDate = Date.valueOf(row.get("raisedOnDate"));
      String description = row.get("description");
      String ticketRaiserEmail = row.get("ticketRaiser");
      String assetNumberStr = row.get("assetNumber");
      MaintenanceTicket newTicket = assetPlus.addMaintenanceTicket(id, raisedOnDate, description,
          User.getWithEmail(ticketRaiserEmail));
      if (assetNumberStr != null) {
        int assetNumber = Integer.parseInt(assetNumberStr);
        newTicket.setAsset(SpecificAsset.getWithAssetNumber(assetNumber));
      }
    }
  }

  /**
   * Gherkin step definition method to create and add ticket notes to the AssetPlus application.
   * 
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the noteTaker, ticketId, addedOnDate and description of the notes that must exist in the system.
   */
  @Given("the following notes exist in the system")
  public void the_following_notes_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String noteTaker = row.get("noteTaker");
      int ticketID = Integer.parseInt(row.get("ticketId"));
      Date addedOnDate = Date.valueOf(row.get("addedOnDate"));
      String description = row.get("description");
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
      ticket.addTicketNote(addedOnDate, description,
          (HotelStaff) HotelStaff.getWithEmail(noteTaker));
    }
  }
  /**
   * Gherkin step definition method to create and add ticket images to the AssetPlus application.
   * 
   * @author Philippe Aprahamian
   * @author Mohamed Abdelrahman
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the imageUrl and ticketId of the ticket images that must exist in the system.
   */
  @Given("the following ticket images exist in the system")
  public void the_following_ticket_images_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String imageURL = row.get("imageUrl");
      int ticketID = Integer.parseInt(row.get("ticketId"));
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
      ticket.addTicketImage(imageURL);
    }
  }
  /**
   * Gherkin step definition method mark a specified ticket to a specified state and set its requiresApproval value.
   *
   * @author Philippe Aprahamian
   * @param string represents the ticket ID
   * @param string2 represents the state the ticket should be marked to
   * @param string3 represents the boolean value of requiresApproval
   */
  @Given("ticket {string} is marked as {string} with requires approval {string}")
  public void ticket_is_marked_as_with_requires_approval(String string, String string2,
                                                         String string3) {
    int ticketID = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if (string3.equals("true")){
      ticket.assign(null, null,null,true);
    }else {
      ticket.assign(null, null,null,false);
    }
    ticket.startWork();
  }
  /**
   * Gherkin step definition method mark a specified ticket to a specified state.
   *
   * @author Philippe Aprahamian
   * @param string represents the ticket ID
   * @param string2 represents the state the ticket should be marked to
   */
  @Given("ticket {string} is marked as {string}")
  public void ticket_is_marked_as(String string, String string2) {
    int ticketID = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if (string2.equals("Open")){
      return;
    }else if (string2.equals("Assigned")||string2.equals("InProgress")||string2.equals("Resolved")||string2.equals("Closed")){
      ticket.assign(null, null,null,false);
    }
    if (string2.equals("InProgress")||string2.equals("Resolved")||string2.equals("Closed")) {
      ticket.startWork();
    }
    if (string2.equals("Resolved")||string2.equals("Closed")){
      ticket.completeFix();
    }
    if (string2.equals("Closed")){
      ticket.acceptFix();
    }
  }

   /**
   * Gherkin step definition method to test controller 6 by getting tickets that exist in the system.
   * 
   * @author Ming Xuan Yue
   */
  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system() {
    tickets = AssetPlusFeatureSet6Controller.getTickets();
  }

  /**
   * Gherkin step definition method to test state machine.
   *
   * @author Anders Woodruff
   */
  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String string, String string2, String string3, String string4, String string5) {
    int id = Integer.parseInt(string);
    String employeeEmail = string2;
    MaintenanceTicket.TimeEstimate timeEstimate = MaintenanceTicket.TimeEstimate.valueOf(string3);
    MaintenanceTicket.PriorityLevel priority = MaintenanceTicket.PriorityLevel.valueOf(string4);
    boolean requiresApproval = Boolean.parseBoolean(string5);
    MaintenanceTicketsStepDefinitions.assignStaffToTicket(id, employeeEmail, timeEstimate, priority,
            requiresApproval);
  }

  /**
   * Gherkin step definition method to test state machine.
   *
   * @author Anders Woodruff
   */
  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String string) {
    int id = Integer.parseInt(string);
    MaintenanceTicketWorkController.startWorkOnTicket(id);
  }

  /**
   * Gherkin step definition method to test state machine.
   *
   * @author Anders Woodruff
   */
  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String string) {
    int id = Integer.parseInt(string);
    MaintenanceTicketWorkController.approveWorkOnTicket(id);
  }

  /**
   * Gherkin step definition method to test state machine.
   *
   * @author Anders Woodruff
   */
  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String string) {
    int id = Integer.parseInt(string);
    MaintenanceTicketWorkController.completeWorkOnTicket(id);
  }

  /**
   * Gherkin step definition method to test state machine.
   *
   * @author Anders Woodruff
   */
  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(String string,
      String string2, String string3) {
    int id = Integer.parseInt(string);
    Date date = Date.valueOf(string2);
    MaintenanceTicketWorkController.disapproveWorkOnTicket(id, date, string3);
  }

  /**
   * Verifying that a given ticket is marked with the correct status
   * @param string The id of the ticket to be checked
   * @param string2 The status of the ticket to be checked
   * @author Erik Cupsa (@Erik-Cupsa)
   */
  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String string, String string2) {
    int ticketID = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    assertEquals(string2, ticket.getStatus());
  }

  /**
   * Verifying that the error being thrown by the system is correct
   * @param string The error in question to check
   * @author Erik Cupsa (@Erik-Cupsa)
   */
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    assertEquals(error, string);
  }

  /**
   * Verifies that for a specified ticket, the ticket shall not exist in the system
   * @param string The ticket ID of the ticket to check in the system
   * @author Erik Cupsa (@Erik-Cupsa)
   */
  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String string) {
    int ticketID = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID); //getting maintenance ticket with given Id
    assertNull(ticket); //verifying ticket is null
  }
  /**
   * Gherkin step definition method checks that, for a specified ticket, estimated time, priority, requires approval are set to the corresponding input strings' values.
   *
   * @author Philippe Aprahamian
   * @param string represents the ticket ID.
   * @param string2 represents the estimated time the ticket should be set to.
   * @param string3 represents the priority level the ticket should be set to.
   * @param string4 represents the boolean value of requiresApproval the ticket should be set to.
   */
  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(String string,
      String string2, String string3, String string4) {
    int ticketID = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    assertEquals(ticket.getTimeToResolve().name(),string2);
    assertEquals(ticket.getPriority().name(),string3);
    if (string4.equals("true")){
      assertTrue(ticket.hasFixApprover());
    }else{
      assertFalse(ticket.hasFixApprover());
    }
  }
  /**
   * Gherkin step definition method checks that, for a specified ticket, it is assigned to the correct ticketFixer.
   *
   * @author Philippe Aprahamian
   * @param string represents the ticket ID.
   * @param string2 represents the email of the expected ticket fixer.
   */
  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String string, String string2) {
    int ticketID = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    assertEquals(ticket.getTicketFixer().getEmail(),string2);
  }

  /**
   * Verifying that the number of tickets in the system is correct
   * @param string The number of tickets
   * @author Erik Cupsa (@Erik-Cupsa)
   */
  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String string) {
    assertEquals(Integer.parseInt(string), assetPlus.getMaintenanceTickets().size());
  }

   /**
   * Gherkin step definition method to ensure the information of the tickets obtained by the controller method 6 is the same as the information in the datatable.
   * @author Ming Xuan Yue
   * @param dataTable Cucumber DataTable containing the id, ticketRaiser, raisedOnDate, description, assetName, expectedLifeSpan, purchaseDate, floorNumber and roomNumber, status, fixedByEmail, timeToResolve, priority and approvalRequired of the tickets shown.
   * 
   */
  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    int i = 0;
    for (var row : rows) {
      TOMaintenanceTicket currTicket = tickets.get(i);
      int id = Integer.parseInt(row.get("id"));
      assertEquals(id, currTicket.getId());
      String ticketRaiserEmail = row.get("ticketRaiser");
      assertEquals(ticketRaiserEmail, currTicket.getRaisedByEmail());
      Date raisedOnDate = Date.valueOf(row.get("raisedOnDate"));
      assertEquals(raisedOnDate, currTicket.getRaisedOnDate());
      String description = row.get("description");
      assertEquals(description, currTicket.getDescription());
      String assetName = row.get("assetName");
      assertEquals(assetName, currTicket.getAssetName());
      String expectLifeSpanStr = row.get("expectLifeSpan");
      int expectLifeSpan = -1;
      if (expectLifeSpanStr != null) {
        expectLifeSpan = Integer.parseInt(expectLifeSpanStr);
      }
      assertEquals(expectLifeSpan, currTicket.getExpectLifeSpanInDays());
      String purchaseDateStr = row.get("purchaseDate");
      Date purchaseDate = null;
      if (purchaseDateStr != null) {
          purchaseDate = Date.valueOf(purchaseDateStr);
      }
      assertEquals(purchaseDate, currTicket.getPurchaseDate());
      String floorNumberStr = row.get("floorNumber");
      int floorNumber = -1;
      if (floorNumberStr != null) {
        floorNumber = Integer.parseInt(floorNumberStr);
      }
      assertEquals(floorNumber, currTicket.getFloorNumber());
      String roomNumberStr = row.get("roomNumber");
      int roomNumber = -1;
      if (roomNumberStr != null) {
        roomNumber = Integer.parseInt(roomNumberStr);
      }
      assertEquals(roomNumber, currTicket.getRoomNumber());
      
      String aStatus = row.get("status");
      assertEquals(aStatus, currTicket.getStatus());

      String fixedByEmail = row.get("fixedByEmail");
      assertEquals(fixedByEmail, currTicket.getFixedByEmail());

      String aTimeToResolve = row.get("timeToResolve");
      assertEquals(aTimeToResolve, currTicket.getTimeToResolve());

      String priority = row.get("priority");
      assertEquals(priority, currTicket.getPriority());

      String approvalRequiredStr = row.get("approvalRequired");
      boolean approvalRequired = Boolean.valueOf(approvalRequiredStr);
      assertEquals(approvalRequired, currTicket.getApprovalRequired());
      i++;
    }        
  }

  /**
   * Gherkin step definition method to ensure that information of the notes of the ticket obtained by the controller method 6 is the same as the information in the datatable.
   * 
   * @author David Marji
   * @author Erik Cupsa
   * @author Philippe Aprahamian
   * @author Mohamed Abdelrahman
   * @author Ming Xuan Yue
   * @param string the ticketId of a specific ticket in the system.
   * @param dataTable Cucumber DataTable containing the noteTaker, addedOnDate and description of the notes of the ticket with the provided ticketId.
   */
  @Then("the ticket with id {string} shall have the following notes")
  public void the_ticket_with_id_shall_have_the_following_notes(String string,
      io.cucumber.datatable.DataTable dataTable) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    for (var ticket : tickets) {
      if (ticket.getId() == ticketID) {
        currTicket = ticket;
      }
    }
    
    assertNotNull(currTicket);

    List<TOMaintenanceNote> currTicketNotes = currTicket.getNotes();
    List<Map<String, String>> rows = dataTable.asMaps();
    int i = 0;
    for (var row : rows) {
      TOMaintenanceNote currNote = currTicketNotes.get(i);
      String noteTaker = row.get("noteTaker");
      assertEquals(noteTaker, currNote.getNoteTakerEmail());
      Date addedOnDate = Date.valueOf(row.get("addedOnDate"));
      assertEquals(addedOnDate, currNote.getDate());
      String description = row.get("description");
      assertEquals(description, currNote.getDescription());
      i++;
    }
  }


  /**
   * Gherkin step definition method to ensure that the ticket obtained by controller 6 has no notes in the system.
   * 
   * @author Erik Cupsa
   * @author Philippe Aprahamian
   * @author Ming Xuan Yue
   * @param string the ticketId of a specific ticket in the system.
   */
  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String string) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    for (var ticket : tickets) {
      if (ticket.getId() == ticketID) {
        currTicket = ticket;
      }
    }

    assertNotNull(currTicket);
    assertEquals(currTicket.hasNotes(), false);
  }

  /**
   * Gherkin step definition method to ensure that information of the images of the ticket obtained by the controller method 6 is the same as the information in the datatable.
   * 
   * @author Erik Cupsa
   * @author Philippe Aprahamian
   * @param string the ticketId of a specific ticket in the system.
   * @param dataTable Cucumber DataTable containing the imageUrl of the ticket images of the ticket with the provided ticketId.
   */
  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String string,
      io.cucumber.datatable.DataTable dataTable) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    for (var ticket : tickets) {
        if (ticket.getId() == ticketID) {
          currTicket = ticket;
        }
      }
      assertNotNull(currTicket);
        
        List<String> currTicketImageURLs = currTicket.getImageURLs();
        List<Map<String, String>> rows = dataTable.asMaps();
        int i = 0;
        for (var row : rows) {
          assertEquals(currTicketImageURLs.get(i), row.get("imageUrl"));
          i++;
        }
  }
    /**
   * Gherkin step definition method to ensure that the ticket obtained by controller 6 has no images in the system.
   * 
   * @author Ming Xuan Yue
   * @author Erik Cupsa
   * @author Philippe Aprahamian
   * @param string the ticketId of a specific ticket in the system.
   */
  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String string) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    for (var ticket : tickets) {
      if (ticket.getId() == ticketID) {
        currTicket = ticket;
      }
    }

    assertNotNull(currTicket);
    assertEquals(currTicket.getImageURLs().size(), 0);
  }
}
