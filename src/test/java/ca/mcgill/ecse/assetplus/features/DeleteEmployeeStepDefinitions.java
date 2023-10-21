package ca.mcgill.ecse.assetplus.features;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteEmployeeStepDefinitions {

  private AssetPlus assetPlus =
      ca.mcgill.ecse.assetplus.application.AssetPlusApplication.getAssetPlus();

  /**
   * This step checks if the following employees exist in the system.
   * 
   * @author Kevin Li
   * @param dataTable This is a data table containing all the employees that exists in the system.
   */
  @Given("the following employees exist in the system \\(p1)")
  public void the_following_employees_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String name = row.get("name");
      String password = row.get("password");
      String phoneNumber = row.get("phoneNumber");
      new Employee(email, name, password, phoneNumber, assetPlus);
    }
  }

  /**
   * This step checks if the following managers exist in the system.
   * 
   * @author Mathieu Allaire
   * @param dataTable This is a data table containing all the managers that exists in the system.
   */
  @Given("the following manager exists in the system \\(p1)")
  public void the_following_manager_exists_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      new Manager(email, "", password, "", assetPlus);
    }
  }

  /**
   * This step deletes an employee account linked to a particular email string.
   * 
   * @author Luis Jarquin
   * @param employeeEmail This is a string containing the email address linked ot an employee
   *        account.
   */
  @When("the employee attempts to delete their own account linked to the {string} \\(p1)")
  public void the_employee_attempts_to_delete_their_own_account_linked_to_the_p1(
      String employeeEmail) {
    AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(employeeEmail);
  }

  /**
   * This step checks that once the employee with email string attempts to delete their own account,
   * their account does not exist in the system.
   * 
   * @author Jerome Desrosiers
   * @param employeeEmail This is a string containing the email address that was linked to the now
   *        deleted employee account.
   */
  @Then("the employee account linked to {string} shall not exist in the system \\(p1)")
  public void the_employee_account_linked_to_shall_not_exist_in_the_system_p1(
      String employeeEmail) {
    User actualEmployee = Employee.getWithEmail(employeeEmail);
    if (actualEmployee != null) {
      Assertions.assertFalse(actualEmployee instanceof Employee);
    }
  }

  /**
   * This step checks that after the employee with email string attempts to delete their own
   * account, the manager still exists in the system.
   * 
   * @author Yuri Sorice
   * @param managerEmail This is a string containing the email address of the manager.
   */
  @Then("the manager account linked to {string} shall exist in the system \\(p1)")
  public void the_manager_account_linked_to_shall_exist_in_the_system_p1(String managerEmail) {
    User expectedManager = Manager.getWithEmail(managerEmail);
    User actualManager = assetPlus.getManager();
    Assertions.assertEquals(expectedManager, actualManager);
  }

  /**
   * This step checks that the number of employees in the system is correct after deletion of an
   * employee with email string.
   * 
   * @author Tessa Hason
   * @param numOfEmployees This is a string containing the expected number of employees.
   */
  @Then("the number of employees in the system shall be {string} \\(p1)")
  public void the_number_of_employees_in_the_system_shall_be_p1(String numOfEmployees) {
    int expectedNumOfEmployees = Integer.parseInt(numOfEmployees);
    int actualNumOfEmployees = assetPlus.numberOfEmployees();
    Assertions.assertEquals(expectedNumOfEmployees, actualNumOfEmployees);
  }
}
