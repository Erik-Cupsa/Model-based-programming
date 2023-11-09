package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import java.util.List;

public class AssetPlusFeatureSet5Controller {

    /**
     * adds a ticket image to a specified ticked
     *
     * @param imageURL the image's url
     * @param ticketID the id of the ticket to add the image to
     * @return returns a string explaining the errors if encountered
     * @author Mohamed Abdelrahman
     */
    public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
        String err = "";
        if (imageURL == null || imageURL.length() == 0) {
            err = "Image URL cannot be empty";
        } else {
            try {
                if ((!imageURL.substring(0, 7).equals("http://"))
                        && (!imageURL.substring(0, 8).equals("https://"))) {
                    err = "Image URL must start with http:// or https://";
                }
            } catch (Exception e) {
                err = "Image URL must start with http:// or https://";
            }
        }
        if (ticketID == 3) {
            System.out.println("here");
        }
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
        if (ticket == null) {
            err = "Ticket does not exist";
        } else {
            List<TicketImage> images = ticket.getTicketImages();
            for (TicketImage image : images) {
                if (image.getImageURL().equals(imageURL)) {
                    err = "Image already exists for the ticket";
                }
            }
        }
        if (err.equals("")) {
            TicketImage ticketImage = new TicketImage(imageURL, ticket);
            ticket.addTicketImage(ticketImage);
            try{
                AssetPlusPersistence.save();
            }catch(RuntimeException e){
                return e.getMessage();
            }
        }
        return err;
    }

    /**
     * Deletes an image from a specified ticked
     *
     * @param imageURL the image's url
     * @param ticketID the id of the ticket
     * @author Mohamed Abdelrahman
     */
    public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
        try {

            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            List<TicketImage> images = ticket.getTicketImages();
            for (TicketImage image : images) {
                if (image.getImageURL().equals(imageURL)) {
                    image.delete();
                }
            }

        } catch (Exception e) {
        }
        AssetPlusPersistence.save();

    }
}
