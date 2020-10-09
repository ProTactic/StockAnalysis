import System.SystemInterface.*;

import java.util.List;

public class Main {
    public static void main(String[] args){

        SystemController systemController = new SystemController();
        CompanyOverviewDTO record = systemController.getCompanyOverview("IBM");
        System.out.println("\n\n"+ record);
        List<IncomeStatementDTO> records1 = systemController.getLastIncomeStatements("IBM");
        System.out.println("\n\nIncome statement:\n"+ records1);
        List<BalanceSheetDTO> records = systemController.getLastBalanceSheets("IBM");
        System.out.println("\n\nBalance sheet:\n"+ records);
        List<CashFlowDTO> records2 = systemController.getLastCashFlows("IBM");
        System.out.println("\n\nCash flows:\n"+ records2);

        /*SettingController settingController = new SettingController();
        settingController.saveOrUpdateAPIKey(APIKeySupplier.ALPHA_ADVANTAGE, "");*/
    }
}