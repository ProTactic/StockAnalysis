package System;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BalanceSheet")
public class BalanceSheetRecord extends Record implements CompanyFinancialRecord{
    @Id
    public Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    public CompanyOverviewRecord companyOverviewRecord;

    public Long totalAssets;
    public Long totalCurrentAssets;
    public Long totalNonCurrentAssets;

    public Long totalLiabilities;
    public Long totalCurrentLiabilities;
    public Long totalNonCurrentLiabilities;

    public Long totalShareholderEquity;

    public BalanceSheetRecord() { }

    public BalanceSheetRecord(Date date, CompanyOverviewRecord companyOverviewRecord, Long totalAssets, Long totalCurrentAssets,
                              Long totalNonCurrentAssets, Long totalLiabilities, Long totalCurrentLiabilities, Long totalNonCurrentLiabilities,
                              Long totalShareholderEquity) {
        this.date = date;
        this.companyOverviewRecord = companyOverviewRecord;
        this.totalAssets = totalAssets;
        this.totalCurrentAssets = totalCurrentAssets;
        this.totalNonCurrentAssets = totalNonCurrentAssets;
        this.totalLiabilities = totalLiabilities;
        this.totalCurrentLiabilities = totalCurrentLiabilities;
        this.totalNonCurrentLiabilities = totalNonCurrentLiabilities;
        this.totalShareholderEquity = totalShareholderEquity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CompanyOverviewRecord getCompanyOverviewRecord() {
        return companyOverviewRecord;
    }

    public void setCompanyOverviewRecord(CompanyOverviewRecord companyOverviewRecord) {
        this.companyOverviewRecord = companyOverviewRecord;
    }

    public Long getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Long totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Long getTotalCurrentAssets() {
        return totalCurrentAssets;
    }

    public void setTotalCurrentAssets(Long totalCurrentAssets) {
        this.totalCurrentAssets = totalCurrentAssets;
    }

    public Long getTotalNonCurrentAssets() {
        return totalNonCurrentAssets;
    }

    public void setTotalNonCurrentAssets(Long totalNonCurrentAssets) {
        this.totalNonCurrentAssets = totalNonCurrentAssets;
    }

    public Long getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(Long totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Long getTotalCurrentLiabilities() {
        return totalCurrentLiabilities;
    }

    public void setTotalCurrentLiabilities(Long totalCurrentLiabilities) {
        this.totalCurrentLiabilities = totalCurrentLiabilities;
    }

    public Long getTotalNonCurrentLiabilities() {
        return totalNonCurrentLiabilities;
    }

    public void setTotalNonCurrentLiabilities(Long totalNonCurrentLiabilities) {
        this.totalNonCurrentLiabilities = totalNonCurrentLiabilities;
    }

    public Long getTotalShareholderEquity() {
        return totalShareholderEquity;
    }

    public void setTotalShareholderEquity(Long totalShareholderEquity) {
        this.totalShareholderEquity = totalShareholderEquity;
    }

    @Override
    public String getSymbol() {
        if(companyOverviewRecord != null){
            return companyOverviewRecord.symbol;
        }
        return null;
    }

    @Override
    public void setSymbol(String symbol) {
        if(companyOverviewRecord == null){
            companyOverviewRecord = new CompanyOverviewRecord();
        }
        companyOverviewRecord.setSymbol(symbol);
    }
}
