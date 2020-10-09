package System.SystemInterface;

import System.SystemSettingManager;

public class SettingController {

    private static SettingController instance;
    private SystemSettingManager systemSettingManager;

    SettingController() {
        this.systemSettingManager = new SystemSettingManager();
    }

    public static SettingController getInstance() {
        if(instance == null){
            instance = new SettingController();
        }
        return instance;
    }


    public void saveOrUpdateAPIKey(APIKeySupplier keySupplier, String key){
        systemSettingManager.saveOrUpdateAPIKey(keySupplier.name(), key);
    }
}

