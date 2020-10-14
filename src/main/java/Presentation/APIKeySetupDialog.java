package Presentation;

import System.SystemInterface.APIKeySupplier;
import System.SystemInterface.ISettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;

public class
APIKeySetupDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel buttonPanel;
    private JTextField keyTextField;
    private JComboBox<String> APIKeySupplierComboBox;
    private JLabel Message;
    private JLabel EnterMessage;
    private LinkedHashMap<String, APIKeySupplier> keySupplierHashMap;

    private boolean sendKeyCheck = false;
    private String PreviousKeyThatWasCheckedAndItsValid;
    private Boolean isKeyValid;

    private ISettingController settingController;

    public APIKeySetupDialog(ISettingController settingController){
        this(settingController, true);
    }

    public APIKeySetupDialog(ISettingController settingController, Boolean isKeyValid) {

        this.settingController = settingController;
        this.isKeyValid = isKeyValid;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        keySupplierHashMap = new LinkedHashMap<>();
        for(APIKeySupplier keySupplier : APIKeySupplier.values()){
            String keyName = APIKeySupplier.getSupplierName(keySupplier);
            APIKeySupplierComboBox.addItem(keyName);
            keySupplierHashMap.put(keyName, keySupplier);
        }

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

        this.setSize(350,250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void onOK() {

        if (!isKeyValid || !keyTextField.getText().equals(PreviousKeyThatWasCheckedAndItsValid)){
            sendKeyCheck = true;
            isKeyValid = false;

            Message.setText("Validating key");
            Message.setForeground(Color.BLACK);

            new Thread(() -> {
                if (settingController.saveOrUpdateAPIKey(keySupplierHashMap.get(APIKeySupplierComboBox.getSelectedItem().toString()),
                        keyTextField.getText())) {
                    isKeyValid = true;
                    PreviousKeyThatWasCheckedAndItsValid = keyTextField.getText();
                    Message.setText("The key is valid and has been saved");
                    Message.setForeground(Color.GREEN);

                } else {
                    Message.setText("Not a valid key");
                    Message.setForeground(Color.RED);
                    sendKeyCheck = false;
                }
            }).start();
        }
    }

    private void onCancel() {
        dispose();
    }

    public Boolean isValidKey() {
        return isKeyValid;
    }
}
