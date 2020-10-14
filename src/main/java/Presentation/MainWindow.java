package Presentation;

import System.SystemInterface.*;
import com.sun.istack.NotNull;

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

    private ISystemController systemController;

    private JPanel mainPanel;

    /*** menu bar ***/
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem fileMenu_UpdateAPIKey;


    /*** search panel ***/
    private JTextField searchTextField;
    private JLabel searchLabel;
    private JButton searchButton;
    private JPanel searchPanel;
    private String previousSearch;

    /*** data panel ***/
    private JPanel dataPanel;
    private JTabbedPane tabbedPanel;
    private final int COMPANY_OVERVIEW_TAB = 0;
    private final int FINANCIAL_TAB = 1;
    private JPanel companyOverviewTab;

    /*** ratio list ***/
    private JList<String> ratioList;

    /*** company overview tab ***/
    private JLabel coName;
    private JLabel coCountry;
    private JLabel coCurrency;
    private JLabel coSector;

    /*** financial tab ***/
    private JTable financialTable;
    private JPanel financial;
    private String[] comboOptions = {"Income statement", "Balance Sheet", "Cash Flow"};
    private final int COMBO_BOX_INCOME_STATEMENT_INDEX = 0;
    private final int COMBO_BOX_BALANCE_SHEET_INDEX = 1;
    private final int COMBO_BOX_CASH_FLOW_INDEX = 2;
    private JComboBox<String> financialComboBox;

    public MainWindow(@NotNull ISystemController systemController){
        super("Stock analysis");

        this.systemController = systemController;

        initializeWindow();
        setListeners();
    }

    private void initializeWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280,960);
        this.setContentPane(mainPanel);

        //menu bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu_UpdateAPIKey = new JMenuItem("update api key");
        fileMenu_UpdateAPIKey.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() ->{
                APIKeySetupDialog apiKeySetupDialog = new APIKeySetupDialog(SettingController.getInstance());
            });
        });
        fileMenu.add(fileMenu_UpdateAPIKey);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);

        previousSearch = "";

        financialTable.setRowHeight(50);

        //ratios list
        ratioList.setFixedCellHeight(50);
        ratioList.setCellRenderer(getRenderer());

        for (String option: comboOptions) {
            financialComboBox.addItem(option);
        }

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.toFront();
    }

    private void setListeners(){
        //Search Button
        searchButton.addActionListener((ActionEvent e) -> {
            updateDataSearchBottun();
        });

        //Search text field
        searchTextField.addKeyListener(new TextFieldKeyListener());

        tabbedPanel.addChangeListener((ChangeEvent e) -> {
            updateTabbedData();
        });

        financialComboBox.addItemListener((ItemEvent e) -> {
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
        String symbol = previousSearch;
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
        Result<CompanyOverviewDTO> companyOverviewRecord = systemController.getCompanyOverview(symbol);
        if(companyOverviewRecord.isNotValid()){
            messageBox_AndCleanData("Error for search" + symbol, companyOverviewRecord.getMessage());
            return;
        }

        CompanyOverviewDTO companyOverview = companyOverviewRecord.getEntity();
        coName.setText(String.format("%s (%s : %s)", companyOverview.name,
                companyOverview.exchange, symbol.toUpperCase()));
        coCountry.setText("Country : " + companyOverview.country);
        coCurrency.setText("Currency : " + companyOverview.currency);
        coSector.setText("Sector : " + companyOverview.sector);

        //ratio list
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("market cap: "+ companyOverview.marketCapitalization);
        listModel.addElement("PE ratio: "+ companyOverview.PERatio);
        listModel.addElement("book value: "+ companyOverview.bookValue);
        listModel.addElement("PB ratio: "+ companyOverview.priceToBookRatio);
        listModel.addElement("shares outstanding: "+ companyOverview.sharesOutstanding);
        ratioList.setModel(listModel);
    }

    private void cleanCompanyOverviewTab(){
        coName.setText(""); coCountry.setText("");
        coCurrency.setText(""); coSector.setText("");
        ((DefaultListModel<String>)ratioList.getModel()).clear();
    }

    private void updateFinancialTab(String symbol) {
        Result<List<? extends FinancialDTO>> financialDTOSResult;
        Vector<String> dates = new Vector<>();
        Field[] fields = null;

        int comboSelectedIndex = financialComboBox.getSelectedIndex();
        switch (comboSelectedIndex){
            case COMBO_BOX_INCOME_STATEMENT_INDEX:
            {
                financialDTOSResult = systemController.getLastIncomeStatements(symbol);
                fields = IncomeStatementDTO.class.getFields();
                break;
            }
            case COMBO_BOX_BALANCE_SHEET_INDEX: {
                financialDTOSResult = systemController.getLastBalanceSheets(symbol);
                fields = BalanceSheetDTO.class.getFields();
                break;
            }
            case COMBO_BOX_CASH_FLOW_INDEX: {
                financialDTOSResult = systemController.getLastCashFlows(symbol);
                fields = CashFlowDTO.class.getFields();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + financialComboBox.getSelectedIndex());
        }

        if(financialDTOSResult.isNotValid()){
            messageBox_AndCleanData("Error for search" + symbol, financialDTOSResult.getMessage());
            return;
        }

        dates.add("Date");
        List<? extends FinancialDTO> financialDTOS = financialDTOSResult.getEntity();
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

            defaultTableModel.setValueAt(FinancialDTO.varNameToPresentableString(field.getName()), atRow, 0);
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
        financialTable.setModel(defaultTableModel);
        financialTable.getColumnModel().getColumn(0).setCellRenderer(new ColumnColorRenderer(Color.getHSBColor(0,0, (float) 0.94), Color.BLACK));
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        defaultTableCellRenderer.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        for(int i = 1; i < financialTable.getColumnCount(); i++){
            financialTable.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
        return defaultTableModel;
    }

    private void messageBox_AndCleanData(String header, String message){
        searchTextField.setText("");
        previousSearch = "";

        //clean company overview tab
        cleanCompanyOverviewTab();

        //clean financial table
        ((DefaultTableModel) financialTable.getModel()).setRowCount(0);

        JOptionPane.showMessageDialog(null, message, header, JOptionPane.INFORMATION_MESSAGE);
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
                updateDataSearchBottun();
            }
        }
    }

    private void updateDataSearchBottun() {
        previousSearch = searchTextField.getText();
        updateTabbedData();
    }

    /*** table column models ***/

    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,Color.BLACK));
                return listCellRendererComponent;
            }
        };
    }


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
