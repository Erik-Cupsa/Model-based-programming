/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 10 "../../../../../../classes.ump"
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String accountName;
  private String password;
  private String name;
  private String phoneNumber;

  //User Associations
  private AssetPlus assetPlus;
  private List<MaintenanceTicket> ticketsOpened;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aAccountName, String aPassword, AssetPlus aAssetPlus)
  {
    accountName = aAccountName;
    password = aPassword;
    name = null;
    phoneNumber = null;
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create user due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    ticketsOpened = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
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

  public String getAccountName()
  {
    return accountName;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getTicketsOpened(int index)
  {
    MaintenanceTicket aTicketsOpened = ticketsOpened.get(index);
    return aTicketsOpened;
  }

  public List<MaintenanceTicket> getTicketsOpened()
  {
    List<MaintenanceTicket> newTicketsOpened = Collections.unmodifiableList(ticketsOpened);
    return newTicketsOpened;
  }

  public int numberOfTicketsOpened()
  {
    int number = ticketsOpened.size();
    return number;
  }

  public boolean hasTicketsOpened()
  {
    boolean has = ticketsOpened.size() > 0;
    return has;
  }

  public int indexOfTicketsOpened(MaintenanceTicket aTicketsOpened)
  {
    int index = ticketsOpened.indexOf(aTicketsOpened);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetPlus(AssetPlus aAssetPlus)
  {
    boolean wasSet = false;
    if (aAssetPlus == null)
    {
      return wasSet;
    }

    AssetPlus existingAssetPlus = assetPlus;
    assetPlus = aAssetPlus;
    if (existingAssetPlus != null && !existingAssetPlus.equals(aAssetPlus))
    {
      existingAssetPlus.removeUser(this);
    }
    assetPlus.addUser(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTicketsOpened()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addTicketsOpened(Date aDateOpened, Manager aReviewer, Asset aAsset)
  {
    return new MaintenanceTicket(aDateOpened, this, aReviewer, aAsset);
  }

  public boolean addTicketsOpened(MaintenanceTicket aTicketsOpened)
  {
    boolean wasAdded = false;
    if (ticketsOpened.contains(aTicketsOpened)) { return false; }
    User existingTicketInitiator = aTicketsOpened.getTicketInitiator();
    boolean isNewTicketInitiator = existingTicketInitiator != null && !this.equals(existingTicketInitiator);
    if (isNewTicketInitiator)
    {
      aTicketsOpened.setTicketInitiator(this);
    }
    else
    {
      ticketsOpened.add(aTicketsOpened);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicketsOpened(MaintenanceTicket aTicketsOpened)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicketsOpened, as it must always have a ticketInitiator
    if (!this.equals(aTicketsOpened.getTicketInitiator()))
    {
      ticketsOpened.remove(aTicketsOpened);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketsOpenedAt(MaintenanceTicket aTicketsOpened, int index)
  {  
    boolean wasAdded = false;
    if(addTicketsOpened(aTicketsOpened))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketsOpened()) { index = numberOfTicketsOpened() - 1; }
      ticketsOpened.remove(aTicketsOpened);
      ticketsOpened.add(index, aTicketsOpened);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketsOpenedAt(MaintenanceTicket aTicketsOpened, int index)
  {
    boolean wasAdded = false;
    if(ticketsOpened.contains(aTicketsOpened))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketsOpened()) { index = numberOfTicketsOpened() - 1; }
      ticketsOpened.remove(aTicketsOpened);
      ticketsOpened.add(index, aTicketsOpened);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketsOpenedAt(aTicketsOpened, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeUser(this);
    }
    for(int i=ticketsOpened.size(); i > 0; i--)
    {
      MaintenanceTicket aTicketsOpened = ticketsOpened.get(i - 1);
      aTicketsOpened.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "accountName" + ":" + getAccountName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null");
  }
}