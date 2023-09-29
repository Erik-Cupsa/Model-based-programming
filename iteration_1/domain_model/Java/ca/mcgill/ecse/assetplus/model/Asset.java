/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 52 "../../../../../../classes.ump"
public class Asset
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Asset> assetsByAssetNumber = new HashMap<Integer, Asset>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Asset Attributes
  private int assetNumber;
  private Date purchaseDate;

  //Asset Associations
  private Location location;
  private AssetType assetType;
  private List<MaintenanceTicket> maintenanceTickets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Asset(int aAssetNumber, Date aPurchaseDate, Location aLocation, AssetType aAssetType)
  {
    purchaseDate = aPurchaseDate;
    if (!setAssetNumber(aAssetNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate assetNumber. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddLocation = setLocation(aLocation);
    if (!didAddLocation)
    {
      throw new RuntimeException("Unable to create asset due to location. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAssetType = setAssetType(aAssetType);
    if (!didAddAssetType)
    {
      throw new RuntimeException("Unable to create asset due to assetType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    maintenanceTickets = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAssetNumber(int aAssetNumber)
  {
    boolean wasSet = false;
    Integer anOldAssetNumber = getAssetNumber();
    if (anOldAssetNumber != null && anOldAssetNumber.equals(aAssetNumber)) {
      return true;
    }
    if (hasWithAssetNumber(aAssetNumber)) {
      return wasSet;
    }
    assetNumber = aAssetNumber;
    wasSet = true;
    if (anOldAssetNumber != null) {
      assetsByAssetNumber.remove(anOldAssetNumber);
    }
    assetsByAssetNumber.put(aAssetNumber, this);
    return wasSet;
  }

  public boolean setPurchaseDate(Date aPurchaseDate)
  {
    boolean wasSet = false;
    purchaseDate = aPurchaseDate;
    wasSet = true;
    return wasSet;
  }

  public int getAssetNumber()
  {
    return assetNumber;
  }
  /* Code from template attribute_GetUnique */
  public static Asset getWithAssetNumber(int aAssetNumber)
  {
    return assetsByAssetNumber.get(aAssetNumber);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithAssetNumber(int aAssetNumber)
  {
    return getWithAssetNumber(aAssetNumber) != null;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }
  /* Code from template association_GetOne */
  public Location getLocation()
  {
    return location;
  }
  /* Code from template association_GetOne */
  public AssetType getAssetType()
  {
    return assetType;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getMaintenanceTicket(int index)
  {
    MaintenanceTicket aMaintenanceTicket = maintenanceTickets.get(index);
    return aMaintenanceTicket;
  }

  public List<MaintenanceTicket> getMaintenanceTickets()
  {
    List<MaintenanceTicket> newMaintenanceTickets = Collections.unmodifiableList(maintenanceTickets);
    return newMaintenanceTickets;
  }

  public int numberOfMaintenanceTickets()
  {
    int number = maintenanceTickets.size();
    return number;
  }

  public boolean hasMaintenanceTickets()
  {
    boolean has = maintenanceTickets.size() > 0;
    return has;
  }

  public int indexOfMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    int index = maintenanceTickets.indexOf(aMaintenanceTicket);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLocation(Location aLocation)
  {
    boolean wasSet = false;
    if (aLocation == null)
    {
      return wasSet;
    }

    Location existingLocation = location;
    location = aLocation;
    if (existingLocation != null && !existingLocation.equals(aLocation))
    {
      existingLocation.removeAsset(this);
    }
    location.addAsset(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetType(AssetType aAssetType)
  {
    boolean wasSet = false;
    if (aAssetType == null)
    {
      return wasSet;
    }

    AssetType existingAssetType = assetType;
    assetType = aAssetType;
    if (existingAssetType != null && !existingAssetType.equals(aAssetType))
    {
      existingAssetType.removeAsset(this);
    }
    assetType.addAsset(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addMaintenanceTicket(Date aDateOpened, User aTicketInitiator, Manager aReviewer)
  {
    return new MaintenanceTicket(aDateOpened, aTicketInitiator, aReviewer, this);
  }

  public boolean addMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasAdded = false;
    if (maintenanceTickets.contains(aMaintenanceTicket)) { return false; }
    Asset existingAsset = aMaintenanceTicket.getAsset();
    boolean isNewAsset = existingAsset != null && !this.equals(existingAsset);
    if (isNewAsset)
    {
      aMaintenanceTicket.setAsset(this);
    }
    else
    {
      maintenanceTickets.add(aMaintenanceTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aMaintenanceTicket, as it must always have a asset
    if (!this.equals(aMaintenanceTicket.getAsset()))
    {
      maintenanceTickets.remove(aMaintenanceTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMaintenanceTicketAt(MaintenanceTicket aMaintenanceTicket, int index)
  {  
    boolean wasAdded = false;
    if(addMaintenanceTicket(aMaintenanceTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTickets()) { index = numberOfMaintenanceTickets() - 1; }
      maintenanceTickets.remove(aMaintenanceTicket);
      maintenanceTickets.add(index, aMaintenanceTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMaintenanceTicketAt(MaintenanceTicket aMaintenanceTicket, int index)
  {
    boolean wasAdded = false;
    if(maintenanceTickets.contains(aMaintenanceTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTickets()) { index = numberOfMaintenanceTickets() - 1; }
      maintenanceTickets.remove(aMaintenanceTicket);
      maintenanceTickets.add(index, aMaintenanceTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMaintenanceTicketAt(aMaintenanceTicket, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    assetsByAssetNumber.remove(getAssetNumber());
    Location placeholderLocation = location;
    this.location = null;
    if(placeholderLocation != null)
    {
      placeholderLocation.removeAsset(this);
    }
    AssetType placeholderAssetType = assetType;
    this.assetType = null;
    if(placeholderAssetType != null)
    {
      placeholderAssetType.removeAsset(this);
    }
    while (maintenanceTickets.size() > 0)
    {
      MaintenanceTicket aMaintenanceTicket = maintenanceTickets.get(maintenanceTickets.size() - 1);
      aMaintenanceTicket.delete();
      maintenanceTickets.remove(aMaintenanceTicket);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "assetNumber" + ":" + getAssetNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "location = "+(getLocation()!=null?Integer.toHexString(System.identityHashCode(getLocation())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetType = "+(getAssetType()!=null?Integer.toHexString(System.identityHashCode(getAssetType())):"null");
  }
}