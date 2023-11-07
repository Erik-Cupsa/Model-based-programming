package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for the DeleteTicketImages feature checks that existing ticket images are no longer
 * available in the system once deleted
 * 
 * @author Team P13
 */

public class DeleteTicketImageStepDefinitions {
  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  // Step to create employees in the system
  @Given("the following employees exist in the system \\(p13)")
  public void the_following_employees_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String name = row.get("name");
      String password = row.get("password");
      String phoneNumber = row.get("phoneNumber");
      assetPlus.addEmployee(email, name, password, phoneNumber);
    }
  }

  // Step to create a manager in the system
  @Given("the following manager exists in the system \\(p13)")
  public void the_following_manager_exists_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      if (assetPlus.hasManager()) {
        assetPlus.getManager().setPassword(password);
      } else {
        new Manager(email, "", password, "", assetPlus);
      }
    }
  }

  // Step to create asset types in the system
  @Given("the following asset types exist in the system \\(p13)")
  public void the_following_asset_types_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int expectedLifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
      assetPlus.addAssetType(name, expectedLifeSpan);
    }
  }

  // Step to create assets in the system
  @Given("the following assets exist in the system \\(p13)")
  public void the_following_assets_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      int assetNumber = Integer.parseInt(row.get("assetNumber"));
      AssetType assetType = AssetType.getWithName(row.get("type"));
      Date purchaseDate = Date.valueOf(row.get("purchaseDate"));
      int floorNumber = Integer.parseInt(row.get("floorNumber"));
      int roomNumber = Integer.parseInt(row.get("roomNumber"));
      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType);
    }
  }

  // Step to create maintenance tickets in the system
  @Given("the following tickets exist in the system \\(p13)")
  public void the_following_tickets_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      int id = Integer.parseInt(row.get("id"));
      User ticketRaiser = User.getWithEmail(row.get("ticketRaiser"));
      Date raisedOnDate = Date.valueOf(row.get("raisedOnDate"));
      String description = row.get("description");
      int assetNumber = Integer.parseInt(row.get("assetNumber"));
      MaintenanceTicket maintenanceTicket =
          new MaintenanceTicket(id, raisedOnDate, description, assetPlus, ticketRaiser);
      maintenanceTicket.setAsset(SpecificAsset.getWithAssetNumber(assetNumber));
    }
  }

  // Step to create ticket images in the system
  @Given("the following ticket images exist in the system \\(p13)")
  public void the_following_ticket_images_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String imageUrl = row.get("imageUrl");
      int ticketId = Integer.parseInt(row.get("ticketId"));
      new TicketImage(imageUrl, MaintenanceTicket.getWithId(ticketId));
    }
  }

  // Step for a manager to delete a ticket image
  @When("the manager deletes the ticket image {string} from the ticket with id {string} \\(p13)")
  public void the_manager_deletes_the_ticket_image_from_the_ticket_with_id_p13(String imageURL,
      String ticketID) {
    int ticketIdInt = Integer.parseInt(ticketID);
    AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(imageURL, ticketIdInt);
  }

  // Step to check the number of images in the system is correct
  @Then("the number of images in the system shall be {string} \\(p13)")
  public void the_number_of_images_in_the_system_shall_be_p13(String numberOfTicketImages) {
    List<MaintenanceTicket> maintenanceTicketList = assetPlus.getMaintenanceTickets();
    int count = 0;
    for (var ticket : maintenanceTicketList) {
      count += ticket.numberOfTicketImages();
    }
    assertEquals(Integer.parseInt(numberOfTicketImages), count);
  }

  // Step to check the number of images for the ticket with deleted image is correct
  @Then("the number of images for the ticket with id {string} in the system shall be {string} \\(p13)")
  public void the_number_of_images_for_the_ticket_with_id_in_the_system_shall_be_p13(
      String ticketId, String numberOfImagesOfTicket) {
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));
    assertEquals(Integer.parseInt(numberOfImagesOfTicket),
        maintenanceTicket.numberOfTicketImages());
  }

  // Step to check that a specific ticket does not have a deleted image
  @Then("the ticket with id {string} shall not have an image with url {string} \\(p13)")
  public void the_ticket_with_id_shall_not_have_an_image_with_url_p13(String ticketId,
      String imageUrl) {
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId));
    List<TicketImage> images = maintenanceTicket.getTicketImages();
    boolean hasImage = false;
    for (var ticketImage : images) {
      if (ticketImage.getImageURL().equals(imageUrl)) {
        hasImage = true;
      }
    }
    assertEquals(false, hasImage);
  }
}
