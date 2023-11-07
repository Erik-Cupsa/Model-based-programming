package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

public class AddTicketImageStepDefinitions {
  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private String error;

  /** Calls controller and sets error and counter. */
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
    }
  }

  /**
   * Defines the intial employees found in the system
   * 
   * @author Group 5
   * @param dataTable tabular data containing email, password, name, and phone number of employees
   *        in system
   */
  @Given("the following employees exist in the system \\(p5)")
  public void the_following_employees_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> employeeDataList = dataTable.asMaps();
    for (Map<String, String> data : employeeDataList) {
      String email = data.get("email");
      String name = data.get("name");
      String password = data.get("password");
      String phoneNumber = data.get("phoneNumber");
      assetPlus.addEmployee(email, name, password, phoneNumber);
    }
  }

  /**
   * Defines the intial managers found in the system
   * 
   * @author Group 5
   * @param dataTable tabular data containing email and password of managers in system
   */
  @Given("the following manager exists in the system \\(p5)")
  public void the_following_manager_exists_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> managerData = dataTable.asMaps();
    for (Map<String, String> data : managerData) {
      String email = data.get("email");
      String password = data.get("password");
      if (assetPlus.hasManager()) {
        assetPlus.getManager().setPassword(password);
      } else {
        new Manager(email, "", password, "", assetPlus);
      }
    }
  }

  /**
   * Defines the intial asset types found in the system
   * 
   * @author Group 5
   * @param dataTable tabular data containing name and expected lifespan of asset types in system
   */
  @Given("the following asset types exist in the system \\(p5)")
  public void the_following_asset_types_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> assetTypes = dataTable.asMaps();
    for (Map<String, String> data : assetTypes) {
      assetPlus.addAssetType(data.get("name"), Integer.parseInt(data.get("expectedLifeSpan")));
    }
  }

  /**
   * Defines the intial assets found in the system
   * 
   * @author Group 5
   * @param dataTable tabular data containing assetNumber, type, purchase date, floor number, and
   *        room number of assets in system
   */
  @Given("the following assets exist in the system \\(p5)")
  public void the_following_assets_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> assetData = dataTable.asMaps();
    for (Map<String, String> data : assetData) {
      int assetNumber = Integer.parseInt(data.get("assetNumber"));
      AssetType assetType = AssetType.getWithName(data.get("type"));
      Date purchaseDate = Date.valueOf(data.get("purchaseDate"));
      int floorNumber = Integer.parseInt(data.get("floorNumber"));
      int roomNumber = Integer.parseInt(data.get("roomNumber"));
      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType);
    }
  }

  /**
   * Defines the intial tickets found in the system
   * 
   * @author Group 5
   * @param dataTable tabular data containing id, ticket raiser, raise date, raisedOnDate,
   *        description, and asset numberasset of tickets in system
   */
  @Given("the following tickets exist in the system \\(p5)")
  public void the_following_tickets_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> tickets = dataTable.asMaps();
    for (Map<String, String> data : tickets) {
      int id = Integer.parseInt(data.get("id"));
      String ticketRaiserEmail = data.get("ticketRaiser");
      Date raisedOnDate = Date.valueOf(data.get("raisedOnDate"));
      String description = data.get("description");
      int assetNumber = Integer.parseInt(data.get("assetNumber"));
      User ticketRaiser = User.getWithEmail(ticketRaiserEmail);
      SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
      AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
      MaintenanceTicket newMaintenanceTicket =
          new MaintenanceTicket(id, raisedOnDate, description, assetPlus, ticketRaiser);
      newMaintenanceTicket.setAsset(specificAsset);
    }
  }

  /**
   * Defines the intial ticket images found in the system
   * 
   * @author Group 5
   * @param dataTable tabular data containing image Url and ticket Id of ticket images in system
   */
  @Given("the following ticket images exist in the system \\(p5)")
  public void the_following_ticket_images_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> images = dataTable.asMaps();
    for (Map<String, String> data : images) {
      String imageUrl = data.get("imageUrl");
      int ticketId = Integer.parseInt(data.get("ticketId"));
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
      new TicketImage(imageUrl, ticket);
    }
  }

  /**
   * Defines the step at which a hotel staff adds an image to the system
   * 
   * @author Group 5
   * @param imgURL String containing image url
   * @param ticketID String containing ticket Id of ticket images
   */
  @When("hotel staff adds an image with url {string} to the ticket with id {string} \\(p5)")
  public void hotel_staff_adds_an_image_with_url_to_the_ticket_with_id_p5(String imgURL,
      String ticketID) {
    callController(AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(imgURL,
        Integer.parseInt(ticketID)));
  }

  /**
   * Verifies the number of images in the system
   * 
   * @param expectedNum The expected number of images in the system
   */
  @Then("the number of images in the system shall be {string} \\(p5)")
  public void the_number_of_images_in_the_system_shall_be_p5(String expectedNum) {
    List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
    int numOfImages = 0;
    for (MaintenanceTicket aTicket : maintenanceTickets) {
      numOfImages += aTicket.numberOfTicketImages();
    }
    assertEquals(numOfImages, Integer.parseInt(expectedNum));
  }

  /**
   * Verifies what ticket images are in the system
   * 
   * @param dataTable contains data, in table form, of the imageURL and ticketId
   */
  @Then("the following ticket images shall exist in the system \\(p5)")
  public void the_following_ticket_images_shall_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> ticketimages = dataTable.asMaps();
    for (Map<String, String> data : ticketimages) {
      boolean image_found = false;
      String url = data.get("imageUrl");
      String id = data.get("ticketId");
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(id));
      for (TicketImage image : ticket.getTicketImages()) {
        if (url.equals(image.getImageURL())) {
          image_found = true;
        }
      }
      assertTrue(image_found);
    }
  }

  /**
   * Verfies the image url of a particular MaintenanceTicket
   * 
   * @param ticketID ticketID of particular MaintenanceTicket
   * @param expectedLength The expected img url
   */
  @Then("the ticket with id {string} shall have an image with url {string} \\(p5)")
  public void the_ticket_with_id_shall_have_an_image_with_url_p5(String ticketID,
      String expectedLength) {
    String url = expectedLength;
    int id = Integer.parseInt(ticketID);
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(id);
    List<TicketImage> images = ticket.getTicketImages();
    int found = 0;
    for (TicketImage image : images) {
      if (!(image.getImageURL() == null)) {
        if (image.getImageURL().equals(url) && found == 0) {
          found = 1;
        }
      }
    }
    assertTrue(found == 1);
  }

  /**
   * Verifies the number of images contained inside a ticket
   * 
   * @param ticketID ticketID of MaintenanceTicket
   * @param numOfImg The number of images that the particular ticket should have
   */
  @Then("the number of images for ticket with id {string} in the system shall be {string} \\(p5)")
  public void the_number_of_images_for_ticket_with_id_in_the_system_shall_be_p5(String ticketID,
      String numOfImg) {
    List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
    MaintenanceTicket t = getTicketById(maintenanceTickets, ticketID);
    assertEquals(Integer.valueOf(numOfImg), t.numberOfTicketImages());
  }

  /**
   * Finds the MaintenanceTicket that matches the ticketID
   * 
   * @param maintenanceTickets An array containing MaintenanceTicket objects
   * @param ticketString The ticketID in string form
   * @return MaintenanceTicket object, if there exists a ticket that matches the string id
   */
  private MaintenanceTicket getTicketById(List<MaintenanceTicket> maintenanceTickets,
      String ticketString) {
    for (MaintenanceTicket t : maintenanceTickets) {
      if (Integer.toString(t.getId()).equals(ticketString)) {
        return t;
      }
    }
    return null;
  }

  /**
   * Checks if the system outputs the appropriate error message
   * 
   * @param errorString: The error message we are looking for
   */
  @Then("the system shall raise the error {string} \\(p5)")
  public void the_system_shall_raise_the_error_p5(String errorString) {
    assertTrue(error.contains(errorString));
  }
}
