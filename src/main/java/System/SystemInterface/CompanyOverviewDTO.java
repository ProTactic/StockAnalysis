package System.SystemInterface;

public class CompanyOverviewDTO extends AbstractDTO {
    public String symbol;
    public String name;
    public String exchange;
    public String currency;
    public String country;
    public String sector;

    /*** ratios ***/
    public Long marketCapitalization;
    public Float PERatio;
    public Float bookValue;
    public Float priceToBookRatio;
    public Long sharesOutstanding;

    protected CompanyOverviewDTO() {}

    public CompanyOverviewDTO(String symbol, String name, String exchange, String currency, String country,
                              String sector, Long marketCapitalization, Float PERatio, Float bookValue,
                              Float priceToBookRatio, Long sharesOutstanding) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.sector = sector;
        this.marketCapitalization = marketCapitalization;
        this.PERatio = PERatio;
        this.bookValue = bookValue;
        this.priceToBookRatio = priceToBookRatio;
        this.sharesOutstanding = sharesOutstanding;
    }
}
