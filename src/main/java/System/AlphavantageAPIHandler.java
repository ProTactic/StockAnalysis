package System;

import System.Records.BalanceSheetRecord;
import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.IncomeStatementRecord;

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
    public <E extends CompanyFinancialRecord> List<E> financialStatement(String tickerSymbol, Class<E> classType) {
        String functionName = "";
        if (classType.equals(IncomeStatementRecord.class)) {
            functionName = "INCOME_STATEMENT";
        } else {
            if(classType.equals(BalanceSheetRecord.class)) {
                functionName = "BALANCE_SHEET";
            } else {
                functionName = "CASH_FLOW";
            }
        }

        RestTemplate restTemplate = new RestTemplate(API_EMPTY_QUERY)
                .addKeyValue("function", functionName)
                .addKeyValue("symbol", tickerSymbol)
                .addKeyValue("apikey", API_KEY);

        HttpRequest request = buildRequest(restTemplate.getRestUrl());

        try {
            HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\n\n" + response.body() + "\n\n");
            return recordBuilder.financialStatement(response.body(), classType);
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
