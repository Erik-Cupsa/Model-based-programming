/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 3 "../../../../../AssetPlus.ump"
public class AssetPlus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AssetPlus Associations
  private List<Employee> employees;
  private List<Guest> guests;
  private Manager manager;
  private List<MaintenanceTicket> maintenanceTickets;
  private List<AssetType> assetTypes;
  private List<SpecificAsset> specificAssets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AssetPlus()
  {
    employees = new ArrayList<Employee>();
    guests = new ArrayList<Guest>();
    maintenanceTickets = new ArrayList<MaintenanceTicket>();
    assetTypes = new ArrayList<AssetType>();
    specificAssets = new ArrayList<SpecificAsset>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }

  public List<Employee> getEmployees()
  {
    List<Employee> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(Employee aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_GetMany */
  public Guest getGuest(int index)
  {
    Guest aGuest = guests.get(index);
    return aGuest;
  }

  public List<Guest> getGuests()
  {
    List<Guest> newGuests = Collections.unmodifiableList(guests);
    return newGuests;
  }

  public int numberOfGuests()
  {
    int number = guests.size();
    return number;
  }

  public boolean hasGuests()
  {
    boolean has = guests.size() > 0;
    return has;
  }

  public int indexOfGuest(Guest aGuest)
  {
    int index = guests.indexOf(aGuest);
    return index;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }

  public boolean hasManager()
  {
    boolean has = manager != null;
    return has;
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
  /* Code from template association_GetMany */
  public AssetType getAssetType(int index)
  {
    AssetType aAssetType = assetTypes.get(index);
    return aAssetType;
  }

  public List<AssetType> getAssetTypes()
  {
    List<AssetType> newAssetTypes = Collections.unmodifiableList(assetTypes);
    return newAssetTypes;
  }

  public int numberOfAssetTypes()
  {
    int number = assetTypes.size();
    return number;
  }

  public boolean hasAssetTypes()
  {
    boolean has = assetTypes.size() > 0;
    return has;
  }

  public int indexOfAssetType(AssetType aAssetType)
  {
    int index = assetTypes.indexOf(aAssetType);
    return index;
  }
  /* Code from template association_GetMany */
  public SpecificAsset getSpecificAsset(int index)
  {
    SpecificAsset aSpecificAsset = specificAssets.get(index);
    return aSpecificAsset;
  }

  public List<SpecificAsset> getSpecificAssets()
  {
    List<SpecificAsset> newSpecificAssets = Collections.unmodifiableList(specificAssets);
    return newSpecificAssets;
  }

  public int numberOfSpecificAssets()
  {
    int number = specificAssets.size();
    return number;
  }

  public boolean hasSpecificAssets()
  {
    boolean has = specificAssets.size() > 0;
    return has;
  }

  public int indexOfSpecificAsset(SpecificAsset aSpecificAsset)
  {
    int index = specificAssets.indexOf(aSpecificAsset);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Employee addEmployee(String aEmail, String aName, String aPassword, String aPhoneNumber)
  {
    return new Employee(aEmail, aName, aPassword, aPhoneNumber, this);
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    AssetPlus existingAssetPlus = aEmployee.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aEmployee.setAssetPlus(this);
    }
    else
    {
      employees.add(aEmployee);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployee, as it must always have a assetPlus
    if (!this.equals(aEmployee.getAssetPlus()))
    {
      employees.remove(aEmployee);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(Employee aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(Employee aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGuests()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Guest addGuest(String aEmail, String aName, String aPassword, String aPhoneNumber)
  {
    return new Guest(aEmail, aName, aPassword, aPhoneNumber, this);
  }

  public boolean addGuest(Guest aGuest)
  {
    boolean wasAdded = false;
    if (guests.contains(aGuest)) { return false; }
    AssetPlus existingAssetPlus = aGuest.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aGuest.setAssetPlus(this);
    }
    else
    {
      guests.add(aGuest);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGuest(Guest aGuest)
  {
    boolean wasRemoved = false;
    //Unable to remove aGuest, as it must always have a assetPlus
    if (!this.equals(aGuest.getAssetPlus()))
    {
      guests.remove(aGuest);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGuestAt(Guest aGuest, int index)
  {  
    boolean wasAdded = false;
    if(addGuest(aGuest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuests()) { index = numberOfGuests() - 1; }
      guests.remove(aGuest);
      guests.add(index, aGuest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGuestAt(Guest aGuest, int index)
  {
    boolean wasAdded = false;
    if(guests.contains(aGuest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuests()) { index = numberOfGuests() - 1; }
      guests.remove(aGuest);
      guests.add(index, aGuest);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGuestAt(aGuest, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setManager(Manager aNewManager)
  {
    boolean wasSet = false;
    if (manager != null && !manager.equals(aNewManager) && equals(manager.getAssetPlus()))
    {
      //Unable to setManager, as existing manager would become an orphan
      return wasSet;
    }

    manager = aNewManager;
    AssetPlus anOldAssetPlus = aNewManager != null ? aNewManager.getAssetPlus() : null;

    if (!this.equals(anOldAssetPlus))
    {
      if (anOldAssetPlus != null)
      {
        anOldAssetPlus.manager = null;
      }
      if (manager != null)
      {
        manager.setAssetPlus(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addMaintenanceTicket(int aId, Date aRaisedOnDate, String aDescription, User aTicketRaiser)
  {
    return new MaintenanceTicket(aId, aRaisedOnDate, aDescription, this, aTicketRaiser);
  }

  public boolean addMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasAdded = false;
    if (maintenanceTickets.contains(aMaintenanceTicket)) { return false; }
    AssetPlus existingAssetPlus = aMaintenanceTicket.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aMaintenanceTicket.setAssetPlus(this);
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
    //Unable to remove aMaintenanceTicket, as it must always have a assetPlus
    if (!this.equals(aMaintenanceTicket.getAssetPlus()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssetTypes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public AssetType addAssetType(String aName, int aExpectedLifeSpan)
  {
    return new AssetType(aName, aExpectedLifeSpan, this);
  }

  public boolean addAssetType(AssetType aAssetType)
  {
    boolean wasAdded = false;
    if (assetTypes.contains(aAssetType)) { return false; }
    AssetPlus existingAssetPlus = aAssetType.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aAssetType.setAssetPlus(this);
    }
    else
    {
      assetTypes.add(aAssetType);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssetType(AssetType aAssetType)
  {
    boolean wasRemoved = false;
    //Unable to remove aAssetType, as it must always have a assetPlus
    if (!this.equals(aAssetType.getAssetPlus()))
    {
      assetTypes.remove(aAssetType);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssetTypeAt(AssetType aAssetType, int index)
  {  
    boolean wasAdded = false;
    if(addAssetType(aAssetType))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssetTypes()) { index = numberOfAssetTypes() - 1; }
      assetTypes.remove(aAssetType);
      assetTypes.add(index, aAssetType);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssetTypeAt(AssetType aAssetType, int index)
  {
    boolean wasAdded = false;
    if(assetTypes.contains(aAssetType))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssetTypes()) { index = numberOfAssetTypes() - 1; }
      assetTypes.remove(aAssetType);
      assetTypes.add(index, aAssetType);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssetTypeAt(aAssetType, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSpecificAssets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public SpecificAsset addSpecificAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate, AssetType aAssetType)
  {
    return new SpecificAsset(aAssetNumber, aFloorNumber, aRoomNumber, aPurchaseDate, this, aAssetType);
  }

  public boolean addSpecificAsset(SpecificAsset aSpecificAsset)
  {
    boolean wasAdded = false;
    if (specificAssets.contains(aSpecificAsset)) { return false; }
    AssetPlus existingAssetPlus = aSpecificAsset.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aSpecificAsset.setAssetPlus(this);
    }
    else
    {
      specificAssets.add(aSpecificAsset);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSpecificAsset(SpecificAsset aSpecificAsset)
  {
    boolean wasRemoved = false;
    //Unable to remove aSpecificAsset, as it must always have a assetPlus
    if (!this.equals(aSpecificAsset.getAssetPlus()))
    {
      specificAssets.remove(aSpecificAsset);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSpecificAssetAt(SpecificAsset aSpecificAsset, int index)
  {  
    boolean wasAdded = false;
    if(addSpecificAsset(aSpecificAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificAssets()) { index = numberOfSpecificAssets() - 1; }
      specificAssets.remove(aSpecificAsset);
      specificAssets.add(index, aSpecificAsset);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSpecificAssetAt(SpecificAsset aSpecificAsset, int index)
  {
    boolean wasAdded = false;
    if(specificAssets.contains(aSpecificAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificAssets()) { index = numberOfSpecificAssets() - 1; }
      specificAssets.remove(aSpecificAsset);
      specificAssets.add(index, aSpecificAsset);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSpecificAssetAt(aSpecificAsset, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (employees.size() > 0)
    {
      Employee aEmployee = employees.get(employees.size() - 1);
      aEmployee.delete();
      employees.remove(aEmployee);
    }
    
    while (guests.size() > 0)
    {
      Guest aGuest = guests.get(guests.size() - 1);
      aGuest.delete();
      guests.remove(aGuest);
    }
    
    Manager existingManager = manager;
    manager = null;
    if (existingManager != null)
    {
      existingManager.delete();
      existingManager.setAssetPlus(null);
    }
    while (maintenanceTickets.size() > 0)
    {
      MaintenanceTicket aMaintenanceTicket = maintenanceTickets.get(maintenanceTickets.size() - 1);
      aMaintenanceTicket.delete();
      maintenanceTickets.remove(aMaintenanceTicket);
    }
    
    while (assetTypes.size() > 0)
    {
      AssetType aAssetType = assetTypes.get(assetTypes.size() - 1);
      aAssetType.delete();
      assetTypes.remove(aAssetType);
    }
    
    while (specificAssets.size() > 0)
    {
      SpecificAsset aSpecificAsset = specificAssets.get(specificAssets.size() - 1);
      aSpecificAsset.delete();
      specificAssets.remove(aSpecificAsset);
    }
    
  }

}