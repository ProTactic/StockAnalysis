package System;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AlphavantageAPIHandler extends RemoteDataHandler {

    final String API_EMPTY_QUERY = "https://www.alphavantage.co/query?";
    //TODO set your key
    final String API_KEY = "Set your key";

    private HttpClient websiteAPI;

    public AlphavantageAPIHandler(){
        websiteAPI = HttpClient.newHttpClient();
    }

    @Override
    protected void createRecordBuilder() {
        recordBuilder = new AlphavantageRecordBuilder();
    }

    public CompanyOverviewRecord companyOverview(String tickerSymbol) {
        String function = "OVERVIEW";
        String UrlRequest = String.format("%sfunction=%s&symbol=%s&apikey=%s",
                            API_EMPTY_QUERY, function, tickerSymbol, API_KEY);

        HttpRequest request = buildRequest(UrlRequest);

        try {
            HttpResponse<String> response = websiteAPI.send(request, HttpResponse.BodyHandlers.ofString());
            return recordBuilder.buildCompanyOverview(response.body());
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
