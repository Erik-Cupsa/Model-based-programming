package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureSet5Controller {

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    String err="Invalid imageURL";
    if (imageURL == null 
    || imageURL.length() < 5
    || !imageURL.substring(0, 6).equals("http://") 
    || !imageURL.substring(0, 7).equals("https://"))
    {
      return err; 
    }
    try {
      MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
      TicketImage ticketImage= new TicketImage(imageURL, ticket);
      if (ticket.addTicketImage(ticketImage)){
        return ""; 
      }
    } catch (Exception e) {
      err="Invalid ticketID";
    }
    return err;
  }

  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    try {
      MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
      TicketImage ticketImage= new TicketImage(imageURL, ticket);
      ticket.removeTicketImage(ticketImage);
    } catch (Exception e) {
    }
  }
}
