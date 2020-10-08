package System.SystemInterface;

import java.util.Date;

public class BalanceSheetDTO extends AbstractDTO {
    public Date date;
    public String symbol;

    public Long totalAssets;
    public Long totalCurrentAssets;
    public Long totalNonCurrentAssets;

    public Long totalLiabilities;
    public Long totalCurrentLiabilities;
    public Long totalNonCurrentLiabilities;

    public Long totalShareholderEquity;

    public BalanceSheetDTO() { }

    public BalanceSheetDTO(Date date, String symbol, Long totalAssets, Long totalCurrentAssets, Long totalNonCurrentAssets,
                           Long totalLiabilities, Long totalCurrentLiabilities, Long totalNonCurrentLiabilities, Long totalShareholderEquity) {
        this.date = new Date(date.getTime());
        this.symbol = symbol;
        this.totalAssets = totalAssets;
        this.totalCurrentAssets = totalCurrentAssets;
        this.totalNonCurrentAssets = totalNonCurrentAssets;
        this.totalLiabilities = totalLiabilities;
        this.totalCurrentLiabilities = totalCurrentLiabilities;
        this.totalNonCurrentLiabilities = totalNonCurrentLiabilities;
        this.totalShareholderEquity = totalShareholderEquity;
    }
}
