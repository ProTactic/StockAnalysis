package System;

import DAL.APIKeyMapper;
import System.Records.APIKey;

public class SystemSettingManager {
    private APIKeyMapper apiKeyMapper;

    public SystemSettingManager(){
        apiKeyMapper = APIKeyMapper.getInstance();
    }

    public void saveOrUpdateAPIKey(String keySupplier, String key){
        apiKeyMapper.saveOrUpdate(new APIKey(keySupplier, key));
    }
}
