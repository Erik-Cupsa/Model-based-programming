/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 38 "../../../../../AssetPlus.ump"
public class Manager extends HotelStaff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private AssetPlus assetPlus;
  private List<MaintenanceTicket> ticketsForApproval;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aEmail, String aName, String aPassword, String aPhoneNumber, AssetPlus aAssetPlus)
  {
    super(aEmail, aName, aPassword, aPhoneNumber);
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create manager due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    ticketsForApproval = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getTicketsForApproval(int index)
  {
    MaintenanceTicket aTicketsForApproval = ticketsForApproval.get(index);
    return aTicketsForApproval;
  }

  public List<MaintenanceTicket> getTicketsForApproval()
  {
    List<MaintenanceTicket> newTicketsForApproval = Collections.unmodifiableList(ticketsForApproval);
    return newTicketsForApproval;
  }

  public int numberOfTicketsForApproval()
  {
    int number = ticketsForApproval.size();
    return number;
  }

  public boolean hasTicketsForApproval()
  {
    boolean has = ticketsForApproval.size() > 0;
    return has;
  }

  public int indexOfTicketsForApproval(MaintenanceTicket aTicketsForApproval)
  {
    int index = ticketsForApproval.indexOf(aTicketsForApproval);
    return index;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAssetPlus(AssetPlus aNewAssetPlus)
  {
    boolean wasSet = false;
    if (aNewAssetPlus == null)
    {
      //Unable to setAssetPlus to null, as manager must always be associated to a assetPlus
      return wasSet;
    }
    
    Manager existingManager = aNewAssetPlus.getManager();
    if (existingManager != null && !equals(existingManager))
    {
      //Unable to setAssetPlus, the current assetPlus already has a manager, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AssetPlus anOldAssetPlus = assetPlus;
    assetPlus = aNewAssetPlus;
    assetPlus.setManager(this);

    if (anOldAssetPlus != null)
    {
      anOldAssetPlus.setManager(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTicketsForApproval()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addTicketsForApproval(MaintenanceTicket aTicketsForApproval)
  {
    boolean wasAdded = false;
    if (ticketsForApproval.contains(aTicketsForApproval)) { return false; }
    Manager existingFixApprover = aTicketsForApproval.getFixApprover();
    if (existingFixApprover == null)
    {
      aTicketsForApproval.setFixApprover(this);
    }
    else if (!this.equals(existingFixApprover))
    {
      existingFixApprover.removeTicketsForApproval(aTicketsForApproval);
      addTicketsForApproval(aTicketsForApproval);
    }
    else
    {
      ticketsForApproval.add(aTicketsForApproval);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicketsForApproval(MaintenanceTicket aTicketsForApproval)
  {
    boolean wasRemoved = false;
    if (ticketsForApproval.contains(aTicketsForApproval))
    {
      ticketsForApproval.remove(aTicketsForApproval);
      aTicketsForApproval.setFixApprover(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketsForApprovalAt(MaintenanceTicket aTicketsForApproval, int index)
  {  
    boolean wasAdded = false;
    if(addTicketsForApproval(aTicketsForApproval))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketsForApproval()) { index = numberOfTicketsForApproval() - 1; }
      ticketsForApproval.remove(aTicketsForApproval);
      ticketsForApproval.add(index, aTicketsForApproval);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketsForApprovalAt(MaintenanceTicket aTicketsForApproval, int index)
  {
    boolean wasAdded = false;
    if(ticketsForApproval.contains(aTicketsForApproval))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketsForApproval()) { index = numberOfTicketsForApproval() - 1; }
      ticketsForApproval.remove(aTicketsForApproval);
      ticketsForApproval.add(index, aTicketsForApproval);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketsForApprovalAt(aTicketsForApproval, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    AssetPlus existingAssetPlus = assetPlus;
    assetPlus = null;
    if (existingAssetPlus != null)
    {
      existingAssetPlus.setManager(null);
    }
    while( !ticketsForApproval.isEmpty() )
    {
      ticketsForApproval.get(0).setFixApprover(null);
    }
    super.delete();
  }

}