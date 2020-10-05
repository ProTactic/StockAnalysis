package System;

import com.google.gson.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlphavantageRecordBuilder implements RecordBuilder {

    private final Gson gson;

    AlphavantageRecordBuilder(){
        gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();
    }

    /**
     * get the data from the API as JSON format and return the
     * appropriate System.CompanyOverviewRecord
     * @param data as JSON format
     * @return System.CompanyOverviewRecord
     */
    @Override
    public CompanyOverviewRecord buildCompanyOverview(String data) {
        return gson.fromJson(data, CompanyOverviewRecord.class);
    }

    public List<IncomeStatementRecord> buildIncomeStatement(String data) {
        Gson gson = new GsonBuilder().create();
        IncomeStatementRecord record = new IncomeStatementRecord();
        List<IncomeStatementRecord> records = new ArrayList<>();
        JsonObject json = gson.fromJson(data, JsonObject.class);

        record.setSymbol(json.get("symbol").getAsJsonPrimitive().getAsString());
        JsonArray annualReports = json.getAsJsonArray("annualReports");

        for ( JsonElement statement: annualReports ) {
            record = gson.fromJson(statement, IncomeStatementRecord.class);

            Date statementDate = null;
            try {
                statementDate = new SimpleDateFormat("yyyy-MM-dd")
                                        .parse(statement.getAsJsonObject().get("fiscalDateEnding")
                                                .getAsJsonPrimitive().getAsString());
            } catch (ParseException ignored) { }

            record.setDate(statementDate);
            records.add(record);
        }

        return records;
    }
}
