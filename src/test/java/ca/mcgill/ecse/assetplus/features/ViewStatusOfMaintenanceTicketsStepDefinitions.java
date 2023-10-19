package ca.mcgill.ecse.assetplus.features;

import java.util.*;
import ca.mcgill.ecse.assetplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import java.sql.Date;

public class ViewStatusOfMaintenanceTicketsStepDefinitions {
  @Given("the following employees exist in the system \\(p15)")
  public void the_following_employees_exist_in_the_system_p15(
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

  @Given("the following manager exists in the system \\(p15)")
  public void the_following_manager_exists_in_the_system_p15(
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

  @Given("the following asset types exist in the system \\(p15)")
  public void the_following_asset_types_exist_in_the_system_p15(
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

  @Given("the following assets exist in the system \\(p15)")
  public void the_following_assets_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows){
      int assetNumber = Integer.parseInt(row.get("assetNumber"));
      AssetType type = row.get("type");
      Date purchasedDate = Date.valueOf(row.get("purchasedDate"));
      int floorNumber = Integer.parseInt(row.get("floorNumber"));
      int roomNumber = Integer.parseInt(row.get("roomNumber"));
      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchasedDate, type);
    }
    throw new io.cucumber.java.PendingException();
  }

  @Given("the following tickets exist in the system \\(p15)")
  public void the_following_tickets_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
      AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
      List<Map<String, String>> rows = dataTable.asMaps();
      for (var row : rows){
        int id = Integer.parseInt(row.get("id"));
        User ticketRaiser = User.getWithEmail(row.get("ticketRaiser"));
        Date raisedOnDate = Date.valueOf(row.get("raisedOnDate"));
        String description = row.get("description");
        int assetNumber = Integer.parseInt(row.get("assetNumber"));
        MaintenanceTicket newTicket = assetPlus.addMaintenanceTicket(id, raisedOnDate, description, ticketRaiser);
        newTicket.setAsset(assetPlus.getSpecificAsset(assetNumber));
      }
    throw new io.cucumber.java.PendingException();
  }

  @Given("the following notes exist in the system \\(p15)")
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
  public void the_following_ticket_images_exist_in_the_system_p15(
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

  @When("the manager attempts to view all maintenance tickets in the system \\(p15)")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system_p15() {
    // Write code here that turns the phrase above into concrete actions
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
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have no notes \\(p15)")
  public void the_ticket_with_id_shall_have_no_notes_p15(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
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
  public void the_ticket_with_id_shall_have_no_images_p15(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
