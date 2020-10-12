package System.SystemInterface;

import System.StockSystemManager;


public class SettingController implements ISettingController {

    private static SettingController instance;

    SettingController() {

    }

    public static SettingController getInstance() {
        if(instance == null){
            instance = new SettingController();
        }
        return instance;
    }

    public boolean saveOrUpdateAPIKey(APIKeySupplier keySupplier, String key){
        return StockSystemManager.saveOrUpdateAPIKey(keySupplier, key);
    }
}

