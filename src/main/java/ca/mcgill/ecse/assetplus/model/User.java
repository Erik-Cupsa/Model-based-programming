/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 13 "../../../../../AssetPlus.ump"
public abstract class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByEmail = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String email;
  private String name;
  private String password;
  private String phoneNumber;

  //User Associations
  private List<MaintenanceTicket> raisedTickets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aEmail, String aName, String aPassword, String aPhoneNumber)
  {
    name = aName;
    password = aPassword;
    phoneNumber = aPhoneNumber;
    if (!setEmail(aEmail))
    {
      throw new RuntimeException("Cannot create due to duplicate email. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    raisedTickets = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    String anOldEmail = getEmail();
    if (anOldEmail != null && anOldEmail.equals(aEmail)) {
      return true;
    }
    if (hasWithEmail(aEmail)) {
      return wasSet;
    }
    email = aEmail;
    wasSet = true;
    if (anOldEmail != null) {
      usersByEmail.remove(anOldEmail);
    }
    usersByEmail.put(aEmail, this);
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithEmail(String aEmail)
  {
    return usersByEmail.get(aEmail);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEmail(String aEmail)
  {
    return getWithEmail(aEmail) != null;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getRaisedTicket(int index)
  {
    MaintenanceTicket aRaisedTicket = raisedTickets.get(index);
    return aRaisedTicket;
  }

  public List<MaintenanceTicket> getRaisedTickets()
  {
    List<MaintenanceTicket> newRaisedTickets = Collections.unmodifiableList(raisedTickets);
    return newRaisedTickets;
  }

  public int numberOfRaisedTickets()
  {
    int number = raisedTickets.size();
    return number;
  }

  public boolean hasRaisedTickets()
  {
    boolean has = raisedTickets.size() > 0;
    return has;
  }

  public int indexOfRaisedTicket(MaintenanceTicket aRaisedTicket)
  {
    int index = raisedTickets.indexOf(aRaisedTicket);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRaisedTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addRaisedTicket(int aId, Date aRaisedOnDate, String aDescription, AssetPlus aAssetPlus)
  {
    return new MaintenanceTicket(aId, aRaisedOnDate, aDescription, aAssetPlus, this);
  }

  public boolean addRaisedTicket(MaintenanceTicket aRaisedTicket)
  {
    boolean wasAdded = false;
    if (raisedTickets.contains(aRaisedTicket)) { return false; }
    User existingTicketRaiser = aRaisedTicket.getTicketRaiser();
    boolean isNewTicketRaiser = existingTicketRaiser != null && !this.equals(existingTicketRaiser);
    if (isNewTicketRaiser)
    {
      aRaisedTicket.setTicketRaiser(this);
    }
    else
    {
      raisedTickets.add(aRaisedTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRaisedTicket(MaintenanceTicket aRaisedTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aRaisedTicket, as it must always have a ticketRaiser
    if (!this.equals(aRaisedTicket.getTicketRaiser()))
    {
      raisedTickets.remove(aRaisedTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRaisedTicketAt(MaintenanceTicket aRaisedTicket, int index)
  {  
    boolean wasAdded = false;
    if(addRaisedTicket(aRaisedTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRaisedTickets()) { index = numberOfRaisedTickets() - 1; }
      raisedTickets.remove(aRaisedTicket);
      raisedTickets.add(index, aRaisedTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRaisedTicketAt(MaintenanceTicket aRaisedTicket, int index)
  {
    boolean wasAdded = false;
    if(raisedTickets.contains(aRaisedTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRaisedTickets()) { index = numberOfRaisedTickets() - 1; }
      raisedTickets.remove(aRaisedTicket);
      raisedTickets.add(index, aRaisedTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRaisedTicketAt(aRaisedTicket, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    usersByEmail.remove(getEmail());
    for(int i=raisedTickets.size(); i > 0; i--)
    {
      MaintenanceTicket aRaisedTicket = raisedTickets.get(i - 1);
      aRaisedTicket.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]";
  }
}