package Presentation;

import Exceptions.StockSystemException;
import System.SystemInterface.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

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
    private final int COMPANY_OVERVIEW_TAB = 0;
    private final int FINANCIAL_TAB = 1;
    private JPanel companyOverviewTab;

    /*** company overview tab ***/
    private JLabel coName;
    private JLabel coCountry;
    private JLabel coCurrency;
    private JLabel coSector;

    /*** Financial tab ***/
    private JTable incomeStatementTable;
    private JPanel Financial;
    private String[] comboOptions = {"Income statement", "Balance Sheet", "Cash Flow"};
    private final int COMBO_BOX_INCOME_STATEMENT_INDEX = 0;
    private final int COMBO_BOX_BALANCE_SHEET_INDEX = 1;
    private final int COMBO_BOX_CASH_FLOW_INDEX = 2;
    private JComboBox<String> FinancialComboBox;

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

        for (String option: comboOptions) {
            FinancialComboBox.addItem(option);
        }

        this.setVisible(true);
        this.toFront();
    }

    private void setListeners(){
        //Search Button
        searchButton.addActionListener((ActionEvent e) -> { updateTabbedData(); });

        //Search text field
        searchTextField.addKeyListener(new TextFieldKeyListener());

        tabbedPanel.addChangeListener((ChangeEvent e) -> {
            updateTabbedData();
        });

        FinancialComboBox.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                String symbol = searchTextField.getText();
                if(symbol.equals("")){
                    return;
                }
                updateFinancialTab(symbol);
            }
        });

    }

    private void updateTabbedData(){
        String symbol = searchTextField.getText();
        if(symbol.equals("")){
            return;
        }

        int tabIndex = tabbedPanel.getSelectedIndex();
        if(tabIndex == COMPANY_OVERVIEW_TAB) {
            updateCompanyOverviewTabData(symbol);
        } else if(tabIndex == FINANCIAL_TAB){
            updateFinancialTab(symbol);
        }
    }

    private void updateCompanyOverviewTabData(String symbol){
        CompanyOverviewDTO companyOverview = systemController.getCompanyOverview(symbol);
        coName.setText(String.format("%s (%s : %s)", companyOverview.name,
                companyOverview.exchange, symbol.toUpperCase()));
        coCountry.setText("Country : " + companyOverview.country);
        coCurrency.setText("Currency : " + companyOverview.currency);
        coSector.setText("Sector : " + companyOverview.sector);
    }

    private void updateFinancialTab(String symbol) {
        List<? extends FinancialDTO> financialDTOS;
        Vector<String> dates = new Vector<>();
        Field[] fields = null;

        int comboSelectedIndex = FinancialComboBox.getSelectedIndex();
        switch (comboSelectedIndex){
            case COMBO_BOX_INCOME_STATEMENT_INDEX: {
                financialDTOS = systemController.getLastIncomeStatements(symbol);
                fields = IncomeStatementDTO.class.getFields();
                break;
            }
            case COMBO_BOX_BALANCE_SHEET_INDEX: {
                financialDTOS = systemController.getLastBalanceSheets(symbol);
                fields = BalanceSheetDTO.class.getFields();
                break;
            }
            case COMBO_BOX_CASH_FLOW_INDEX: {
                financialDTOS = systemController.getLastCashFlows(symbol);
                fields = CashFlowDTO.class.getFields();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + FinancialComboBox.getSelectedIndex());
        }
        dates.add("Date");
        for (FinancialDTO dto: financialDTOS) {
            dates.add(dto.date.toString());
        }

        DefaultTableModel defaultTableModel = cleanAndInitializeFinancialTable(dates, fields);
        updateFinancialTable(financialDTOS, fields, defaultTableModel);
    }


    private void updateFinancialTable(List<? extends FinancialDTO> financialDTOS, Field[] fields, DefaultTableModel defaultTableModel) {
        int atRow = 0;
        int atColumn = 1;
        for ( Field field : fields  ) {
            if(field.getName().equals("symbol") || field.getName().equals("date")){
                continue;
            }

            defaultTableModel.setValueAt(field.getName(), atRow, 0);
            for (FinancialDTO dto: financialDTOS) {
                try {
                    defaultTableModel.setValueAt(field.get(dto).toString(), atRow, atColumn);
                } catch (IllegalAccessException ignored) {
                }
                ++atColumn;
            }
            ++atRow;
            atColumn = 1;
        }
    }

    private DefaultTableModel cleanAndInitializeFinancialTable(Vector<String> dates, Field[] fields) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(dates, fields.length - 2);
        incomeStatementTable.setModel(defaultTableModel);
        incomeStatementTable.getColumnModel().getColumn(0).setCellRenderer(new ColumnColorRenderer(Color.getHSBColor(0,0, (float) 0.94), Color.BLACK));
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        defaultTableCellRenderer.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        for(int i=1; i < incomeStatementTable.getColumnCount(); i++){
            incomeStatementTable.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
        return defaultTableModel;
    }

    /*** listeners ***/

    protected class TextFieldKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER){
                updateTabbedData();
            }
        }
    }

    /*** table column models ***/
    class ColumnColorRenderer extends DefaultTableCellRenderer {
        Color backgroundColor, foregroundColor;
        public ColumnColorRenderer(Color backgroundColor, Color foregroundColor) {
            super();
            this.backgroundColor = backgroundColor;
            this.foregroundColor = foregroundColor;
            this.setHorizontalAlignment(JLabel.CENTER);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,   boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setBackground(backgroundColor);
            cell.setForeground(foregroundColor);
            this.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));

            return cell;
        }
    }

}
