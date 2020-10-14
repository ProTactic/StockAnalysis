package System;

import DAL.APIKeyMapper;
import DAL.CompanyMapper;
import System.Records.APIKey;
import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.SystemInterface.APIKeySupplier;
import Exceptions.StockSystemException;
import System.SystemInterface.Result;

import java.util.List;

public class StockSystemManager {

    private static StockSystemManager instance;

    private RemoteDataHandler remoteDataHandler;
    private CompanyMapper companyMapper;
    private APIKeyMapper apiKeyMapper;

    /********************************/
    /*********** Initialize *********/
    /********************************/

    StockSystemManager() {
        companyMapper = new CompanyMapper();
        apiKeyMapper = APIKeyMapper.getInstance();
    }

    public static StockSystemManager getInstance() throws StockSystemException {
        if(instance == null){
            instance = new StockSystemManager();
        }

        initialize(instance);

        return instance;
    }

    private static void initialize(StockSystemManager instance) throws StockSystemException {
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

    public Result<CompanyOverviewRecord> getCompanyOverview(String symbol){
        symbol = symbol.toUpperCase();
        CompanyOverviewRecord record = companyMapper.findById(symbol);
        if(record == null){
            Result<CompanyOverviewRecord> recordResult = remoteDataHandler.companyOverview(symbol);
            if(recordResult.isValid()) {
                companyMapper.save(recordResult.getEntity());
                return recordResult;
            } else {
                return recordResult;
            }
        }
        return new Result<>(true, record);
    }

    public <E extends CompanyFinancialRecord> Result<List<E>> getFinancialStatement(String symbol, Class<E> classType) {
        symbol = symbol.toUpperCase();

        //CompanyOverview symbol is a foreign key in the financial statements
        Result<CompanyOverviewRecord> corResult = getCompanyOverview(symbol);
        if(corResult.isNotValid()){
            return new Result<>(false, null, corResult.getMessage());
        }

        List<E> records = companyMapper.getFinancialStatement(symbol, classType);
        if(records.isEmpty()){
            Result<List<E>> recordsResult = remoteDataHandler.financialStatement(symbol, classType);
            if(recordsResult.isValid()){
                companyMapper.save(recordsResult.getEntity());
                return new Result<>(true, recordsResult.getEntity());
            } else {
                return recordsResult;
            }
        }
        return new Result<>(true, records);
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
     * @return {@code true} if the key was updated or saved otherwise return false for invalid key
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
