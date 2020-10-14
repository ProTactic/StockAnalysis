package System.SystemInterface;

import Exceptions.StockSystemException;
import System.Records.BalanceSheetRecord;
import System.Records.CashFlowRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.IncomeStatementRecord;
import System.StockSystemManager;

import java.util.ArrayList;
import java.util.List;

public class SystemController implements ISystemController {
    private static SystemController instance;
    private StockSystemManager systemManager;

    SystemController(StockSystemManager systemManager) {
            this.systemManager = systemManager;

    }

    public static SystemController getInstance() throws StockSystemException {
        if(instance == null){
            StockSystemManager systemManager = StockSystemManager.getInstance();
            instance = new SystemController(systemManager);
        }
        return instance;
    }

    public Result<CompanyOverviewDTO> getCompanyOverview(String symbol){
        Result<CompanyOverviewRecord> corResult = systemManager.getCompanyOverview(symbol);

        if(!corResult.isValid()){
            return new Result<CompanyOverviewDTO>(false, null, corResult.getMessage());
        }

        return new Result<>(true, buildFromCompanyOverviewRecord(corResult.getEntity()));
    }

    public Result<List<? extends FinancialDTO>> getLastIncomeStatements(String symbol){
        Result<List<IncomeStatementRecord>> incomeStatements = systemManager.getFinancialStatement(symbol, IncomeStatementRecord.class);
        List<IncomeStatementDTO> incomeStatementDTOS = new ArrayList<>();

        if(incomeStatements.isNotValid()){
            return new Result<>(false, null, incomeStatements.getMessage());
        }

        for (IncomeStatementRecord incomeStatementRecord : incomeStatements.getEntity()) {
            incomeStatementDTOS.add(buildFromIncomeStatementRecord(symbol, incomeStatementRecord));
        }

        return new Result<>(true, incomeStatementDTOS);
    }

    public Result<List<? extends FinancialDTO>> getLastBalanceSheets(String symbol){
        Result<List<BalanceSheetRecord>> balanceSheets = systemManager.getFinancialStatement(symbol, BalanceSheetRecord.class);
        List<BalanceSheetDTO> balanceSheetDTOS = new ArrayList<>();

        if(balanceSheets.isNotValid()){
            return new Result<>(false, null, balanceSheets.getMessage());
        }

        for (BalanceSheetRecord balanceSheetRecord : balanceSheets.getEntity()) {
            balanceSheetDTOS.add(buildFromBalanceSheetRecord(symbol, balanceSheetRecord));
        }

        return new Result<>(true, balanceSheetDTOS);
    }

    public Result<List<? extends FinancialDTO>> getLastCashFlows(String symbol){
        Result<List<CashFlowRecord>> cashFlowRecords = systemManager.getFinancialStatement(symbol, CashFlowRecord.class);
        List<CashFlowDTO> cashFlowDTOS = new ArrayList<>();

        if(cashFlowRecords.isNotValid()){
            return new Result<>(false, null, cashFlowRecords.getMessage());
        }

        for (CashFlowRecord cashFlowRecord : cashFlowRecords.getEntity()) {
            cashFlowDTOS.add(buildFromCashFlowRecord(symbol, cashFlowRecord));
        }
        return new Result<>(true, cashFlowDTOS);
    }

    /***********************************/
    /******* Build record to DTO *******/
    /***********************************/

    private static CompanyOverviewDTO buildFromCompanyOverviewRecord(CompanyOverviewRecord cor){
        return new CompanyOverviewDTO(cor.getSymbol(), cor.getName(), cor.getExchange(), cor.getCurrency(),
                                    cor.getCountry(), cor.getSector(), cor.getMarketCapitalization(),
                                    cor.getPERatio(), cor.getBookValue(), cor.getPriceToBookRatio(),
                                    cor.getSharesOutstanding());
    }

    private static IncomeStatementDTO buildFromIncomeStatementRecord(String symbol, IncomeStatementRecord incomeStatement){
        return new IncomeStatementDTO(symbol, incomeStatement.getDate(), incomeStatement.getTotalRevenue(),
                                    incomeStatement.getCostOfRevenue(), incomeStatement.getGrossProfit(), incomeStatement.getTotalOperatingExpense(),
                                    incomeStatement.getOperatingIncome(), incomeStatement.getNetIncome());
    }

    private static BalanceSheetDTO buildFromBalanceSheetRecord(String symbol, BalanceSheetRecord balanceSheet){
        return new BalanceSheetDTO(balanceSheet.getDate(),symbol, balanceSheet.getTotalAssets(),
                                    balanceSheet.getTotalCurrentAssets(), balanceSheet.getTotalNonCurrentAssets(),
                                    balanceSheet.getTotalLiabilities(), balanceSheet.getTotalCurrentLiabilities(),
                                    balanceSheet.getTotalNonCurrentLiabilities(), balanceSheet.getTotalShareholderEquity());
    }

    private static CashFlowDTO buildFromCashFlowRecord(String symbol, CashFlowRecord cashFlow){
        return new CashFlowDTO(cashFlow.getSymbol(), cashFlow.getDate(), cashFlow.getOperatingCashFlow(),
                                cashFlow.getInvestmentCashFlow(), cashFlow.getFinancingCashFlow(),
                                cashFlow.getChangeInCash(), cashFlow.getCapitalExpenditures(),
                                cashFlow.getFreeCashFlow());
    }
}
