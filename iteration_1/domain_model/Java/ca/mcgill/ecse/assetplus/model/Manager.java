/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 32 "../../../../../../classes.ump"
public class Manager extends Employee
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private List<Employee> employees;
  private List<MaintenanceTicket> ticketsToApprove;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aAccountName, String aPassword, AssetPlus aAssetPlus, Manager aManager)
  {
    super(aAccountName, aPassword, aAssetPlus, aManager);
    employees = new ArrayList<Employee>();
    ticketsToApprove = new ArrayList<MaintenanceTicket>();
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
  public MaintenanceTicket getTicketsToApprove(int index)
  {
    MaintenanceTicket aTicketsToApprove = ticketsToApprove.get(index);
    return aTicketsToApprove;
  }

  public List<MaintenanceTicket> getTicketsToApprove()
  {
    List<MaintenanceTicket> newTicketsToApprove = Collections.unmodifiableList(ticketsToApprove);
    return newTicketsToApprove;
  }

  public int numberOfTicketsToApprove()
  {
    int number = ticketsToApprove.size();
    return number;
  }

  public boolean hasTicketsToApprove()
  {
    boolean has = ticketsToApprove.size() > 0;
    return has;
  }

  public int indexOfTicketsToApprove(MaintenanceTicket aTicketsToApprove)
  {
    int index = ticketsToApprove.indexOf(aTicketsToApprove);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Employee addEmployee(String aAccountName, String aPassword, AssetPlus aAssetPlus)
  {
    return new Employee(aAccountName, aPassword, aAssetPlus, this);
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    Manager existingManager = aEmployee.getManager();
    boolean isNewManager = existingManager != null && !this.equals(existingManager);
    if (isNewManager)
    {
      aEmployee.setManager(this);
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
    //Unable to remove aEmployee, as it must always have a manager
    if (!this.equals(aEmployee.getManager()))
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
  public static int minimumNumberOfTicketsToApprove()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addTicketsToApprove(Date aDateOpened, User aTicketInitiator, Asset aAsset)
  {
    return new MaintenanceTicket(aDateOpened, aTicketInitiator, this, aAsset);
  }

  public boolean addTicketsToApprove(MaintenanceTicket aTicketsToApprove)
  {
    boolean wasAdded = false;
    if (ticketsToApprove.contains(aTicketsToApprove)) { return false; }
    Manager existingReviewer = aTicketsToApprove.getReviewer();
    boolean isNewReviewer = existingReviewer != null && !this.equals(existingReviewer);
    if (isNewReviewer)
    {
      aTicketsToApprove.setReviewer(this);
    }
    else
    {
      ticketsToApprove.add(aTicketsToApprove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicketsToApprove(MaintenanceTicket aTicketsToApprove)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicketsToApprove, as it must always have a reviewer
    if (!this.equals(aTicketsToApprove.getReviewer()))
    {
      ticketsToApprove.remove(aTicketsToApprove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketsToApproveAt(MaintenanceTicket aTicketsToApprove, int index)
  {  
    boolean wasAdded = false;
    if(addTicketsToApprove(aTicketsToApprove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketsToApprove()) { index = numberOfTicketsToApprove() - 1; }
      ticketsToApprove.remove(aTicketsToApprove);
      ticketsToApprove.add(index, aTicketsToApprove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketsToApproveAt(MaintenanceTicket aTicketsToApprove, int index)
  {
    boolean wasAdded = false;
    if(ticketsToApprove.contains(aTicketsToApprove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketsToApprove()) { index = numberOfTicketsToApprove() - 1; }
      ticketsToApprove.remove(aTicketsToApprove);
      ticketsToApprove.add(index, aTicketsToApprove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketsToApproveAt(aTicketsToApprove, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=employees.size(); i > 0; i--)
    {
      Employee aEmployee = employees.get(i - 1);
      aEmployee.delete();
    }
    for(int i=ticketsToApprove.size(); i > 0; i--)
    {
      MaintenanceTicket aTicketsToApprove = ticketsToApprove.get(i - 1);
      aTicketsToApprove.delete();
    }
    super.delete();
  }

}