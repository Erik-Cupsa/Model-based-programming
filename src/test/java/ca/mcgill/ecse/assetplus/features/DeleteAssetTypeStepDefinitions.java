package ca.mcgill.ecse.assetplus.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for the DeleteAssetType feature Checks that an existing asset type is no longer
 * available in the system when deleted by a manager
 * 
 * @author Team P4
 */
public class DeleteAssetTypeStepDefinitions {

  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
   * Loads asset types from the input dataTable
   * 
   * @param dataTable
   */
  private void loadAssetTypes(io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, Object>> rows = dataTable.asMaps(String.class, Object.class);

    for (Map<String, Object> row : rows) {
      String name = (row.get("name")).toString();
      int expectedLifeSpan = Integer.parseInt(row.get("expectedLifeSpan").toString());
      assetPlus.addAssetType(name, expectedLifeSpan);
    }
  }

  /**
   * Loads the input dataTable and creates the given asset types
   * 
   * @param dataTable
   */
  @Given("the following asset types exist in the system \\(p4)")
  public void the_following_asset_types_exist_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {
    loadAssetTypes(dataTable);
  }

  /**
   * Calls the deleteAssetType controller method to delete the asset type with the input name
   * 
   * @param assetType
   */
  @When("the manager attempts to delete an asset type in the system with name {string} \\(p4)")
  public void the_manager_attempts_to_delete_an_asset_type_in_the_system_with_name_p4(
      String assetType) {
    AssetPlusFeatureSet2Controller.deleteAssetType(assetType);
  }

  /**
   * Checks that the number of asset types in the system is equal to the expected input number
   * 
   * @param expectedNumberOfAssetTypes
   */
  @Then("the number of asset types in the system shall be {string} \\(p4)")
  public void the_number_of_asset_types_in_the_system_shall_be_p4(
      String expectedNumberOfAssetTypes) {
    int expected = Integer.parseInt(expectedNumberOfAssetTypes);
    assertEquals(expected, assetPlus.numberOfAssetTypes());
  }

  /**
   * Checks that the input asset types in the input dataTable exist in the system
   * 
   * @param dataTable
   */
  @Then("the following asset types shall exist in the system \\(p4)")
  public void the_following_asset_types_shall_exist_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
      String name = row.get("name");
      int lifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
      // Checks that the assetType object with the input name exists with the corresponding lifespan
      boolean contained = AssetType.hasWithName(name)
          && AssetType.getWithName(name).getExpectedLifeSpan() == lifeSpan
          && AssetType.getWithName(name).getAssetPlus() == assetPlus;
      assertTrue(contained);
    }
  }
}
