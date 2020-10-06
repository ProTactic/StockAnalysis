package System;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CompanyOverview")
public class CompanyOverviewRecord extends Record {
    public String symbol;
    public String name;
    public String exchange;
    public String currency;
    public String country;
    public String sector;

    protected CompanyOverviewRecord() {}

    public CompanyOverviewRecord(String symbol, String name, String exchange, String currency, String country, String sector) {
        this.setSymbol(symbol);
        this.setName(name);
        this.setExchange(exchange);
        this.setCurrency(currency);
        this.setCountry(country);
        this.setSector(sector);
    }

    @Id
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
