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

    List<IncomeStatementRecord> getIncomeStatement(String symbol){
        List<IncomeStatementRecord> records = companyMapper.getIncomeStatements(symbol);
        if(records.isEmpty()){
           records = remoteDataHandler.incomeStatement(symbol);
           if(!records.isEmpty()){
                companyMapper.save(records);
           }
        }
        return records;
    }
}
