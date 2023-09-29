/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;

// line 65 "../../../../../AssetPlus.ump"
public class TicketImage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TicketImage Attributes
  private String imageURL;

  //TicketImage Associations
  private MaintenanceTicket ticket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TicketImage(String aImageURL, MaintenanceTicket aTicket)
  {
    imageURL = aImageURL;
    boolean didAddTicket = setTicket(aTicket);
    if (!didAddTicket)
    {
      throw new RuntimeException("Unable to create ticketImage due to ticket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setImageURL(String aImageURL)
  {
    boolean wasSet = false;
    imageURL = aImageURL;
    wasSet = true;
    return wasSet;
  }

  public String getImageURL()
  {
    return imageURL;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getTicket()
  {
    return ticket;
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
      existingTicket.removeTicketImage(this);
    }
    ticket.addTicketImage(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderTicket = ticket;
    this.ticket = null;
    if(placeholderTicket != null)
    {
      placeholderTicket.removeTicketImage(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "imageURL" + ":" + getImageURL()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}