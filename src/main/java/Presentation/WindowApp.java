package Presentation;

import Exceptions.StockSystemException;
import System.SystemInterface.SettingController;
import System.SystemInterface.SystemController;

public class WindowApp {

    private SystemController systemController;
    private SettingController settingController;

    public WindowApp() {
        settingController = SettingController.getInstance();
    }

    public void run() {
       try {
           runMainWindow();
        } catch (StockSystemException e) {
           APIKeySetupDialog apiKeySetupDialog = new APIKeySetupDialog(settingController);
           if(apiKeySetupDialog.getKeyValid()){
               try {
                   runMainWindow();
               } catch (StockSystemException ignored) {
               }
           }

       }
    }
    private void runMainWindow() throws StockSystemException {
        systemController = SystemController.getInstance();
        MainWindow mainWindow = new MainWindow(systemController);
    }
}
