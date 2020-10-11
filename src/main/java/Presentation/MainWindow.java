package Presentation;

import Exceptions.StockSystemException;
import System.SystemInterface.CompanyOverviewDTO;
import System.SystemInterface.IncomeStatementDTO;
import System.SystemInterface.SystemController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    private void setListeners(){
        //Search Button
        searchButton.addActionListener(new ButtonSearchAction());

        //Search text field
        searchTextField.addKeyListener(new TextFieldKeyListener());

        tabbedPanel.addChangeListener((ChangeEvent e) -> {
            updateTabbedData();
        });

    }

    private void updateTabbedData(){
        String symbol = searchTextField.getText();
        if(symbol.equals("")){
            return;
        }

        int tabIndex = tabbedPanel.getSelectedIndex();
        if(tabIndex == 0) {
            CompanyOverviewDTO companyOverview = systemController.getCompanyOverview(symbol);
            coName.setText(String.format("%s (%s : %s)", companyOverview.name,
                    companyOverview.exchange, symbol.toUpperCase()));
            coCountry.setText("Country : " + companyOverview.country);
            coCurrency.setText("Currency : " + companyOverview.currency);
            coSector.setText("Sector : " + companyOverview.sector);
        } else if(tabIndex == 1){
            List<IncomeStatementDTO> incomeStatementDTOS = systemController.getLastIncomeStatements(symbol);
            Vector<String> dates = new Vector<>();

            dates.add("Date");
            for (IncomeStatementDTO dto: incomeStatementDTOS) {
                dates.add(dto.date.toString());
            }

            Field[] fields = IncomeStatementDTO.class.getDeclaredFields();
            DefaultTableModel defaultTableModel = new DefaultTableModel(dates, fields.length - 2);
            incomeStatementTable.setModel(defaultTableModel);
            incomeStatementTable.getColumnModel().getColumn(0).setCellRenderer(new ColumnColorRenderer(Color.getHSBColor(0,0, (float) 0.94), Color.BLACK));
            DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
            defaultTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
            defaultTableCellRenderer.setFont(new Font(Font.SERIF, Font.BOLD, 24));
            for(int i=1; i < incomeStatementTable.getColumnCount(); i++){
                incomeStatementTable.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
            }

            int atRow = 0;
            int atColumn = 1;
            for ( Field field : fields  ) {
                if(field.getName().equals("symbol") || field.getName().equals("date")){
                    continue;
                }

                defaultTableModel.setValueAt(field.getName(), atRow, 0);
                for (IncomeStatementDTO dto: incomeStatementDTOS) {
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
    }

    /*** listeners ***/

    protected class ButtonSearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateTabbedData();
        }
    }

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
