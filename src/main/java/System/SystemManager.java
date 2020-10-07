package System;

import DAL.CompanyMapper;

import java.util.List;

public class SystemManager {
    private RemoteDataHandler remoteDataHandler;
    private CompanyMapper companyMapper;
    public SystemManager () {
        remoteDataHandler = new AlphavantageAPIHandler();
        companyMapper = new CompanyMapper();
    }

    CompanyOverviewRecord getCompanyOverview(String symbol){
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
}
