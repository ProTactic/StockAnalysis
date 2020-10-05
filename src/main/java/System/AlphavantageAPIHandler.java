package System;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AlphavantageAPIHandler extends RemoteDataHandler {

    final String API_EMPTY_QUERY = "https://www.alphavantage.co/query?";
    //TODO set your key
    final String API_KEY = "DHGQ9VIBRHBR51ZW";

    private HttpClient websiteAPI;

    public AlphavantageAPIHandler(){
        websiteAPI = HttpClient.newHttpClient();
    }

    @Override
    protected void createRecordBuilder() {
        recordBuilder = new AlphavantageRecordBuilder();
    }

    public CompanyOverviewRecord companyOverview(String tickerSymbol) {
        RestTemplate restTemplate = new RestTemplate(API_EMPTY_QUERY)
                .addKeyValue("function", "OVERVIEW")
                .addKeyValue("symbol", tickerSymbol)
                .addKeyValue("apikey", API_KEY);

        HttpRequest request = buildRequest(restTemplate.getRestUrl());

        try {
            HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
            return recordBuilder.buildCompanyOverview(response.body());
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    @Override
    public List<IncomeStatementRecord> incomeStatement(String tickerSymbol) {
        RestTemplate restTemplate = new RestTemplate(API_EMPTY_QUERY)
                .addKeyValue("function", "INCOME_STATEMENT")
                .addKeyValue("symbol", tickerSymbol)
                .addKeyValue("apikey", API_KEY);

        HttpRequest request = buildRequest(restTemplate.getRestUrl());

        try {
            HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
            return recordBuilder.buildIncomeStatement(response.body());
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private HttpRequest buildRequest(String url){
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
    }
}
