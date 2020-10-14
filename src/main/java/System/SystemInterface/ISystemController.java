package System.SystemInterface;

import java.util.List;

public interface ISystemController {

    /**
     * Return the company overview data
     * @param symbol the symbol of the company
     * @return {@code Result<CompanyOverviewDTO>} if symbol exists, otherwise return {@code null}
     */
    Result<CompanyOverviewDTO> getCompanyOverview(String symbol);

    /**
     * Return the last 5 year of a company income statement annually
     * @param symbol the symbol of the company
     * @return {@code Result<List<IncomeStatementDTO>>} if symbol exists, otherwise return {@code null}
     */
    Result<List<? extends FinancialDTO>> getLastIncomeStatements(String symbol);

    /**
     * Return the last 5 year of a company balance sheet statement annually
     * @param symbol the symbol of the company
     * @return {@code Result<List<BalanceSheetDTO>>} if symbol exists, otherwise return {@code null}
     */
    Result<List<? extends FinancialDTO>> getLastBalanceSheets(String symbol);

    /**
     * Return the last 5 year of a company cash flow statement annually
     * @param symbol the symbol of the company
     * @return {@code Result<List<CashFlowDTO>>} if symbol exists, otherwise return {@code null}
     */
    Result<List<? extends FinancialDTO>> getLastCashFlows(String symbol);

}
