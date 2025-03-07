package System;

import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.RecordBuilder;
import System.SystemInterface.Result;

import java.util.List;

public abstract class RemoteDataHandler {

    protected RecordBuilder recordBuilder;

    RemoteDataHandler(){
        createRecordBuilder();
    }

    /**
     * Set the recordBuilder to the appropriate class
     */
    protected abstract void createRecordBuilder();

    /**
     * Get the overview data (name, industry etc) of the company
     * represented by the ticker symbol
     * @param tickerSymbol Ticker symbol of the stock
     * @return Overview data for the company
     */
    public abstract Result<CompanyOverviewRecord> companyOverview(String tickerSymbol);

    /*public abstract List<IncomeStatementRecord> incomeStatement(String tickerSymbol);
    public abstract List<BalanceSheetRecord> balanceSheet(String tickerSymbol);*/
    public abstract <E extends CompanyFinancialRecord> Result<List<E>> financialStatement(String data, Class<E> classType);

}
