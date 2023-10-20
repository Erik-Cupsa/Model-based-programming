package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * This class defines the Gherkin step defintions for the DeleteAsset feature.
 */

public class DeleteAssetStepDefinitions {


  /**
   * Gherkin step definition method to create and add asset types to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the asset type information.
   *
   * @author Julia B. Grenier
   * @author Tayba Jusab
   */
  @Given("the following asset types exist in the system \\(p12)")
  public void the_following_asset_types_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    // Turns the dataTable into a list of lists (each row becomes a list).
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified asset types and add it to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      String name = (row.get("name")).toString();
      int expectedLifeSpan = Integer.parseInt(row.get("expectedLifeSpan").toString());
      AssetType type = AssetPlusApplication.getAssetPlus().addAssetType(name, expectedLifeSpan);
      AssetPlusApplication.getAssetPlus().addAssetType(type);
    }
  }

  /**
   * Gherkin step definition method to create and add the specific assets to the AssetPlus
   * application.
   * 
   * @param dataTable Cucumber DataTable containing specific asset information.
   * 
   * @author Sahar Fathi
   * @author Anjali Singhal
   * @author Julia B. Grenier
   * @author Tayba Jusab
   * @author Camille Pouliot
   * @author Émilia Gagné
   */
  @Given("the following assets exist in the system \\(p12)")
  public void the_following_assets_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterate through each map representing a row and cast it to the appropriate type.
    for (Map<String, Object> row : tableList) {
      int assetNumber = Integer.parseInt(row.get("assetNumber").toString());
      String assetType = (row.get("type")).toString();
      int floorNumber = Integer.parseInt(row.get("floorNumber").toString());
      int roomNumber = Integer.parseInt(row.get("roomNumber").toString());
      Date purchaseDate = Date.valueOf(row.get("purchaseDate").toString());

      // Adding the specific asset based on the table information.
      SpecificAsset asset = AssetPlusApplication.getAssetPlus().addSpecificAsset(assetNumber,
          floorNumber, roomNumber, purchaseDate, AssetType.getWithName(assetType));
      AssetPlusApplication.getAssetPlus().addSpecificAsset(asset);
    }

  }

  /**
   * Gherkin step definition method to delete the specific asset specified by number from the
   * AssetPlus application.
   * 
   * @param assetNumber Specific asset number associated to the asset to be deleted.
   * 
   * @author Camille Pouliot
   * @author Émilia Gagné
   */
  @When("the manager attempts to delete the asset with number {assetNumber} \\(p12)")
  public void the_manager_attempts_to_delete_the_asset_with_number_p12(String assetNumber) {
    // Removes the specific asset based on the asset number given.
    AssetPlusFeatureSet3Controller.deleteSpecificAsset(Integer.parseInt(assetNumber));

  }

  /**
   * Gherkin step definition method to verify the amount of assets in the application following the
   * deletion of a specific asset.
   * 
   * @param expectedNumberOfAssets Expected number of assets after the specific asset has been
   *        deleted.
   * 
   * @author Sahar Fathi
   * @author Anjali Singhal
   */
  @Then("the number of assets in the system shall be {expectedNumberOfAssets} \\(p12)")
  public void the_number_of_assets_in_the_system_shall_be_p12(String expectedNumberOfAssets) {
    // Confirms that the amount of assets has gone down after remvoving an asset.
    assertEquals(Integer.parseInt(expectedNumberOfAssets),
        AssetPlusApplication.getAssetPlus().getSpecificAssets().size());
  }

  /**
   * Gherkin step definition method to verify that the correct assets still exist in the AssetPlus
   * application.
   * 
   * @param dataTable Cucumber DataTable containing specific asset information of the assets which
   *        should still be existing in the AssetPlus application.
   * @author Sahar Fathi
   * @author Anjali Singhal
   * @author Julia B. Grenier
   * @author Tayba Jusab
   * @author Camille Pouliot
   * @author Émilia Gagné
   */

  @Then("the following assets shall exist in the system \\(p12)")
  public void the_following_assets_shall_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    // Turns the data table into a list of HashMaps with the column name as the key.
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);
    // This loop is used to iterate through the table at the same time as the assets in the
    // application, in order
    // to assert that they are equals with respect to their fields. Application counter augments the
    // index of the application asset list.
    int applicationCounter = 0;
    for (Map<String, Object> row : tableList) {
      SpecificAsset asset =
          AssetPlusApplication.getAssetPlus().getSpecificAssets().get(applicationCounter);
      assertEquals(Integer.parseInt(row.get("assetNumber").toString()), asset.getAssetNumber());
      assertEquals(row.get("type").toString(), asset.getAssetType().getName());
      assertEquals(Integer.parseInt(row.get("floorNumber").toString()), asset.getFloorNumber());
      assertEquals(Integer.parseInt(row.get("roomNumber").toString()), asset.getRoomNumber());
      assertEquals(Date.valueOf(row.get("purchaseDate").toString()), asset.getPurchaseDate());
      applicationCounter++;
    }
  }



}
