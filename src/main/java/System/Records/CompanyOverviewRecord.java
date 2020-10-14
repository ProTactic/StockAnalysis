package System.Records;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CompanyOverview")
public class CompanyOverviewRecord extends Record {
    @Id
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


    protected CompanyOverviewRecord() {}

    public CompanyOverviewRecord(String symbol, String name, String exchange, String currency,
                                 String country, String sector, Long marketCapitalization, Float PERatio,
                                 Float bookValue, Float priceToBookRatio, Long sharesOutstanding) {
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

    public Long getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(Long marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public Float getPERatio() {
        return PERatio;
    }

    public void setPERatio(Float PERatio) {
        this.PERatio = PERatio;
    }

    public Float getBookValue() {
        return bookValue;
    }

    public void setBookValue(Float bookValue) {
        this.bookValue = bookValue;
    }

    public Float getPriceToBookRatio() {
        return priceToBookRatio;
    }

    public void setPriceToBookRatio(Float priceToBookRatio) {
        this.priceToBookRatio = priceToBookRatio;
    }

    public Long getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(Long sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }
}
