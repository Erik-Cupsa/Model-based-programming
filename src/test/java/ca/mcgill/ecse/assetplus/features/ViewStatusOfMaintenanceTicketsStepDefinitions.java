package ca.mcgill.ecse.assetplus.features;

import java.util.*;
import ca.mcgill.ecse.assetplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class ViewStatusOfMaintenanceTicketsStepDefinitions {
  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();	

  @Given("the following employees exist in the system \\(p15)")
  @author("Mohamed Abdelrahman && Anders Woodruff && Philippe Aprahamian && David Marji && Ming Yue && Manuel Hanna")
  public void the_following_employees_exist_in_the_system_p15(
    io.cucumber.datatable.DataTable dataTable) {
	    assetPlus=AssetPlusApplication.getAssetPlus();
      List<Map<String,String,String,String>> rows= dataTable.asMaps(String.class,String.class,String.class,String.class);
      List<Employee> employees = new List<Employee>();
      for (Map<String,String,String,String> column : rows) {
        employees.add(new Employee(column.get("email"), column.get("name"), column.get("password"), column.get("phoneNumber"), assetPlus));
      }
  }

  @Given("the following manager exists in the system \\(p15)")
  @author("Mannuel Hanna")
  public void the_following_manager_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
      List<Map<String,String>> rows = dataTable.asMaps(String.class, String.class);
      for (Map<String,String>columns: rows) {
        Manager manager = new Manager(columns.get("email"), columns.get("password"), null, null, assetPlus); 
      }
  }

  @Given("the following asset types exist in the system \\(p15)")
  @author("Anders Woodruff")
  public void the_following_asset_types_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
      List<Map<String,String> rows= dataTable.asMaps(String.class,String.class);
      for (Map<String,String> column : rows) {
        AssetType a = new AssetType(column.get("name"), column.get("expectedLifeSpan"), this.assetPlus);
        // Anders I think we have to type cast to int
      }
  }

  @Given("the following assets exist in the system \\(p15)")
  @author("Ming Yue")
  public void the_following_assets_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Given("the following tickets exist in the system \\(p15)")
  @author("Ming Yue")
  public void the_following_tickets_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Given("the following notes exist in the system \\(p15)")
  @author("Philippe Aprahamian")
  public void the_following_notes_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Given("the following ticket images exist in the system \\(p15)")
  @author("Mohamed Abdelrahman")
  public void the_following_ticket_images_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
        List<Map<String,String>> rows= dataTable.asMaps(String.class,String.class);
        for (Map<String,String> column : rows) {
          TicketImage t = new TicketImage(column.get("imageUrl"), column.get("ticketId"), assetPlus);
  }
  
  @When("the manager attempts to view all maintenance tickets in the system \\(p15)")
  @author("David Marji, Manuel Hanna, Mohamed Abdelrahman")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system_p15() {
    AssetPlusFeatureSet6Controller.getMaintenanceTickets();
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following maintenance tickets shall be presented \\(p15)")
  public void the_following_maintenance_tickets_shall_be_presented_p15(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have the following notes \\(p15)")
  public void the_ticket_with_id_shall_have_the_following_notes_p15(String string,
      io.cucumber.datatable.DataTable dataTable) {
      List<MaintenanceTicket> ticketList = assetPlus.getMaintenanceTickets();
      MaintenanceTicket ticketOfInterest;

      for (MaintenanceTicket ticket : ticketList){
        if (ticket.id == Integer.parseInt(string)){
          ticketOfInterest = ticket;
        }
      }
      List<MaintenanceNote> notesList = ticket.getTicketNotes();
      List<Map<String,String,String>> rows = dataTable.asMaps(String.class,String.class, String.class);
      for (int i = 0; i > rows.length; i++) {
        assertEquals(notesList[i].noteTaker, rows[i].get("noteTaker"));
        assertEquals(notesList[i].date.toString(), rows[i].get("addedOnDate"));
        assertEquals(notesList[i].description, rows[i].get("description"));

      }
  }

  @Then("the ticket with id {string} shall have no notes \\(p15)")
  @author("Erik Cupsa")
  public void the_ticket_with_id_shall_have_no_notes_p15(String string) {
    int ticketIdInt = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketIdInt);

    assertNotNull("Ticket should not be null", ticket);
    List<MaintenanceNote> notesList = ticket.getTicketNotes();
    assertTrue("Notes list should be empty", notesList.isEmpty());
  }

  @Then("the ticket with id {string} shall have the following images \\(p15)")
  public void the_ticket_with_id_shall_have_the_following_images_p15(String string,
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have no images \\(p15)")
  @author("Erik Cupsa")
  public void the_ticket_with_id_shall_have_no_images_p15(String string) {
    int ticketIdInt = Integer.parseInt(string);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketIdInt);

    assertNotNull("Ticket should not be null", ticket);
    List<TicketImage> imagesList = ticket.getTicketImages();
    assertTrue("Images list should be empty", imagesList.isEmpty());
  }
}
