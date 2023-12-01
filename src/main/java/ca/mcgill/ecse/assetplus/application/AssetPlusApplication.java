package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.controller.MaintenanceTicketWorkController;
import ca.mcgill.ecse.assetplus.model.*;
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

import java.sql.Date;
import java.util.Calendar;


public class AssetPlusApplication {

  private static AssetPlus assetPlus;

  public static void main(String[] args) {
    // TODO Start the application user interface here
    //assetPlus=getAssetPlus();

    assetPlus = createDemoData();


    MaintenanceTicketWorkController.assignStaffToTicket(2,"jeff@ap.com", MaintenanceTicket.TimeEstimate.LessThanADay, MaintenanceTicket.PriorityLevel.Normal,true);
    Application.launch(AssetPlusFxmlView.class, args);
  }

  private static AssetPlus createDemoData() {
    // root
    AssetPlus assetPlus = new AssetPlus();

    // manager
    Manager m = new Manager("manager@ap.com", "Jane", "manager", "514-444-5555", assetPlus);

    // employee
    Employee e1 = new Employee("e1@ap.com", "Mary", "pwd1", "514-444-5566", assetPlus);
    Employee e2 = new Employee("e2@ap.com", "Nathan", "pwd2", "514-444-5577", assetPlus);

    // guest
    Guest g = new Guest("g@google.com", "George", "secret", "514-888-9000", assetPlus);

    // asset type
    AssetType chair = new AssetType("chair", 3500, assetPlus);
    AssetType desk = new AssetType("desk", 4500, assetPlus);
    AssetType carpet = new AssetType("carpet", 6000, assetPlus);

    // asset
    Calendar calendar = Calendar.getInstance();
    calendar.set(2023, Calendar.JUNE, 1, 0, 0, 0);
    SpecificAsset chair1 = new SpecificAsset(1, 5, 501, new Date(calendar.getTimeInMillis()), assetPlus, chair);
    calendar.set(2023, Calendar.JULY, 10, 0, 0, 0);
    SpecificAsset desk1 = new SpecificAsset(2, 5, 501, new Date(calendar.getTimeInMillis()), assetPlus, desk);
    calendar.set(2023, Calendar.AUGUST, 5, 0, 0, 0);
    SpecificAsset carpet1 = new SpecificAsset(3, 5, -1, new Date(calendar.getTimeInMillis()), assetPlus, carpet);
    calendar.set(2023, Calendar.JUNE, 1, 0, 0, 0);
    new SpecificAsset(4, 5, 502, new Date(calendar.getTimeInMillis()), assetPlus, chair);
    calendar.set(2023, Calendar.JULY, 10, 0, 0, 0);
    SpecificAsset desk2 = new SpecificAsset(5, 5, 502, new Date(calendar.getTimeInMillis()), assetPlus, desk);

    // ticket
    calendar.set(2023, Calendar.NOVEMBER, 5, 0, 0, 0);
    MaintenanceTicket t1 = new MaintenanceTicket(1, new Date(calendar.getTimeInMillis()), "desk is scratched", assetPlus, e1);
    t1.setAsset(desk1);
    calendar.set(2023, Calendar.NOVEMBER, 6, 0, 0, 0);
    MaintenanceTicket t2 = new MaintenanceTicket(2, new Date(calendar.getTimeInMillis()), "chair wheel jammed", assetPlus, e2);
    t2.setAsset(chair1);
    calendar.set(2023, Calendar.NOVEMBER, 7, 0, 0, 0);
    MaintenanceTicket t3 = new MaintenanceTicket(3, new Date(calendar.getTimeInMillis()), "very noisy after 10pm", assetPlus, g);
    calendar.set(2023, Calendar.NOVEMBER, 8, 0, 0, 0);
    MaintenanceTicket t4 = new MaintenanceTicket(4, new Date(calendar.getTimeInMillis()), "desk is wobbly", assetPlus, e1);
    t4.setAsset(desk2);
    calendar.set(2023, Calendar.NOVEMBER, 9, 0, 0, 0);
    MaintenanceTicket t5 = new MaintenanceTicket(5, new Date(calendar.getTimeInMillis()), "carpet is stained", assetPlus, m);
    t5.setAsset(carpet1);

    // note
    calendar.set(2023, Calendar.NOVEMBER, 5, 0, 0, 0);
    new MaintenanceNote(new Date(calendar.getTimeInMillis()), "this is an easy issue to address", t1, e1);
    calendar.set(2023, Calendar.NOVEMBER, 6, 0, 0, 0);
    new MaintenanceNote(new Date(calendar.getTimeInMillis()), "wheels are on backorder", t2, e2);
    calendar.set(2023, Calendar.NOVEMBER, 7, 0, 0, 0);
    new MaintenanceNote(new Date(calendar.getTimeInMillis()), "cannot do much about it", t3, m);

    // image
    new TicketImage("https://img.freepik.com/premium-photo/green-scratched-metal-background-texture_88281-1787.jpg", t1);
    new TicketImage("https://img.freepik.com/free-photo/oxid-steel-texture_1194-8852.jpg", t5);

    return assetPlus;
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