package business;


import System.AlphavantageAPIHandler;
import System.Records.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class AlphaAdvantageTests {

    private AlphavantageAPIHandler apiHandler;
    private final String symbol = "DISCA";

    public AlphaAdvantageTests(){
        //TODO set the api key before testing
        apiHandler = AlphavantageAPIHandler.buildAlphavantageAPIHandler("");
    }

    @Test
    public void ConstructorWithNonValidALIKeyTest(){
        AlphavantageAPIHandler notValidApiHandler = AlphavantageAPIHandler.buildAlphavantageAPIHandler("demo");
        Assert.assertNull("The build method should return null api key is not valid", notValidApiHandler);
    }

    @Test
    public void companyOverviewAllFieldAreNotNullTest(){
        CompanyOverviewRecord record = apiHandler.companyOverview(symbol);

        Assert.assertNotNull(symbol+" is valid symbol therefore the record shouldnt be null", record);

        Field[] fields = CompanyOverviewRecord.class.getDeclaredFields();
        for (Field field: fields) {
            try {
                Assert.assertNotNull("Field with null value: "+field.getName() ,field.get(record));
            } catch (IllegalAccessException e) {
                Assert.fail("Error was throw in "+ field.getName() +" check");
            }
        }
    }

    @Test
    public void financialStatementsAreNotNullTest(){

        Assert.assertNotNull(symbol+"Income statement is null",
                apiHandler.financialStatement(symbol, IncomeStatementRecord.class));
        Assert.assertNotNull(symbol+"Balance sheet is null",
                apiHandler.financialStatement(symbol, BalanceSheetRecord.class));
        Assert.assertNotNull(symbol+"Cash flow is null",
                apiHandler.financialStatement(symbol, CashFlowRecord.class));
    }
}
