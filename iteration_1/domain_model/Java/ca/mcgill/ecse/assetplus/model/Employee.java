/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 25 "../../../../../../classes.ump"
public class Employee extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private List<MaintenanceTicket> maintenanceTask;
  private Manager manager;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aAccountName, String aPassword, AssetPlus aAssetPlus, Manager aManager)
  {
    super(aAccountName, aPassword, aAssetPlus);
    maintenanceTask = new ArrayList<MaintenanceTicket>();
    boolean didAddManager = setManager(aManager);
    if (!didAddManager)
    {
      throw new RuntimeException("Unable to create employee due to manager. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public MaintenanceTicket getMaintenanceTask(int index)
  {
    MaintenanceTicket aMaintenanceTask = maintenanceTask.get(index);
    return aMaintenanceTask;
  }

  public List<MaintenanceTicket> getMaintenanceTask()
  {
    List<MaintenanceTicket> newMaintenanceTask = Collections.unmodifiableList(maintenanceTask);
    return newMaintenanceTask;
  }

  public int numberOfMaintenanceTask()
  {
    int number = maintenanceTask.size();
    return number;
  }

  public boolean hasMaintenanceTask()
  {
    boolean has = maintenanceTask.size() > 0;
    return has;
  }

  public int indexOfMaintenanceTask(MaintenanceTicket aMaintenanceTask)
  {
    int index = maintenanceTask.indexOf(aMaintenanceTask);
    return index;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceTask()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addMaintenanceTask(MaintenanceTicket aMaintenanceTask)
  {
    boolean wasAdded = false;
    if (maintenanceTask.contains(aMaintenanceTask)) { return false; }
    maintenanceTask.add(aMaintenanceTask);
    if (aMaintenanceTask.indexOfMaintenanceStaff(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMaintenanceTask.addMaintenanceStaff(this);
      if (!wasAdded)
      {
        maintenanceTask.remove(aMaintenanceTask);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeMaintenanceTask(MaintenanceTicket aMaintenanceTask)
  {
    boolean wasRemoved = false;
    if (!maintenanceTask.contains(aMaintenanceTask))
    {
      return wasRemoved;
    }

    int oldIndex = maintenanceTask.indexOf(aMaintenanceTask);
    maintenanceTask.remove(oldIndex);
    if (aMaintenanceTask.indexOfMaintenanceStaff(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMaintenanceTask.removeMaintenanceStaff(this);
      if (!wasRemoved)
      {
        maintenanceTask.add(oldIndex,aMaintenanceTask);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMaintenanceTaskAt(MaintenanceTicket aMaintenanceTask, int index)
  {  
    boolean wasAdded = false;
    if(addMaintenanceTask(aMaintenanceTask))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTask()) { index = numberOfMaintenanceTask() - 1; }
      maintenanceTask.remove(aMaintenanceTask);
      maintenanceTask.add(index, aMaintenanceTask);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMaintenanceTaskAt(MaintenanceTicket aMaintenanceTask, int index)
  {
    boolean wasAdded = false;
    if(maintenanceTask.contains(aMaintenanceTask))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTask()) { index = numberOfMaintenanceTask() - 1; }
      maintenanceTask.remove(aMaintenanceTask);
      maintenanceTask.add(index, aMaintenanceTask);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMaintenanceTaskAt(aMaintenanceTask, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setManager(Manager aManager)
  {
    boolean wasSet = false;
    if (aManager == null)
    {
      return wasSet;
    }

    Manager existingManager = manager;
    manager = aManager;
    if (existingManager != null && !existingManager.equals(aManager))
    {
      existingManager.removeEmployee(this);
    }
    manager.addEmployee(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<MaintenanceTicket> copyOfMaintenanceTask = new ArrayList<MaintenanceTicket>(maintenanceTask);
    maintenanceTask.clear();
    for(MaintenanceTicket aMaintenanceTask : copyOfMaintenanceTask)
    {
      aMaintenanceTask.removeMaintenanceStaff(this);
    }
    Manager placeholderManager = manager;
    this.manager = null;
    if(placeholderManager != null)
    {
      placeholderManager.removeEmployee(this);
    }
    super.delete();
  }

}