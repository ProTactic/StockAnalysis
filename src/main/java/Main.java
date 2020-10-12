import Exceptions.StockSystemException;
import Presentation.MainWindow;
import System.SystemInterface.SystemController;

public class Main {
    public static void main(String[] args)  {
        //MainWindow mainWindow = new MainWindow();
        try {
            SystemController systemController = SystemController.getInstance();
        } catch (StockSystemException e) {
            e.printStackTrace();
        }

    }
}