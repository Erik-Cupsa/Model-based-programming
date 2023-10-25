package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;

import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

public class AssetPlusFeatureSet7Controller {

	public static String addMaintenanceNote(Date date, String description, int ticketID,
			String email) {
		if(description==null || description.equals("")){
			return "Ticket description cannot be empty";
		}
		MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
		if (ticket==null){
			return "Ticket does not exist";
		}
		HotelStaff noteTaker = (HotelStaff) HotelStaff.getWithEmail(email);
		if (noteTaker==null){
			return "Hotel staff does not exist";
		}
		MaintenanceNote newNote = new MaintenanceNote(date, description, ticket, noteTaker);
		ticket.addTicketNote(newNote);
		return "";
	}

	// index starts at 0
	public static String updateMaintenanceNote(int ticketID, int index, Date newDate,
			String newDescription, String newEmail) {
		if(newDescription==null || newDescription.equals("")){
			return "Ticket description cannot be empty";
		}
		MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
		if (ticket==null){
			return "Ticket does not exist";
		}
		MaintenanceNote targetNote;
		try{
			targetNote= ticket.getTicketNote(index);
		}catch(Exception e){
			return "Note does not exist";
		}
		if (targetNote==null){
			return "Note does not exist";
		}
		HotelStaff newNoteTaker = (HotelStaff) HotelStaff.getWithEmail(newEmail);
		if (newNoteTaker==null){
			return "Hotel staff does not exist";
		}
		targetNote.setDate(newDate);
		targetNote.setDescription(newDescription);
		targetNote.setNoteTaker(newNoteTaker);
		return "";
	}

	// index starts at 0
	public static void deleteMaintenanceNote(int ticketID, int index) {
		try{
			MaintenanceTicket ticket= MaintenanceTicket.getWithId(ticketID);
			MaintenanceNote targetNote = ticket.getTicketNote(index);
			targetNote.delete();
		}catch(Exception e){

		}
	}
}
