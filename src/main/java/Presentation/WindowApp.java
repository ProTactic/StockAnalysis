package Presentation;

import Exceptions.StockSystemException;
import System.SystemInterface.SettingController;
import System.SystemInterface.SystemController;

import javax.swing.*;

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
           APIKeySetup apiKeySetup = new APIKeySetup(settingController);

       }
    }
    private void runMainWindow() throws StockSystemException {
        systemController = SystemController.getInstance();
        MainWindow mainWindow = new MainWindow(systemController);
    }
}
