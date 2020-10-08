package System.Records;

import java.util.Date;

public interface CompanyFinancialRecord {
    String getSymbol();
    void setSymbol(String symbol);

    Date getDate();
    void setDate(Date date);
}
