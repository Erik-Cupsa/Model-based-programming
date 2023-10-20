package ca.mcgill.ecse.assetplus.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateAssetStepDefinitions {
  private AssetPlus hotel = AssetPlusApplication.getAssetPlus();
  private String error;

  /**
   * @author Jules Delabrousse (@JulesDelab)
   * @author Deniz Emre (@denizemre03)
   * @param dataTable representing table of existing asset types (see
   *        ../resources/AddAndUpdateAsset.feature)
   */
  @Given("the following asset types exist in the system \\(p9)")
  public void the_following_asset_types_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> columns : rows) {
      new AssetType(columns.get("name"), Integer.parseInt(columns.get("expectedLifeSpan")), hotel);
    }
  }

  /**
   * @author Kaitlyn Pereira (@kaitlynp18)
   * @author Deniz Emre (@denizemre03)
   * @param dataTable representing table of existing assets (see
   *        ../resources/AddAndUpdateAsset.feature)
   */
  @Given("the following assets exist in the system \\(p9)")
  public void the_following_assets_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      int aAssetNumber = Integer.parseInt(row.get("assetNumber"));
      AssetType aAssetType = AssetType.getWithName(row.get("type"));
      Date aPurchaseDate = Date.valueOf(row.get("purchaseDate"));
      int aFloorNumber = Integer.parseInt(row.get("floorNumber"));
      int aRoomNumber = Integer.parseInt(row.get("roomNumber"));
      new SpecificAsset(aAssetNumber, aFloorNumber, aRoomNumber, aPurchaseDate, hotel, aAssetType);
    }
  }

  /**
   * @author Viviane-Laura Tain (@vivianeltain)
   * @author Deniz Emre (@denizemre03)
   * @param assetNum represents asset number to update
   * @param assetType represents asset type
   * @param purchaseDateString represents purchase date
   * @param floorNum represents floor number
   * @param roomNum represents room number
   */
  @When("the manager attempts to add a new asset to the system with asset number {string}, type {string}, purchase date {string}, floor number {string}, and room number {string} \\(p9)")
  public void the_manager_attempts_to_add_a_new_asset_to_the_system_with_asset_number_type_purchase_date_floor_number_and_room_number_p9(
      String assetNum, String assetType, String purchaseDateString, String floorNum,
      String roomNum) {

    int assetNumber = Integer.parseInt(assetNum);
    int floorNumber = Integer.parseInt(floorNum);
    int roomNumber = Integer.parseInt(roomNum);
    Date purchaseDate = Date.valueOf(purchaseDateString);

    error = AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber, roomNumber,
        purchaseDate, assetType);
  }

  /**
   * @author Kaitlyn Pereira (@kaitlynp18)
   * @author Ana Floarea (@anafloarea)
   * @param assetNum represents asset number to update
   * @param assetType represents asset type
   * @param purchaseDate represents purchase date
   * @param floorNum represents floor number
   * @param roomNum represents room number
   */
  @When("the manager attempts to update asset number {string} in the system with type {string}, purchaseDate {string}, floorNumber {string}, and roomNumber {string} \\(p9)")
  public void the_manager_attempts_to_update_asset_number_in_the_system_with_type_purchase_date_floor_number_and_room_number_p9(
      String assetNum, String assetType, String purchaseDate, String floorNum, String roomNum) {
    // get all the new parameters
    int assetNumber = Integer.parseInt(assetNum);
    int newFloorNumber = Integer.parseInt(floorNum);
    int newRoomNumber = Integer.parseInt(roomNum);
    Date newPurchaseDate = Date.valueOf(purchaseDate);
    String newAssetTypeName = assetType;
    // update the asset with new parameters and track possible error
    error = AssetPlusFeatureSet3Controller.updateSpecificAsset(assetNumber, newFloorNumber,
        newRoomNumber, newPurchaseDate, newAssetTypeName);
  }

  /**
   * @author Kaitlyn Pereira (@kaitlynp18)
   * @author Caroline Thom (@carolinethom02)
   * @param expectedNumOfAssets represents the expected number of assets in the system
   */
  @Then("the number of assets in the system shall be {string} \\(p9)")
  public void the_number_of_assets_in_the_system_shall_be_p9(String expectedNumOfAssets) {

    int numOfAssets = hotel.numberOfSpecificAssets();
    assertEquals(Integer.parseInt(expectedNumOfAssets), numOfAssets);
  }

  /**
   * @author Jules Delabrousse (@JulesDelab)
   * @author Caroline Thom (@carolinethom02)
   * @param dataTable representing table of supposedly existing assets (see
   *        ../resources/AddAndUpdateAsset.feature)
   */
  @Then("the following assets shall exist in the system \\(p9)")
  public void the_following_assets_shall_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      SpecificAsset currentAsset =
          SpecificAsset.getWithAssetNumber(Integer.parseInt(columns.get("assetNumber")));
      assertNotNull(currentAsset);
      assertEquals(AssetType.getWithName(columns.get("type")), currentAsset.getAssetType());
      assertEquals(Date.valueOf(columns.get("purchaseDate")), currentAsset.getPurchaseDate());
      assertEquals(Integer.parseInt(columns.get("floorNumber")), currentAsset.getFloorNumber());
      assertEquals(Integer.parseInt(columns.get("roomNumber")), currentAsset.getRoomNumber());
    }
  }

  /**
   * @author Ana Floarea (@anafloarea)
   * @author Viviane-Laura Tain (@vivianeltain)
   * @param dataTable
   */
  @Then("the following asset types shall exist in the system \\(p9)")
  public void the_following_assets_types_shall_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      AssetType actualAssetType = SpecificAsset
          .getWithAssetNumber(Integer.parseInt(columns.get("assetNumber"))).getAssetType();
      AssetType expectedAssetType = AssetType.getWithName(columns.get("type"));
      assertEquals(expectedAssetType.getName(), actualAssetType.getName());
      assertEquals(expectedAssetType.getExpectedLifeSpan(), actualAssetType.getExpectedLifeSpan());
    }
  }

  /**
   * @author Viviane-Laura Tain (@vivianeltain)
   * @author Caroline Thom (@carolinethom02)
   * @param expectedError
   */
  @Then("the error {string} shall be raised \\(p9)")
  public void the_error_shall_be_raised_p9(String expectedError) {

    assertEquals(expectedError, error);
  }

  /**
   * @author Jules Delabrousse (@JulesDelab)
   * @author Ana Floarea (@anafloarea)
   * @param assetType Asset Type of the compared asset
   * @param assetNumber Asset Number of the compared asset
   * @param purchaseDate Purchase Date of the compared asset
   * @param floorNumber Floor Number of the compared asset
   * @param roomNumber Room Number of the compared asset
   */
  @Then("the asset {string} with asset number {string}, purchase date {string}, floor number {string}, and room number {string} shall exist in the system \\(p9)")
  public void the_asset_with_asset_number_purchase_date_floor_number_and_room_number_shall_exist_in_the_system_p9(
      String assetType, String assetNumber, String purchaseDate, String floorNumber,
      String roomNumber) {

    SpecificAsset assetInSystem = SpecificAsset.getWithAssetNumber(Integer.parseInt(assetNumber));
    assertNotNull(assetInSystem);
    assertEquals(assetInSystem.getAssetType(), AssetType.getWithName(assetType));
    assertEquals(assetInSystem.getPurchaseDate(), Date.valueOf(purchaseDate));
    assertEquals(assetInSystem.getFloorNumber(), Integer.parseInt(floorNumber));
    assertEquals(assetInSystem.getRoomNumber(), Integer.parseInt(roomNumber));
  }
}
