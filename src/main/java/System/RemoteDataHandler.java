package System;

public abstract class RemoteDataHandler {

    protected RecordBuilder recordBuilder;

    RemoteDataHandler(){
        createRecordBuilder();
    }

    /**
     * Get the overview data (name, industry etc) of the company
     * represented by the ticker symbol
     * @param tickerSymbol Ticker symbol of the stock
     * @return Overview data for the company
     */
    public abstract CompanyOverviewRecord companyOverview(String tickerSymbol);

    /**
     * Set the recordBuilder to the appropriate class
     */
    protected abstract void createRecordBuilder();
}
