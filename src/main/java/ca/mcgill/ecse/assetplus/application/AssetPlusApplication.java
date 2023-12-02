package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import javafx.application.Application;


public class AssetPlusApplication {

    private static AssetPlus assetPlus;

    public static void main(String[] args) {
        // TODO Start the application user interface here
        assetPlus = getAssetPlus();

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