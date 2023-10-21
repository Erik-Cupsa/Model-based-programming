package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureSet5Controller {

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    String err = "";
    if (imageURL == null || imageURL.length() == 0){
      err="Image URL cannot be empty";
    }
//    else if (((imageURL.length() > 6) && (!imageURL.substring(0, 7).equals("http://")))
//            || ((imageURL.length() > 7) && (!imageURL.substring(0, 8).equals("https://"))))
    else
    {
      try {
      if ((!imageURL.substring(0, 7).equals("http://"))
        && (!imageURL.substring(0, 8).equals("https://")))
      {
        err="Image URL must start with http:// or https://";
      }
    } catch (Exception e) {
        err="Image URL must start with http:// or https://";
    }
    }

    if (err == "") {
      try {
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
        TicketImage ticketImage = new TicketImage(imageURL, ticket);
        if (!ticket.addTicketImage(ticketImage)) {
          err = "Image already exists for the ticket";
        }
      } catch (Exception e) {
        err = "Ticket does not exist";
      }
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
