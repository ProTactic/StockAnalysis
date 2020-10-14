package System;

import System.Records.BalanceSheetRecord;
import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.IncomeStatementRecord;
import System.SystemInterface.Result;
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

    private final int NO_ERROR = 0;
    private final int ERROR_API_KEY_CODE = 1;
    private final int ERROR_API_REQUESTS = 2;

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

    public Result<CompanyOverviewRecord> companyOverview(String tickerSymbol) {
        RestTemplate restTemplate = new RestTemplate(API_EMPTY_QUERY)
                .addKeyValue("function", "OVERVIEW")
                .addKeyValue("symbol", tickerSymbol)
                .addKeyValue("apikey", API_KEY);

        HttpRequest request = buildRequest(restTemplate.getRestUrl());

        try {
            HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
            int errorCode = isErrorResponse(response.body());
            if(errorCode == NO_ERROR){
                return new Result<>(true, recordBuilder.buildCompanyOverview(response.body()));
            }

            return new Result<>(false, null, "This api can send 5 request pre minute");
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    @Override
    public <E extends CompanyFinancialRecord> Result<List<E>> financialStatement(String tickerSymbol, Class<E> classType) {
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
            int errorCode = isErrorResponse(response.body());
            if(errorCode == NO_ERROR){
                return new Result<>(true, recordBuilder.financialStatement(response.body(), classType));
            }

            return new Result<>(false, null, "This api can send 5 request pre minute");
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
            return isErrorResponse(response.body()) == NO_ERROR;
        } catch (IOException | InterruptedException e) {
            return false;
        }

    }

    public int isErrorResponse(String responseBody){
        Gson gson = new GsonBuilder().create();
        JsonElement element;
        element = gson.fromJson(responseBody, JsonElement.class);
        if(element.getAsJsonObject().size() == 0 || element.getAsJsonObject().has("Information")){
            return ERROR_API_KEY_CODE;
        }

        if(element.getAsJsonObject().has("Note")){
            return ERROR_API_REQUESTS;
        }

        return NO_ERROR;

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
