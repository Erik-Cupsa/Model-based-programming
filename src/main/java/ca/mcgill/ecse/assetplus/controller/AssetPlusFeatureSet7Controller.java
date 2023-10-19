package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;

import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

public class AssetPlusFeatureSet7Controller {

	public static String addMaintenanceNote(Date date, String description, int ticketID,
			String email) {
		String msg = "";
		try {
			MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
			HotelStaff noteTaker = (HotelStaff) HotelStaff.getWithEmail(email);
			MaintenanceNote newNote = new MaintenanceNote(date, description, ticket, noteTaker);
			ticket.addTicketNote(newNote);
			msg="Successful";
		}catch(Exception e){
			msg="Invalid ticket or Hotel Staff";
		}
		return msg;
	}

	// index starts at 0
	public static String updateMaintenanceNote(int ticketID, int index, Date newDate,
			String newDescription, String newEmail) {
		String msg = "";
		try {
			MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
			MaintenanceNote targetNote = ticket.getTicketNote(index);
			HotelStaff newNoteTaker = (HotelStaff) HotelStaff.getWithEmail(newEmail);
			targetNote.setDate(newDate);
			targetNote.setDescription(newDescription);
			targetNote.setNoteTaker(newNoteTaker);
			msg="Successful";
		}catch(Exception e){
			msg="Invalid ticket or Hotel Staff";
		} 
		return msg;
	}

	// index starts at 0
	public static void deleteMaintenanceNote(int ticketID, int index) {
		MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
		MaintenanceNote targetNote = ticket.getTicketNote(index);
		targetNote.delete();
	}
}
