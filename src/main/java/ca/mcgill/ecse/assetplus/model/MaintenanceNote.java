/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;

// line 59 "../../../../../AssetPlus.ump"
public class MaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceNote Attributes
  private Date date;
  private String description;

  //MaintenanceNote Associations
  private MaintenanceTicket ticket;
  private HotelStaff noteTaker;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceNote(Date aDate, String aDescription, MaintenanceTicket aTicket, HotelStaff aNoteTaker)
  {
    date = aDate;
    description = aDescription;
    boolean didAddTicket = setTicket(aTicket);
    if (!didAddTicket)
    {
      throw new RuntimeException("Unable to create ticketNote due to ticket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddNoteTaker = setNoteTaker(aNoteTaker);
    if (!didAddNoteTaker)
    {
      throw new RuntimeException("Unable to create maintenanceNote due to noteTaker. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getTicket()
  {
    return ticket;
  }
  /* Code from template association_GetOne */
  public HotelStaff getNoteTaker()
  {
    return noteTaker;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTicket(MaintenanceTicket aTicket)
  {
    boolean wasSet = false;
    if (aTicket == null)
    {
      return wasSet;
    }

    MaintenanceTicket existingTicket = ticket;
    ticket = aTicket;
    if (existingTicket != null && !existingTicket.equals(aTicket))
    {
      existingTicket.removeTicketNote(this);
    }
    ticket.addTicketNote(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setNoteTaker(HotelStaff aNoteTaker)
  {
    boolean wasSet = false;
    if (aNoteTaker == null)
    {
      return wasSet;
    }

    HotelStaff existingNoteTaker = noteTaker;
    noteTaker = aNoteTaker;
    if (existingNoteTaker != null && !existingNoteTaker.equals(aNoteTaker))
    {
      existingNoteTaker.removeMaintenanceNote(this);
    }
    noteTaker.addMaintenanceNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderTicket = ticket;
    this.ticket = null;
    if(placeholderTicket != null)
    {
      placeholderTicket.removeTicketNote(this);
    }
    HotelStaff placeholderNoteTaker = noteTaker;
    this.noteTaker = null;
    if(placeholderNoteTaker != null)
    {
      placeholderNoteTaker.removeMaintenanceNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "noteTaker = "+(getNoteTaker()!=null?Integer.toHexString(System.identityHashCode(getNoteTaker())):"null");
  }
}