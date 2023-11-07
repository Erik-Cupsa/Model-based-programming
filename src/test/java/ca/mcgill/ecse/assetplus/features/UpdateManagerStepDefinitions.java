package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Manager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class UpdateManagerStepDefinitions {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  private String error = "";
  private String email = "";
  private String password = "";

  /**
   * @author Omar Moussa
   */
  @Given("the following manager exists in the system \\(p6)")
  public void the_following_manager_exists_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    Manager manager =
        new Manager(dataTable.row(1).get(0), null, dataTable.row(1).get(1), null, assetPlus);
    if (!assetPlus.hasManager())
      assetPlus.setManager(manager);

    Assertions.assertTrue(assetPlus.hasManager());
    Assertions.assertEquals("manager@ap.com", assetPlus.getManager().getEmail());
    Assertions.assertEquals("manager", assetPlus.getManager().getPassword());

  }

  /**
   * @author Omar Moussa
   */
  @When("a manager with {string} attempts to update their password to {string} \\(p6)")
  public void a_manager_with_attempts_to_update_their_password_to_p6(String email,
      String password) {
    this.error = AssetPlusFeatureSet1Controller.updateManager(password);
    this.email = assetPlus.getManager().getEmail();
    this.password = assetPlus.getManager().getPassword();
  }

  /**
   * @author Achraf Ghellach
   */
  @Then("the following {string} shall be raised \\(p6)")
  public void the_following_shall_be_raised_p6(String errorExpected) {
    Assertions.assertTrue(error.contains(errorExpected));
    error = "";
  }

  /**
   * @author Achraf Ghellach
   */
  @Then("the manager account information will not be updated and will keep {string} and {string} \\(p6)")

  public void the_manager_account_information_will_not_be_updated_and_will_keep_and_p6(
          String email, String password) {
    Assertions.assertEquals(email, this.email);
    Assertions.assertEquals(password, this.password);
  }

  /**
   * @author Teddy El-Husseini
   */
  @Then("the manager account information will be updated and is now {string} and {string} \\(p6)")
  public void the_manager_account_information_will_be_updated_and_is_now_and_p6(String email,
                                                                                String password) {
    Assertions.assertEquals(email, this.email);
    Assertions.assertEquals(password, this.password);
  }

  /**
   * @author Teddy El-Husseini
   */
  @Then("the number of managers in the system shall be {string} \\(p6)")
  public void the_number_of_managers_in_the_system_shall_be_p6(String numberOfManagersExpected) {
    String managerNumber = "0";
    boolean managerExists = AssetPlusApplication.getAssetPlus().hasManager();
    if (managerExists) {
      managerNumber = "1";
    }
    assertEquals(numberOfManagersExpected, managerNumber);
  }
}
