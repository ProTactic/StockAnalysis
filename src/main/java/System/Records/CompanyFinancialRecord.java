package System.Records;

import java.io.Serializable;
import java.util.Date;

public interface CompanyFinancialRecord {
    public abstract String getSymbol();
    public abstract void setSymbol(String symbol);

    public abstract Date getDate();
    public abstract void setDate(Date date);

    public class FinancialId implements Serializable {
        private Date date;
        private String companyOverviewRecord;

        public FinancialId() {
        }

        public FinancialId(Date data, String symbol) {
            this.date = data;
            this.companyOverviewRecord = symbol;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getCompanyOverviewRecord() {
            return companyOverviewRecord;
        }

        public void setCompanyOverviewRecord(String companyOverviewRecord) {
            this.companyOverviewRecord = companyOverviewRecord;
        }
    }
}
