package ca.mcgill.ecse.assetplus.features;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateAssetTypeStepDefinitions {
  String error;
  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus(); // Instance of AssetPlus
                                                                     // retrieved from
                                                                     // AssetPlusApplication

  /**
   * @param dataTable represents assetTypes that should exist in system
   * @author Namir Habib ; Mahmoud Amin ; Thibaut Chan Teck Su
   */
  @Given("the following asset types exist in the system \\(p14)")
  public void the_following_asset_types_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int expectedLifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
      new AssetType(name, expectedLifeSpan, assetPlus);
    }
  }

  /**
   * @param string represents name of the assetType
   * @param string2 represents lifespan of the assetType Defines steps so :that manager can
   *        successfully add an AssetType
   * @author Namir Habib ; Mahmoud Amin ; Thibaut Chan Teck Su
   */
  @When("the manager attempts to add a new asset type to the system with name {string} and expected life span of {string} days \\(p14)")
  public void the_manager_attempts_to_add_a_new_asset_type_to_the_system_with_name_and_expected_life_span_of_days_p14(
      String name, String expectedLifeSpan) {
    error = AssetPlusFeatureSet2Controller.addAssetType(name, parseInt(expectedLifeSpan));
  }

  /**
   * @param string assetType to be updated
   * @param string2 new name of assetType
   * @param string3 new lifespan of assetType Defines steps so : manager can successfully update an
   *        AssetType
   * @author Namir Habib ; Mahmoud Amin ; Thibaut Chan Teck Su
   */
  @When("the manager attempts to update an asset type in the system with name {string} to have name {string} and expected life span of {string} days \\(p14)")
  public void the_manager_attempts_to_update_an_asset_type_in_the_system_with_name_to_have_name_and_expected_life_span_of_days_p14(
      String oldName, String newName, String expectedLifeSpan) {
    error = AssetPlusFeatureSet2Controller.updateAssetType(oldName, newName,
        parseInt(expectedLifeSpan));
  }

  /**
   * @param string represents name of the assetType Verifies that number of assetTypes in system is
   *        correct
   * @author Sophia Carbone ; Mathias Pacheco Lemina
   */
  @Then("the number of asset types in the system shall be {string} \\(p14)")
  public void the_number_of_asset_types_in_the_system_shall_be_p14(String number) {
    assertEquals(assetPlus.numberOfAssetTypes(), parseInt(number));
  }

  /**
   * @param dataTable represents assetTypes that should exist in system Verifies that number of
   *        assetTypes in datatable exist in assetPLus application
   * @author Sophia Carbone
   */
  @Then("the following asset types shall exist in the system \\(p14)")
  public void the_following_asset_types_shall_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int expectedLifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
      AssetType e = AssetType.getWithName(name);

      assertNotNull(e);
      assertEquals(e.getExpectedLifeSpan(), expectedLifeSpan);
    }
  }

  /**
   * @param string Represents name of assetType supposed to exist in the system
   * @param string2 Represents expectedLifespan (if string CORRECTLY exist, then associated object
   *        should have that corresponding lifespan
   * @author Sophia Carbone ; Mathias Pacheco Lemina ; Anslean Albert Jeyaras
   */
  @Then("the asset type with name {string} and expected life span of {string} days shall exist in the system \\(p14)")
  public void the_asset_type_with_name_and_expected_life_span_of_days_shall_exist_in_the_system_p14(
      String name, String expectedLifeSpan) {
    AssetType e = AssetType.getWithName(name); // Get the new element to test
    assertNotNull(e);
  }

  /**
   * @param string Represents name of assetType supposed to NOT exist in system
   * @param string2 Represents expectedLifespan ( if string DOES exist, then associated string2
   *        should not) Verifies assetType AssetType(string, string2, assetPLus) does not exist
   * @author Sophia Carbone ; Mathias Pacheco Lemina ; Anslean Albert Jeyaras
   */
  @Then("the asset type with name {string} and expected life span of {string} days shall not exist in the system \\(p14)")
  public void the_asset_type_with_name_and_expected_life_span_of_days_shall_not_exist_in_the_system_p14(
      String name, String expectedLifeSpan) {
    AssetType e = AssetType.getWithName(name); // Get the old or new element to test
    assertNull(e);
  }

  /**
   * @param string : The error message that should be raised
   * @author Mathias Pacheco Lemina
   */
  @Then("the system shall raise the error {string} \\(p14)")
  public void the_system_shall_raise_the_error_p14(String expectedError) {
    assertEquals(error, expectedError);
  }
}
