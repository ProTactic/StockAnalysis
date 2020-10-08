package System;

import System.Records.CompanyFinancialRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.RecordBuilder;
import com.google.gson.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlphavantageRecordBuilder implements RecordBuilder {

    private final SimpleDateFormat dateFormat;

    AlphavantageRecordBuilder(){
        dateFormat = new SimpleDateFormat("yyyy-dd-MM");
    }

    /**
     * get the data from the API as JSON format and return the
     * appropriate System.Records.CompanyOverviewRecord
     * @param data as JSON format
     * @return System.Records.CompanyOverviewRecord
     */
    @Override
    public CompanyOverviewRecord buildCompanyOverview(String data) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
        return gson.fromJson(data, CompanyOverviewRecord.class);
    }

    public <E extends CompanyFinancialRecord> List<E> financialStatement(String data, Class<E> classType) {
        Gson gson = new GsonBuilder().create();
        E record;
        List<E> records = new ArrayList<>();
        JsonObject json = gson.fromJson(data, JsonObject.class);
        Date statementDate = null;
        String symbol;

        symbol = json.get("symbol").getAsJsonPrimitive().getAsString();
        JsonArray annualReports = json.getAsJsonArray("annualReports");
        for ( JsonElement statement: annualReports ) {
            record = gson.fromJson(statement, classType);

            record.setSymbol(symbol);
            try {
                statementDate = dateFormat.parse(statement.getAsJsonObject().get("fiscalDateEnding")
                        .getAsJsonPrimitive().getAsString());
                record.setDate(statementDate);
                records.add(record);
            } catch (ParseException ignored) { }
        }
        return records;
    }
}
