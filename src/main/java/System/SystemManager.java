package System;

import DAL.CompanyMapper;

public class SystemManager {

    private RemoteDataHandler remoteDataHandler;
    private CompanyMapper companyMapper;
    public SystemManager () {
        remoteDataHandler = new AlphavantageAPIHandler();
        companyMapper = new CompanyMapper();
    }

    CompanyOverviewRecord getCompanyOverview(String symbol){
        CompanyOverviewRecord record = companyMapper.findBySymbol(symbol);
        if(record == null){
            record = remoteDataHandler.companyOverview(symbol);
            if(record != null){
                companyMapper.save(record);
            }
        }
        return record;
    }
}
