package Presentation;

import Exceptions.StockSystemException;
import System.SystemInterface.ISettingController;
import System.SystemInterface.ISystemController;
import System.SystemInterface.SettingController;
import System.SystemInterface.SystemController;

public class WindowApp {

    private ISystemController systemController;
    private ISettingController settingController;

    public WindowApp() {
        settingController = SettingController.getInstance();
    }

    public void run() {
       try {
           runMainWindow();
        } catch (StockSystemException e) {
           APIKeySetupDialog apiKeySetupDialog = new APIKeySetupDialog(settingController);
           if(apiKeySetupDialog.isVaildKey()){
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
