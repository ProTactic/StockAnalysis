package System;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
}
