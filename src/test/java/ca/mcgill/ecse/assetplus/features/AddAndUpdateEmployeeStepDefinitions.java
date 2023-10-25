package ca.mcgill.ecse.assetplus.features;

import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller.addEmployeeOrGuest;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller.updateEmployeeOrGuest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateEmployeeStepDefinitions {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private String error="";


  /**
   * @author Jatin Patel and Anastasiia Nemyrovska
   */
  @Given("the following employees exist in the system \\(p11)")
  public void the_following_employees_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> employeesToAdd = dataTable.asMaps();
    for (Map<String, String> employee : employeesToAdd) {
      String email = employee.get("email");
      String name = employee.get("name");
      String password = employee.get("password");
      String phoneNumber = employee.get("phoneNumber");
      assetPlus.addEmployee(email, name, password, phoneNumber);
    }
  }

  /**
   * @author Jatin Patel and Anastasiia Nemyrovska
   */
  @Given("the following manager exists in the system \\(p11)")
  public void the_following_manager_exists_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> managerToAdd = dataTable.asMaps();
    for (Map<String, String> manager : managerToAdd) {
      String email = manager.get("email");
      String name = manager.get("name");
      String password = manager.get("password");
      String phoneNumber = manager.get("phoneNumber");
      new Manager(email, name, password, phoneNumber, assetPlus);
    }
  }

  /**
   * @author Jatin Patel and Anastasiia Nemyrovska
   */
  @When("a new employee attempts to register with {string}, {string}, {string}, and {string} \\(p11)")
  public void a_new_employee_attempts_to_register_with_and_p11(String email, String password,
      String name, String phoneNumber) {

    callController(addEmployeeOrGuest(email, password, name, phoneNumber, true));
  }

  /**
   * @author Pei Yan Geng, Dmytro Martyniuk and Laurence Perreault
   */
  @When("the employee with {string} attempts to update their account information to {string}, {string}, and {string} \\(p11)")
  public void the_employee_with_attempts_to_update_their_account_information_to_and_p11(
      String email, String newPassword, String newName, String newPhoneNumber) {
    callController(updateEmployeeOrGuest(email, newPassword, newName, newPhoneNumber));
  }

  /**
   * @author Pei Yan Geng, Dmytro Martyniuk and Laurence Perreault
   */
  @Then("the following {string} shall be raised \\(p11)")
  public void the_following_shall_be_raised_p11(String errorString) {
    assertTrue(error.contains(errorString));
  }

  /**
   * @author Pei Yan Geng, Dmytro Martyniuk and Laurence Perreault
   */
  @Then("the number of employees in the system shall be {string} \\(p11)")
  public void the_number_of_employees_in_the_system_shall_be_p11(String expectedNumberOfEmployees) {

    List<Employee> employees = assetPlus.getEmployees();

    assertEquals((Integer) employees.size(), Integer.parseInt(expectedNumberOfEmployees));
  }

  /**
   * @author Marc-Antoine Nadeau & Behrad Rezaie
   */
  @Then("a new employee account shall exist with {string}, {string}, {string}, and {string} \\(p11)")
  public void a_new_employee_account_shall_exist_with_and_p11(String email, String password,
      String name, String phoneNumber) {

    // Checks an employee with given email exists
    assertTrue(Employee.hasWithEmail(email));
    // Checks other employee attributes
    Employee existingEmployee = (Employee) User.getWithEmail(email);
    assertEquals(password, existingEmployee.getPassword());
    assertEquals(name, existingEmployee.getName());
    assertEquals(phoneNumber, existingEmployee.getPhoneNumber());

  }

  /**
   * @author Marc-Antoine Nadeau & Behrad Rezaie
   */
  @Then("their employee account information will be updated and is now {string}, {string}, {string}, and {string} \\(p11)")
  public void their_employee_account_information_will_be_updated_and_is_now_and_p11(String email,
      String newPassword, String newName, String newPhoneNumber) {

    assertTrue(Employee.hasWithEmail(email));
    Employee updatedEmployeeWithKnownEmailAddress = (Employee) User.getWithEmail(email);
    assertEquals(newPassword, updatedEmployeeWithKnownEmailAddress.getPassword());
    assertEquals(newName, updatedEmployeeWithKnownEmailAddress.getName());
    assertEquals(newPhoneNumber, updatedEmployeeWithKnownEmailAddress.getPhoneNumber());
  }

  /**
   * @author Marc-Antoine Nadeau & Behrad Rezaie
   */
  @Then("the following employees shall exist in the system \\(p11)")
  public void the_following_employees_shall_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> employeeData = dataTable.asMaps();
    // dataTable should be converted to this format:
    // [
    // { "email"="jeff@ap.com", "password"="pass1", "name"="Jeff", "phoneNumber"="(555)555-5555" },
    // { "email"="john@ap.com", "password"="pass2", "name"="John", "phoneNumber"="(444)444-4444" }
    // ]
    for (Map<String, String> data : employeeData) {
      // Get info from each sub-directory
      String email = data.get("email");
      String name = data.get("name");
      String password = data.get("password");
      String phoneNumber = data.get("phoneNumber");

      // Compares it to what we have in the system
      Employee employee = (Employee) User.getWithEmail(email);
      assertNotNull(employee, "Employee with" + email + " was not found.");
      assertEquals(name, employee.getName());
      assertEquals(password, employee.getPassword());
      assertEquals(phoneNumber, employee.getPhoneNumber());
    }
  }

  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
    }
  }
}
