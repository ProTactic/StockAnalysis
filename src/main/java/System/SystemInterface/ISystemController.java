package System.SystemInterface;

import System.Records.BalanceSheetRecord;
import System.Records.CashFlowRecord;
import System.Records.CompanyOverviewRecord;
import System.Records.IncomeStatementRecord;

import java.util.ArrayList;
import java.util.List;

public interface ISystemController {

    /**
     * Return the company overview data
     * @param symbol the symbol of the company
     * @return {@code CompanyOverviewDTO} if symbol exists, otherwise return {@code null}
     */
    CompanyOverviewDTO getCompanyOverview(String symbol);

    /**
     * Return the last 5 year of a company income statement annually
     * @param symbol the symbol of the company
     * @return {@code List<IncomeStatementDTO>} if symbol exists, otherwise return {@code null}
     */
    List<IncomeStatementDTO> getLastIncomeStatements(String symbol);

    /**
     * Return the last 5 year of a company balance sheet statement annually
     * @param symbol the symbol of the company
     * @return {@code List<BalanceSheetDTO>} if symbol exists, otherwise return {@code null}
     */
    List<BalanceSheetDTO> getLastBalanceSheets(String symbol);

    /**
     * Return the last 5 year of a company cash flow statement annually
     * @param symbol the symbol of the company
     * @return {@code List<CashFlowDTO>} if symbol exists, otherwise return {@code null}
     */
    List<CashFlowDTO> getLastCashFlows(String symbol);

}
