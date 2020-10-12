import Exceptions.StockSystemException;
import Presentation.MainWindow;
import Presentation.WindowApp;
import System.SystemInterface.SystemController;

public class Main {
    public static void main(String[] args)  {
        WindowApp windowApp = new WindowApp();
        windowApp.run();
    }
}