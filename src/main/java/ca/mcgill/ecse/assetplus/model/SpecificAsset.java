/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 70 "../../../../../AssetPlus.ump"
public class SpecificAsset
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, SpecificAsset> specificassetsByAssetNumber = new HashMap<Integer, SpecificAsset>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SpecificAsset Attributes
  private int assetNumber;
  private int floorNumber;
  private int roomNumber;
  private Date purchaseDate;

  //SpecificAsset Associations
  private AssetPlus assetPlus;
  private List<MaintenanceTicket> maintenanceTickets;
  private AssetType assetType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SpecificAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate, AssetPlus aAssetPlus, AssetType aAssetType)
  {
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    purchaseDate = aPurchaseDate;
    if (!setAssetNumber(aAssetNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate assetNumber. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create specificAsset due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    maintenanceTickets = new ArrayList<MaintenanceTicket>();
    boolean didAddAssetType = setAssetType(aAssetType);
    if (!didAddAssetType)
    {
      throw new RuntimeException("Unable to create specificAsset due to assetType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
      specificassetsByAssetNumber.remove(anOldAssetNumber);
    }
    specificassetsByAssetNumber.put(aAssetNumber, this);
    return wasSet;
  }

  public boolean setFloorNumber(int aFloorNumber)
  {
    boolean wasSet = false;
    floorNumber = aFloorNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(int aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
    wasSet = true;
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
  public static SpecificAsset getWithAssetNumber(int aAssetNumber)
  {
    return specificassetsByAssetNumber.get(aAssetNumber);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithAssetNumber(int aAssetNumber)
  {
    return getWithAssetNumber(aAssetNumber) != null;
  }

  public int getFloorNumber()
  {
    return floorNumber;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
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
  /* Code from template association_GetOne */
  public AssetType getAssetType()
  {
    return assetType;
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
      existingAssetPlus.removeSpecificAsset(this);
    }
    assetPlus.addSpecificAsset(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasAdded = false;
    if (maintenanceTickets.contains(aMaintenanceTicket)) { return false; }
    SpecificAsset existingAsset = aMaintenanceTicket.getAsset();
    if (existingAsset == null)
    {
      aMaintenanceTicket.setAsset(this);
    }
    else if (!this.equals(existingAsset))
    {
      existingAsset.removeMaintenanceTicket(aMaintenanceTicket);
      addMaintenanceTicket(aMaintenanceTicket);
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
    if (maintenanceTickets.contains(aMaintenanceTicket))
    {
      maintenanceTickets.remove(aMaintenanceTicket);
      aMaintenanceTicket.setAsset(null);
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
      existingAssetType.removeSpecificAsset(this);
    }
    assetType.addSpecificAsset(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    specificassetsByAssetNumber.remove(getAssetNumber());
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeSpecificAsset(this);
    }
    while( !maintenanceTickets.isEmpty() )
    {
      maintenanceTickets.get(0).setAsset(null);
    }
    AssetType placeholderAssetType = assetType;
    this.assetType = null;
    if(placeholderAssetType != null)
    {
      placeholderAssetType.removeSpecificAsset(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "assetNumber" + ":" + getAssetNumber()+ "," +
            "floorNumber" + ":" + getFloorNumber()+ "," +
            "roomNumber" + ":" + getRoomNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetType = "+(getAssetType()!=null?Integer.toHexString(System.identityHashCode(getAssetType())):"null");
  }
}