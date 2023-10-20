package ca.mcgill.ecse.assetplus.features;
import static org.junit.Assert.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
import java.util.*;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ca.mcgill.ecse.assetplus.controller.*;
//import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
//import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
//import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
//import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
//import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
//import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
//import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
//import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;

import java.sql.Date;

public class ViewStatusOfMaintenanceTicketsStepDefinitions {
  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();	
  private List<TOMaintenanceTicket> tickets;
  @Given("the following employees exist in the system \\(p15)")
  //@author("Mohamed Abdelrahman && Anders Woodruff && Philippe Aprahamian && David Marji && Ming Yue && Manuel Hanna")
  public void the_following_employees_exist_in_the_system_p15(
    io.cucumber.datatable.DataTable dataTable) {
	    List<Map<String,String>> rows= dataTable.asMaps(String.class,String.class);
      for (Map<String,String> row : rows) {
    	 assetPlus.addEmployee(row.get("email"), row.get("name"), row.get("password"), row.get("phoneNumber"));
      }
  }

  @Given("the following manager exists in the system \\(p15)")
  //@author("Mannuel Hanna && Philippe Aprahamian && Ming Xuan Yue")
  public void the_following_manager_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
      List<Map<String,String>> rows = dataTable.asMaps(String.class, String.class);
      Manager manager = new Manager(rows.get(0).get("email"),  null,rows.get(0).get("password"), null, assetPlus); 
      assetPlus.setManager(manager);
  }

  @Given("the following asset types exist in the system \\(p15)")
  //@author("Anders Woodruff && Philippe Aprahamian && Ming Xuan Yue")
  public void the_following_asset_types_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
      List<Map<String,String>> rows= dataTable.asMaps(String.class,String.class);
      for (Map<String,String> row : rows) {
    	assetPlus.addAssetType(row.get("name"), Integer.parseInt(row.get("expectedLifeSpan")));
      }
  }

  @Given("the following assets exist in the system \\(p15)")
  //@author("Ming Xuan Yue && Philippe Aprahamian" && David Marji)
  public void the_following_assets_exist_in_the_system_p15(
	      io.cucumber.datatable.DataTable dataTable) {
	    
	    List<Map<String, String>> rows = dataTable.asMaps();
	    for (var row : rows){
	      int assetNumber = Integer.parseInt(row.get("assetNumber"));
	      Date purchaseDate = Date.valueOf(row.get("purchaseDate"));
	      int floorNumber = Integer.parseInt(row.get("floorNumber"));
	      int roomNumber = Integer.parseInt(row.get("roomNumber"));
	      AssetType type = AssetType.getWithName(row.get("type"));
	      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, type);

	    }
	    
	  }

  @Given("the following tickets exist in the system \\(p15)")
  //@author("Ming Xuan Yue && Philippe Aprahamian")
  public void the_following_tickets_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String,String>> rows = dataTable.asMaps();
	  for (var row: rows) {
		  int id = Integer.parseInt(row.get("id"));
		  Date raisedOnDate = Date.valueOf(row.get("raisedOnDate"));
		  String description = row.get("description");
		  String ticketRaiserEmail = row.get("ticketRaiser");
		  String assetNumberStr = row.get("assetNumber");
		  MaintenanceTicket newTicket = assetPlus.addMaintenanceTicket(id, raisedOnDate, description, User.getWithEmail(ticketRaiserEmail));
		  if (assetNumberStr!=null) {
			  int assetNumber = Integer.parseInt(assetNumberStr);
			  newTicket.setAsset(SpecificAsset.getWithAssetNumber(assetNumber));
		  }
		  
	  }
  }

  @Given("the following notes exist in the system \\(p15)")
  //@author("Philippe Aprahamian" && Ming Xuan Yue)
  public void the_following_notes_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String,String>> rows = dataTable.asMaps();
	  for (var row: rows) {
		  String noteTaker = row.get("noteTaker");
		  int ticketID= Integer.parseInt(row.get("ticketId"));
		  Date addedOnDate = Date.valueOf(row.get("addedOnDate"));
		  String description = row.get("description");
		  MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
		  ticket.addTicketNote(addedOnDate, description, (HotelStaff) HotelStaff.getWithEmail(noteTaker));
	  } 
	  
  }

  @Given("the following ticket images exist in the system \\(p15)")
//@author("Mohamed Abdelrahman && Philippe Aprahamian")
  public void the_following_ticket_images_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String,String>> rows = dataTable.asMaps();
	  for (var row: rows) {
		  String imageURL = row.get("imageUrl");
		  int ticketID= Integer.parseInt(row.get("ticketId"));
		  MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
		  ticket.addTicketImage(imageURL);
		  } 
  }
  @When("the manager attempts to view all maintenance tickets in the system \\(p15)")
//@author("David Marji, Manuel Hanna, Mohamed Abdelrahman")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system_p15() {
	 tickets=AssetPlusFeatureSet6Controller.getTickets();
	 for (TOMaintenanceTicket t: tickets){
		 System.out.println(t);
	 }
  }

  @Then("the following maintenance tickets shall be presented \\(p15)")
//@author("David Marji, Philippe Aprahamian, Mohamed Abdelrahman,Ming Xuan Yue")
  public void the_following_maintenance_tickets_shall_be_presented_p15(
      io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String,String>> rows = dataTable.asMaps();
	  int i =0;
	  for (var row: rows) {
		  TOMaintenanceTicket currTicket = tickets.get(i);
		  int id= Integer.parseInt(row.get("id"));
		  assertEquals(id,currTicket.getId());
		  String ticketRaiserEmail = row.get("ticketRaiser");
		  assertEquals(ticketRaiserEmail,currTicket.getRaisedByEmail());
		  Date raisedOnDate = Date.valueOf(row.get("raisedOnDate"));
		  assertEquals(raisedOnDate,currTicket.getRaisedOnDate());
		  String description = row.get("description");
		  assertEquals(description,currTicket.getDescription());
		  String assetName = row.get("assetName");
		  assertEquals(assetName,currTicket.getAssetName());
			String expectLifeSpanStr = row.get("expectLifeSpan");
			int expectLifeSpan=-1;
			if (expectLifeSpanStr !=null){
				expectLifeSpan = Integer.parseInt(expectLifeSpanStr);
			}
		  assertEquals(expectLifeSpan,currTicket.getExpectLifeSpanInDays());
			String purchaseDateStr = row.get("purchaseDate");
			Date purchaseDate=null;
			if (purchaseDateStr !=null){
				purchaseDate = Date.valueOf(purchaseDateStr);
			}
		  assertEquals(purchaseDate,currTicket.getPurchaseDate());
		  String floorNumberStr = row.get("floorNumber");
			int floorNumber=-1;
			if (floorNumberStr !=null){
				floorNumber = Integer.parseInt(floorNumberStr);
			}
		  assertEquals(floorNumber,currTicket.getFloorNumber());
			String roomNumberStr = row.get("roomNumber");
			int roomNumber=-1;
			if (roomNumberStr !=null){
				roomNumber = Integer.parseInt(roomNumberStr);
			}
		  assertEquals(roomNumber,currTicket.getRoomNumber());
		  i++;
		  } 
  }

  @Then("the ticket with id {string} shall have the following notes \\(p15)")
//@author("Erik Cupsa, David Marji, Philippe Aprahamian, Mohamed Abdelrahman,Ming Xuan Yue")
  public void the_ticket_with_id_shall_have_the_following_notes_p15(String string,
	      io.cucumber.datatable.DataTable dataTable) {
	  	int ticketID=Integer.parseInt(string) ;
	  	TOMaintenanceTicket currTicket = null; 
	  	for (var ticket:tickets) {
	  		if (ticket.getId()==ticketID) {
	  			currTicket = ticket;
	  		}
	  	}
	  	List<TOMaintenanceNote> currTicketNotes= currTicket.getNotes(); 
	  	List<Map<String,String>> rows = dataTable.asMaps();
	  	int i=0;
	  	for (var row: rows) {
	  		  TOMaintenanceNote currNote=currTicketNotes.get(i);
	  		  String noteTaker = row.get("noteTaker");
	  		  assertEquals(noteTaker,currNote.getNoteTakerEmail());
			  Date addedOnDate = Date.valueOf(row.get("addedOnDate"));
			  assertEquals(addedOnDate,currNote.getDate());
			  String description = row.get("description");
			  assertEquals(description,currNote.getDescription());
			  i++;
			  } 
	  }

  @Then("the ticket with id {string} shall have no notes \\(p15)")
  //@author("Erik Cupsa && Philippe Aprahamian")
  public void the_ticket_with_id_shall_have_no_notes_p15(String string) {
	  	int ticketID=Integer.parseInt(string) ;
	  	TOMaintenanceTicket currTicket = null; 
	  	for (var ticket:tickets) {
	  		if (ticket.getId()==ticketID) {
	  			currTicket = ticket;
	  		}
	  	}
	  	assertEquals(currTicket.hasNotes(),false);
	  }

  @Then("the ticket with id {string} shall have the following images \\(p15)")
  //@author("Erik Cupsa && Philippe Aprahamian")
  public void the_ticket_with_id_shall_have_the_following_images_p15(String string,
	      io.cucumber.datatable.DataTable dataTable) {
	  	int ticketID=Integer.parseInt(string) ;
	  	TOMaintenanceTicket currTicket = null; 
	  	for (var ticket:tickets) {
	  		if (ticket.getId()==ticketID) {
	  			currTicket = ticket;
	  		}
	  	}
	  	List<String> currTicketImageURLs= currTicket.getImageURLs();
	  	List<Map<String,String>> rows = dataTable.asMaps();
	  	int i=0;
	  	for (var row: rows) {
	  		  assertEquals(currTicketImageURLs.get(i),row.get("imageUrl"));
			  i++;
			  } 
	  }

	  

  @Then("the ticket with id {string} shall have no images \\(p15)")
  //@author("Erik Cupsa && Philippe Aprahamian")
  public void the_ticket_with_id_shall_have_no_images_p15(String string) {
	    int ticketID=Integer.parseInt(string) ;
	  	TOMaintenanceTicket currTicket = null; 
	  	for (var ticket:tickets) {
	  		if (ticket.getId()==ticketID) {
	  			currTicket = ticket;
	  		}
	  	}
	  	assertEquals(currTicket.getImageURLs().size(),0);
	  }
	  
}
