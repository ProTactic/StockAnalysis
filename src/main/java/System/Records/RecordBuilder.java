package System.Records;

import java.util.List;

public interface RecordBuilder {
    CompanyOverviewRecord buildCompanyOverview(String data);
    /*List<IncomeStatementRecord> buildIncomeStatement(String data);
    List<BalanceSheetRecord> buildBalanceSheet(String data);*/
    public <E extends CompanyFinancialRecord> List<E> financialStatement(String data, Class<E> classType);
}
