package Presentation;

import Exceptions.StockSystemException;
import System.SystemInterface.CompanyOverviewDTO;
import System.SystemInterface.IncomeStatementDTO;
import System.SystemInterface.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
    private JScrollPane incomeStatementTab;
    private JPanel companyOverviewTab;

    /*** company overview tab ***/
    private JLabel coName;
    private JLabel coCountry;
    private JLabel coCurrency;
    private JLabel coSector;

    /*** income statement tab ***/
    private JTable incomeStatementTable;

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

        String[] columnsNames = {"Date", "Total revenue", "cost of revenue", "Gross profit",
                "Operating expense", "Operating income", "Net income"};
        incomeStatementTable.setModel(new DefaultTableModel(columnsNames, 0));
    }

    private void setListeners(){
        searchButton.addActionListener(new ButtonSearchAction());
    }

    /*** listeners ***/

    protected class ButtonSearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String symbol = searchTextField.getText();
            int tabIndex = tabbedPanel.getSelectedIndex();
            if(tabIndex == 0) {
                CompanyOverviewDTO companyOverview = systemController.getCompanyOverview(symbol);
                coName.setText(String.format("%s (%s : %s)", companyOverview.name,
                        companyOverview.exchange, symbol.toUpperCase()));
                coCountry.setText("Country : " + companyOverview.country);
                coCurrency.setText("Currency : " + companyOverview.currency);
                coSector.setText("Sector : " + companyOverview.sector);
            } else if(tabIndex == 1){
                DefaultTableModel tableModel = (DefaultTableModel) incomeStatementTable.getModel();
                tableModel.setRowCount(0);
                List<IncomeStatementDTO> incomeStatementDTOS = systemController.getLastIncomeStatements(symbol);
                for (IncomeStatementDTO dto: incomeStatementDTOS) {
                    Object[] data = {dto.date, dto.totalRevenue, dto.costOfRevenue, dto.grossProfit,
                                    dto.totalOperatingExpense, dto.operatingIncome, dto.netIncome};
                    tableModel.addRow(data);
                }
            }
        }
    }

}
