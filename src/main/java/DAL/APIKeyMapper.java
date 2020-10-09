package DAL;

import System.Records.APIKey;

public class APIKeyMapper extends GeneralMapper<APIKey, String> {

    private static APIKeyMapper instance;

    APIKeyMapper(Class<APIKey> classType) {
        super(classType);
    }

    public static APIKeyMapper getInstance(){
        if(instance == null){
            instance = new APIKeyMapper(APIKey.class);
        }
        return instance;
    }

    public void saveOrUpdate(APIKey apiKey){
        currentSession.beginTransaction();
        currentSession.merge(apiKey);
        currentSession.getTransaction().commit();
    }
}
