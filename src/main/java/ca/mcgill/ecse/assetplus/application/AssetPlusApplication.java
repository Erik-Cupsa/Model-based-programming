package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import javafx.application.Application;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller.*;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller.*;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller.*;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller.*;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller.*;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller.*;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller.*;
import static ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller.*;
import static ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;


public class AssetPlusApplication {

  private static AssetPlus assetPlus;

  public static void main(String[] args) {
    // TODO Start the application user interface here
    assetPlus=getAssetPlus();

    //MaintenanceTicketWorkController.assignStaffToTicket(2,"jeff@ap.com", MaintenanceTicket.TimeEstimate.LessThanADay, MaintenanceTicket.PriorityLevel.Normal,false);
    Application.launch(AssetPlusFxmlView.class, args);
  }

  public static AssetPlus getAssetPlus() {
    if (assetPlus == null) {
      // these attributes are default, you should set them later with the setters
      assetPlus = AssetPlusPersistence.load();
      AssetPlusPersistence.save();
    }
    return assetPlus;
  }

}
