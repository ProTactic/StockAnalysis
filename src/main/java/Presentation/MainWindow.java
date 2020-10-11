package Presentation;

import Exceptions.StockSystemException;
import System.SystemInterface.CompanyOverviewDTO;
import System.SystemInterface.SystemController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private SystemController systemController;

    private JPanel mainPanel;

    /*** search panel ***/
    private JTextField searchTextField;
    private JLabel searchLabel;
    private JButton searchButton;
    private JPanel searchPanel;

    /*** data panel ***/
    private JPanel dataPanel;
    private JTabbedPane tabbedPanel;
    private JPanel companyOverviewTab;
    private JPanel incomeStatementTab;

    /*** company overview tab ***/
    private JLabel coName;
    private JLabel coCountry;
    private JLabel coCurrency;
    private JLabel coSector;

    public MainWindow(){
        super("Stock analysis");

        try {
            systemController = SystemController.getInstance();
        } catch (StockSystemException e) {}

        initializeWindow();
        setListeners();
    }

    private void initializeWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(720,640);
        this.setContentPane(mainPanel);

        this.setVisible(true);
        this.toFront();
    }

    private void setListeners(){
        searchButton.addActionListener(new ButtonSearchAction());
    }

    /*** listeners ***/

    protected class ButtonSearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String symbol = searchTextField.getText();
            CompanyOverviewDTO companyOverview = systemController.getCompanyOverview(symbol);
            coName.setText(String.format("%s (%s : %s)", companyOverview.name,
                                                companyOverview.exchange, symbol.toUpperCase()));
            coCountry.setText("Country : " + companyOverview.country);
            coCurrency.setText("Currency : " + companyOverview.currency);
            coSector.setText("Sector : " + companyOverview.sector);
        }
    }

}
