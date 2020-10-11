import Exceptions.StockSystemException;
import System.SystemInterface.*;

import java.util.List;

public class Main {
    public static void main(String[] args){

        try {
            /*SettingController settingController = SettingController.getInstance();
            settingController.saveOrUpdateAPIKey(APIKeySupplier.ALPHA_ADVANTAGE, "");*/
            SystemController systemController = SystemController.getInstance();
            CompanyOverviewDTO record = systemController.getCompanyOverview("ibm");
            System.out.println("\n\n" + record);
            List<IncomeStatementDTO> records1 = systemController.getLastIncomeStatements("ibm");
            System.out.println("\n\nIncome statement:\n" + records1);
            List<BalanceSheetDTO> records = systemController.getLastBalanceSheets("IBM");
            System.out.println("\n\nBalance sheet:\n" + records);
            List<CashFlowDTO> records2 = systemController.getLastCashFlows("IBM");
            System.out.println("\n\nCash flows:\n" + records2);
        } catch (StockSystemException e){
            System.out.println(e.getMessage());
        }
    }
}