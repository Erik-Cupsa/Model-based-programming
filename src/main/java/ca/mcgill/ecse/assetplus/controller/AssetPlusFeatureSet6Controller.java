package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ca.mcgill.ecse.assetplus.model.*;


import java.sql.Array;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
public class AssetPlusFeatureSet6Controller {

  public static void deleteEmployeeOrGuest(String email) {
    // Remove this exception when you implement this method
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    boolean hasEmployee = false;
    boolean hasGuest = false;
   
    try{
      if (email == null){
        throw new IllegalArgumentException("Email cannot be null.");
      }else if (email == ""){
        throw new IllegalArgumentException("Email cannot be empty.");
      }else if (email == "manager@ap.com"){
        throw new IllegalArgumentException("Manager cannot be deleted.");
      }
      if (email.endsWith("@ap.com")){
        List<Employee> employees = assetPlus.getEmployees(); 
        for (Employee e : employees){
          if(e.getEmail().equals(email)){
            Employee employeeToDelete = e;
            employeeToDelete.delete();
            hasEmployee = true;
          }
        }if (!hasEmployee){
          throw new IllegalArgumentException ("Employee does not exist.");
        }
      }else{
        List<Guest> guests = assetPlus.getGuests(); 
        for (Guest g : guests){
          if(g.getEmail().equals(email)){
            Guest guestToDelete = g;
            guestToDelete.delete();
            hasGuest = true;
          }
        }if (!hasGuest){
          throw new IllegalArgumentException ("Guest does not exist.");
        }  
      }
    }catch (Exception e){
      e.printStackTrace();      // trace all errors
    }
  }

  // returns all tickets
  public static List<TOMaintenanceTicket> getTickets() {

  
	  List<TOMaintenanceTicket> tickets = new ArrayList<TOMaintenanceTicket>();
	    
	  AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
	  for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()){
		
	    int id = ticket.getId();
	    String description = ticket.getDescription();
	    String raisedByEmail = ticket.getTicketRaiser().getEmail();
	    String assetname = null;
	    int expectedLifeSpanInDays = 0;
	    int floorNumber = 0;
	    int roomNumber = 0;
	    if (ticket.hasAsset()){
	    	//assertTrue(ticket!=null);
	      assetname = ticket.getAsset().getAssetType().getName();
	      expectedLifeSpanInDays = ticket.getAsset().getAssetType().getExpectedLifeSpan();
	      floorNumber = ticket.getAsset().getFloorNumber();
	      roomNumber = ticket.getAsset().getRoomNumber();
	    }
	  List<TicketImage> ticketImages = ticket.getTicketImages();
	  List<String> imageURLs = new ArrayList<String>();
	  TOMaintenanceNote[] notes = new TOMaintenanceNote[ticket.getTicketImages().size()];

      // hasImages/not
      // hasAsset/not
      // hasMaintenanceNotes/not
      if (!ticketImages.isEmpty()){
        for (TicketImage ticketimage: ticketImages){
          imageURLs.add(ticketimage.getImageURL());
        }
      }

      if (!ticket.getTicketNotes().isEmpty()){
        for (int i = 0; i <ticket.getTicketImages().size(); i++){
          notes[i] = new TOMaintenanceNote(ticket.getTicketNote(i).getDate(), ticket.getTicketNote(i).getDescription(), ticket.getTicketNote(i).getNoteTaker().getEmail());
        }
      } 
      if (ticket.hasAsset()){
    	  tickets.add(new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, assetname, expectedLifeSpanInDays, ticket.getAsset().getPurchaseDate(), floorNumber, roomNumber, imageURLs, notes));
      }else {
    	  tickets.add(new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, assetname, expectedLifeSpanInDays, null, floorNumber, roomNumber, imageURLs, notes));
      }
    }
    return tickets;
  }
}
