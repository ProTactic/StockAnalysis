package System.Records;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CashFlow")
public class CashFlowRecord extends Record implements CompanyFinancialRecord {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    public CompanyOverviewRecord companyOverviewRecord;
    @Id
    public Date date;

    public Long operatingCashFlow;
    public Long investmentCashFlow;
    public Long financingCashFlow;
    public Long changeInCash;
    public Long capitalExpenditures;
    public Long freeCashFlow;

    public CashFlowRecord() {
    }

    public CashFlowRecord(CompanyOverviewRecord companyOverviewRecord, Date date, Long operatingCashFlow, Long
                        investmentCashFlow, Long financingCashFlow, Long changeInCash, Long capitalExpenditures) {
        this.companyOverviewRecord = companyOverviewRecord;
        this.date = date;
        this.operatingCashFlow = operatingCashFlow;
        this.investmentCashFlow = investmentCashFlow;
        this.financingCashFlow = financingCashFlow;
        this.changeInCash = changeInCash;
        this.capitalExpenditures = capitalExpenditures;
        updateInferredVariables();
    }

    public CompanyOverviewRecord getCompanyOverviewRecord() {
        return companyOverviewRecord;
    }

    public void setCompanyOverviewRecord(CompanyOverviewRecord companyOverviewRecord) {
        this.companyOverviewRecord = companyOverviewRecord;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getOperatingCashFlow() {
        return operatingCashFlow;
    }

    public void setOperatingCashFlow(Long operatingCashFlow) {
        this.operatingCashFlow = operatingCashFlow;
    }

    public Long getInvestmentCashFlow() {
        return investmentCashFlow;
    }

    public void setInvestmentCashFlow(Long investmentCashFlow) {
        this.investmentCashFlow = investmentCashFlow;
    }

    public Long getFinancingCashFlow() {
        return financingCashFlow;
    }

    public void setFinancingCashFlow(Long financingCashFlow) {
        this.financingCashFlow = financingCashFlow;
    }

    public Long getChangeInCash() {
        return changeInCash;
    }

    public void setChangeInCash(Long changeInCash) {
        this.changeInCash = changeInCash;
    }

    public Long getCapitalExpenditures() {
        return capitalExpenditures;
    }

    public void setCapitalExpenditures(Long capitalExpenditures) {
        this.capitalExpenditures = capitalExpenditures;
    }

    public Long getFreeCashFlow() {
        return freeCashFlow;
    }

    public void setFreeCashFlow(Long freeCashFlow) {
        this.freeCashFlow = freeCashFlow;
    }

    public void updateInferredVariables(){
        this.freeCashFlow = operatingCashFlow - capitalExpenditures;
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
