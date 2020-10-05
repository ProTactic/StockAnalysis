package System;

import java.util.Date;

public class IncomeStatementRecord extends Record {
    public String symbol;
    public Date date;
    public Long totalRevenue;
    public Long costOfRevenue;
    public Long grossProfit;
    public Long totalOperatingExpense;
    public Long operatingIncome;
    public Long netIncome;

    public IncomeStatementRecord() {
    }

    public IncomeStatementRecord(String symbol, Date date, Long totalRevenue, Long costOfRevenue,
                                 Long grossProfit, Long totalOperatingExpense, Long operatingIncome, Long netIncome) {
        this.symbol = symbol;
        this.date = date;
        this.totalRevenue = totalRevenue;
        this.costOfRevenue = costOfRevenue;
        this.grossProfit = grossProfit;
        this.totalOperatingExpense = totalOperatingExpense;
        this.operatingIncome = operatingIncome;
        this.netIncome = netIncome;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getCostOfRevenue() {
        return costOfRevenue;
    }

    public void setCostOfRevenue(Long costOfRevenue) {
        this.costOfRevenue = costOfRevenue;
    }

    public Long getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Long grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Long getTotalOperatingExpense() {
        return totalOperatingExpense;
    }

    public void setTotalOperatingExpense(Long totalOperatingExpense) {
        this.totalOperatingExpense = totalOperatingExpense;
    }

    public Long getOperatingIncome() {
        return operatingIncome;
    }

    public void setOperatingIncome(Long operatingIncome) {
        this.operatingIncome = operatingIncome;
    }

    public Long getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(Long netIncome) {
        this.netIncome = netIncome;
    }
}
