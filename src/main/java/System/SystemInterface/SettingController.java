package System.SystemInterface;

import System.SystemManager;

public class SettingController {

    private SystemManager systemManager;

    public SettingController(){
        systemManager = SystemManager.getInstance();
    }

    public void saveOrUpdateAPIKey(APIKeySupplier keySupplier, String key){
        systemManager.saveOrUpdateAPIKey(keySupplier.name(), key);
    }
}

