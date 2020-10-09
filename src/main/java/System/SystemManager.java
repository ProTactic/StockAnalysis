package System;

import DAL.APIKeyMapper;
import DAL.CompanyMapper;
import System.Records.APIKey;
import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.SystemInterface.APIKeySupplier;

import java.util.List;

public class SystemManager {

    private static SystemManager instance;

    private RemoteDataHandler remoteDataHandler;
    private CompanyMapper companyMapper;
    private APIKeyMapper apiKeyMapper;

    /********************************/
    /*********** Initialize *********/
    /********************************/

    SystemManager () {
        companyMapper = new CompanyMapper();
        apiKeyMapper = APIKeyMapper.getInstance();
    }

    public static SystemManager getInstance(){
        if(instance == null){
            instance = new SystemManager();
        }

        if(!initialize(instance)){
            //TODO throw error or return indicator of some sort
            instance = null;
            return null;
        }

        return instance;
    }

    private static boolean initialize(SystemManager instance){
        APIKeyMapper keyMapper = instance.getApiKeyMapper();
        APIKey apiKey = keyMapper.findById(APIKeySupplier.ALPHA_ADVANTAGE.name());
        if(apiKey == null){
            return false;
        }

        RemoteDataHandler remoteDataHandler = new AlphavantageAPIHandler(apiKey.getKey());
        instance.setRemoteDataHandler(remoteDataHandler);

        return true;
    }

    /********************************/
    /************ Business **********/
    /********************************/

    public CompanyOverviewRecord getCompanyOverview(String symbol){
        CompanyOverviewRecord record = companyMapper.findById(symbol);
        if(record == null){
            record = remoteDataHandler.companyOverview(symbol);
            if(record != null){
                companyMapper.save(record);
            }
        }
        companyMapper.update(record);
        return record;
    }

    public <E extends CompanyFinancialRecord> List<E> getFinancialStatement(String symbol, Class<E> classType) {
        List<E> records = companyMapper.getFinancialStatement(symbol, classType);
        if(records.isEmpty()){
            records = remoteDataHandler.financialStatement(symbol, classType);
            System.out.println(records);
            if(!records.isEmpty()){
                companyMapper.save(records);
            }
        }
        return records;
    }

    public void saveOrUpdateAPIKey(String keySupplier, String key){
        apiKeyMapper.saveOrUpdate(new APIKey(keySupplier, key));
    }

    public void setRemoteDataHandler(RemoteDataHandler remoteDataHandler) {
        this.remoteDataHandler = remoteDataHandler;
    }

    private RemoteDataHandler getRemoteDataHandler() {
        return remoteDataHandler;
    }

    private CompanyMapper getCompanyMapper() {
        return companyMapper;
    }

    private APIKeyMapper getApiKeyMapper() {
        return apiKeyMapper;
    }
}
