package System;

public class CompanyOverviewRecord extends Record {
    public Long id;
    public String symbol;
    public String name;
    public String exchange;
    public String currency;
    public String country;
    public String sector;

    protected CompanyOverviewRecord() {}

    public CompanyOverviewRecord(Long id, String symbol, String name, String exchange, String currency, String country, String sector) {
        this.setId(id);
        this.setSymbol(symbol);
        this.setName(name);
        this.setExchange(exchange);
        this.setCurrency(currency);
        this.setCountry(country);
        this.setSector(sector);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
