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

    /*
        @Override
        public List<IncomeStatementRecord> incomeStatement(String tickerSymbol) {
            RestTemplate restTemplate = new RestTemplate(API_EMPTY_QUERY)
                    .addKeyValue("function", "INCOME_STATEMENT")
                    .addKeyValue("symbol", tickerSymbol)
                    .addKeyValue("apikey", API_KEY);

            HttpRequest request = buildRequest(restTemplate.getRestUrl());

            try {
                HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
                return recordBuilder.financialStatement(response.body(), IncomeStatementRecord.class);
            } catch (IOException | InterruptedException e) {
                return null;
            }
        }

        @Override
        public List<BalanceSheetRecord> balanceSheet(String tickerSymbol) {
            RestTemplate restTemplate = new RestTemplate(API_EMPTY_QUERY)
                    .addKeyValue("function", "BALANCE_SHEET")
                    .addKeyValue("symbol", tickerSymbol)
                    .addKeyValue("apikey", API_KEY);

            HttpRequest request = buildRequest(restTemplate.getRestUrl());

            try {
                HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("\n\n" + response.body() + "\n\n");
                return recordBuilder.financialStatement(response.body(), BalanceSheetRecord.class);
            } catch (IOException | InterruptedException e) {
                return null;
            }
        }
    */
    @Override
    public <E extends CompanyFinancialRecord> List<E> financialStatement(String tickerSymbol, Class<E> classType) {
        String functionName = "";
        if (classType.equals(IncomeStatementRecord.class)) {
            functionName = "INCOME_STATEMENT";
        } else {
            functionName = "BALANCE_SHEET";
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
