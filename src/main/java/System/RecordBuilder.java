package System;

import java.util.List;

public interface RecordBuilder {
    CompanyOverviewRecord buildCompanyOverview(String data);
    List<IncomeStatementRecord> buildIncomeStatement(String data);
}
