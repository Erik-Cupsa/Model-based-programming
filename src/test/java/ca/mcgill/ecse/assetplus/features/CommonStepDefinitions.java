package ca.mcgill.ecse.assetplus.features;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CommonStepDefinitions {
  /**
   * Method used to delete the current bikeSafePlus system instance before the next test. This is
   * effective for all scenerios in all feature files
   */
  @Before
  public void setup() {
    AssetPlusApplication.getAssetPlus().delete();
  }

  @After
  public void tearDown() {
    AssetPlusApplication.getAssetPlus().delete();
  }
}
