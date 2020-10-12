package System;

import DAL.APIKeyMapper;
import DAL.CompanyMapper;
import System.Records.APIKey;
import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.SystemInterface.APIKeySupplier;
import Exceptions.StockSystemException;

import java.util.List;

public class StcokSystemManager {

    private static StcokSystemManager instance;

    private RemoteDataHandler remoteDataHandler;
    private CompanyMapper companyMapper;
    private APIKeyMapper apiKeyMapper;

    /********************************/
    /*********** Initialize *********/
    /********************************/

    StcokSystemManager() {
        companyMapper = new CompanyMapper();
        apiKeyMapper = APIKeyMapper.getInstance();
    }

    public static StcokSystemManager getInstance() throws StockSystemException {
        if(instance == null){
            instance = new StcokSystemManager();
        }

        initialize(instance);

        return instance;
    }

    private static void initialize(StcokSystemManager instance) throws StockSystemException {
        APIKeyMapper keyMapper = instance.getApiKeyMapper();
        APIKey apiKey = keyMapper.findById(APIKeySupplier.ALPHA_ADVANTAGE.name());
        if(apiKey == null){
            throw new StockSystemException(StockSystemException.SystemExceptionType.NOT_INITIALIZED_API_KEY);
        }

        RemoteDataHandler remoteDataHandler = AlphavantageAPIHandler.buildAlphavantageAPIHandler(apiKey.getKey());
        if(remoteDataHandler == null){
            throw new StockSystemException(StockSystemException.SystemExceptionType.NOT_VALID_API_KEY);
        }

        instance.setRemoteDataHandler(remoteDataHandler);
    }

    /********************************/
    /************ Business **********/
    /********************************/

    public CompanyOverviewRecord getCompanyOverview(String symbol){
        symbol = symbol.toUpperCase();
        CompanyOverviewRecord record = companyMapper.findById(symbol);
        if(record == null){
            record = remoteDataHandler.companyOverview(symbol);
            if(record != null){
                companyMapper.save(record);
            } else {
                return null;
            }
        }
        companyMapper.update(record);
        return record;
    }

    public <E extends CompanyFinancialRecord> List<E> getFinancialStatement(String symbol, Class<E> classType) {
        symbol = symbol.toUpperCase();
        List<E> records = companyMapper.getFinancialStatement(symbol, classType);
        if(records.isEmpty()){
            records = remoteDataHandler.financialStatement(symbol, classType);
            System.out.println(records);
            if(!records.isEmpty()){
                companyMapper.save(records);
            } else {
                return null;
            }
        }
        return records;
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

    /********************************/
    /************ Settings **********/
    /********************************/

    /**
     * Save or update an api key
     * @param keySupplier the api supplier
     * @param key api key
     * @return true if the key was updated or saved otherwise return false for invalid key
     */
    public static boolean saveOrUpdateAPIKey(APIKeySupplier keySupplier, String key){
        if(keySupplier == APIKeySupplier.ALPHA_ADVANTAGE){
            AlphavantageAPIHandler remoteDataHandler = AlphavantageAPIHandler.buildAlphavantageAPIHandler(key);
            if(remoteDataHandler == null){
                return false;
            }

            APIKeyMapper.getInstance().saveOrUpdate(new APIKey(keySupplier.name(), key));
            // update the existing remote if its the same and there is an instance
            // currently the same remote
            if(instance != null){
                instance.setRemoteDataHandler(remoteDataHandler);
            }
            return true;
        }
        return false;
    }
}
