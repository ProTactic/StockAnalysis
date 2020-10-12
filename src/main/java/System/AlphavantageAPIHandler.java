package System;

import System.Records.BalanceSheetRecord;
import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.IncomeStatementRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.sun.istack.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AlphavantageAPIHandler extends RemoteDataHandler {

    private final String API_EMPTY_QUERY = "https://www.alphavantage.co/query?";

    private String API_KEY;

    private HttpClient websiteAPI;

    AlphavantageAPIHandler(@NotNull String apiKey){
        websiteAPI = HttpClient.newHttpClient();
        API_KEY = apiKey;
    }

    /**
     * Build a class instance
     * @param apiKey the api key
     * @return null if the api key is valid
     */
    public static AlphavantageAPIHandler buildAlphavantageAPIHandler(@NotNull String apiKey){
        AlphavantageAPIHandler apiHandler = new AlphavantageAPIHandler(apiKey);
        if(!apiHandler.isValidKey(apiKey)){
            return null;
        }
        return apiHandler;
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
            if(isErrorResponse(response.body())){
                return null;
            }

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
            if(isErrorResponse(response.body())){
                return null;
            }

            return recordBuilder.financialStatement(response.body(), classType);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public boolean isValidKey(@NotNull String apiKey){
        RestTemplate restTemplate = new RestTemplate(API_EMPTY_QUERY)
                .addKeyValue("function", "INCOME_STATEMENT")
                .addKeyValue("symbol", "DISCA")
                .addKeyValue("apikey", API_KEY);
        HttpRequest request = buildRequest(restTemplate.getRestUrl());

        try {
            HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
            return isErrorResponse(response.body());
        } catch (IOException | InterruptedException e) {
            return false;
        }

    }

    public boolean isErrorResponse(String responseBody){
        Gson gson = new GsonBuilder().create();
        JsonElement element;
        element = gson.fromJson(responseBody, JsonElement.class);
        if(element.getAsJsonObject().getAsJsonPrimitive("Information").getAsString() == null){
            return false;
        }
        return true;

    }

    private HttpRequest buildRequest(String url){
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
    }

    public String getAPI_KEY() {
        return API_KEY;
    }
}
