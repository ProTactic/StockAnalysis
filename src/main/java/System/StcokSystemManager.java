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

        RemoteDataHandler remoteDataHandler = new AlphavantageAPIHandler(apiKey.getKey());
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
}
