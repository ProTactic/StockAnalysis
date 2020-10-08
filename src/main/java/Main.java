import System.SystemInterface.BalanceSheetDTO;
import System.SystemInterface.CompanyOverviewDTO;
import System.SystemInterface.IncomeStatementDTO;
import System.SystemInterface.SystemController;

import java.util.List;

public class Main {
    public static void main(String[] args){

        SystemController systemController = new SystemController();
        CompanyOverviewDTO record = systemController.getCompanyOverview("IBM");
        System.out.println(record);
        List<IncomeStatementDTO> records1 = systemController.getLastIncomeStatements("IBM");
        System.out.println(records1);
        List<BalanceSheetDTO> records = systemController.getLastBalanceSheets("IBM");
        System.out.println(records);
    }
}