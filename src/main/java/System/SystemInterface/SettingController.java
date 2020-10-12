package System.SystemInterface;

import System.StcokSystemManager;


public class SettingController {

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
        return StcokSystemManager.saveOrUpdateAPIKey(keySupplier, key);
    }
}

