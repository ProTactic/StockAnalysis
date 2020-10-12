package System.SystemInterface;

import Exceptions.StockSystemException;
import System.Records.BalanceSheetRecord;
import System.Records.CashFlowRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.IncomeStatementRecord;
import System.StcokSystemManager;

import java.util.ArrayList;
import java.util.List;

public class SystemController {
    private static SystemController instance;
    private StcokSystemManager systemManager;

    SystemController(StcokSystemManager systemManager) {
            this.systemManager = systemManager;

    }

    public static SystemController getInstance() throws StockSystemException {
        if(instance == null){
            StcokSystemManager systemManager = StcokSystemManager.getInstance();
            instance = new SystemController(systemManager);
        }
        return instance;
    }

    public CompanyOverviewDTO getCompanyOverview(String symbol){
        CompanyOverviewRecord cor = systemManager.getCompanyOverview(symbol);

        if(cor == null){
            return null;
        }

        return buildFromCompanyOverviewRecord(cor);
    }

    public List<IncomeStatementDTO> getLastIncomeStatements(String symbol){
        List<IncomeStatementRecord> incomeStatements = systemManager.getFinancialStatement(symbol, IncomeStatementRecord.class);
        List<IncomeStatementDTO> incomeStatementDTOS = new ArrayList<>();

        if(incomeStatements == null){
            return null;
        }

        for (IncomeStatementRecord incomeStatementRecord : incomeStatements) {
            incomeStatementDTOS.add(buildFromIncomeStatementRecord(symbol, incomeStatementRecord));
        }

        return incomeStatementDTOS;
    }

    public List<BalanceSheetDTO> getLastBalanceSheets(String symbol){
        List<BalanceSheetRecord> balanceSheets = systemManager.getFinancialStatement(symbol, BalanceSheetRecord.class);
        List<BalanceSheetDTO> balanceSheetDTOS = new ArrayList<>();

        if(balanceSheets == null){
            return null;
        }

        for (BalanceSheetRecord balanceSheetRecord : balanceSheets) {
            balanceSheetDTOS.add(buildFromBalanceSheetRecord(symbol, balanceSheetRecord));
        }

        return balanceSheetDTOS;
    }

    public List<CashFlowDTO> getLastCashFlows(String symbol){
        List<CashFlowRecord> cashFlowRecords = systemManager.getFinancialStatement(symbol, CashFlowRecord.class);
        List<CashFlowDTO> cashFlowDTOS = new ArrayList<>();

        if(cashFlowRecords == null){
            return null;
        }

        for (CashFlowRecord cashFlowRecord : cashFlowRecords) {
            cashFlowDTOS.add(buildFromCashFlowRecord(symbol, cashFlowRecord));
        }
        return cashFlowDTOS;
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
