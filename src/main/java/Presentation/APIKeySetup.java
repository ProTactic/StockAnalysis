package Presentation;

import System.SystemInterface.APIKeySupplier;
import System.SystemInterface.SettingController;

import javax.swing.*;
import java.awt.event.*;
import java.util.LinkedHashMap;

public class
APIKeySetup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel buttonPanel;
    private JTextField keyTextField;
    private JComboBox APIKeySupplierComboBox;
    private JLabel Message;
    private LinkedHashMap<String, APIKeySupplier> keySupplierHashMap;

    private boolean sendKeyCheck = false;

    SettingController settingController;

    public APIKeySetup(SettingController settingController) {

        this.settingController = settingController;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        keySupplierHashMap = new LinkedHashMap<>();
        for(APIKeySupplier keySupplier : APIKeySupplier.values()){
            String keyName = APIKeySupplier.getSupplierName(keySupplier);
            APIKeySupplierComboBox.addItem(keyName);
            keySupplierHashMap.put(keyName, keySupplier);
        }

        /*buttonOK.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) {
                onOK();
            }
        });*/

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void onOK() {

        if (!sendKeyCheck){
            sendKeyCheck = true;
            new Thread(() -> {
                if (settingController.saveOrUpdateAPIKey(keySupplierHashMap.get(APIKeySupplierComboBox.getSelectedItem().toString()),
                        keyTextField.getText())) {
                    dispose();
                }
                Message.setText("Not a valid key");
                sendKeyCheck = false;
            }).start();
        }
    }

    private void onCancel() {
        System.exit(0);
    }
}
