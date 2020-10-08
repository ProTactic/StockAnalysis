package System.SystemInterface;

public class CompanyOverviewDTO extends BusinessDTO{
    public String symbol;
    public String name;
    public String exchange;
    public String currency;
    public String country;
    public String sector;

    protected CompanyOverviewDTO() {}

    public CompanyOverviewDTO(String symbol, String name, String exchange, String currency, String country, String sector) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.sector = sector;
    }
}
