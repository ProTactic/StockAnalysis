package System.SystemInterface;

import java.util.Date;

public class CashFlowDTO extends FinancialDTO {

    public Long operatingCashFlow;
    public Long investmentCashFlow;
    public Long financingCashFlow;
    public Long changeInCash;
    public Long capitalExpenditures;
    public Long freeCashFlow;

    public CashFlowDTO(String symbol, Date date, Long operatingCashFlow, Long investmentCashFlow,
                       Long financingCashFlow, Long changeInCash, Long capitalExpenditures, Long freeCashFlow) {
        super();
        this.symbol = symbol;
        this.date = new Date(date.getTime());
        this.operatingCashFlow = operatingCashFlow;
        this.investmentCashFlow = investmentCashFlow;
        this.financingCashFlow = financingCashFlow;
        this.changeInCash = changeInCash;
        this.capitalExpenditures = capitalExpenditures;
        this.freeCashFlow = freeCashFlow;
    }
}
