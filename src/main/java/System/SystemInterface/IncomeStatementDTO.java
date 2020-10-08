package System.SystemInterface;

import java.util.Date;

public class IncomeStatementDTO extends BusinessDTO{

    public String symbol;
    public Date date;
    public Long totalRevenue;
    public Long costOfRevenue;
    public Long grossProfit;
    public Long totalOperatingExpense;
    public Long operatingIncome;
    public Long netIncome;

    public IncomeStatementDTO() {
    }

    public IncomeStatementDTO(String symbol, Date date, Long totalRevenue, Long costOfRevenue, Long grossProfit,
                              Long totalOperatingExpense, Long operatingIncome, Long netIncome) {
        this.symbol = symbol;
        this.date = new Date(date.getTime());
        this.totalRevenue = totalRevenue;
        this.costOfRevenue = costOfRevenue;
        this.grossProfit = grossProfit;
        this.totalOperatingExpense = totalOperatingExpense;
        this.operatingIncome = operatingIncome;
        this.netIncome = netIncome;
    }
}
