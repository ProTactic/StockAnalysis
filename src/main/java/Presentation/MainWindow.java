package Presentation;

import javax.swing.*;

public class MainWindow extends JFrame {

    private JPanel mainPanel;
    private JTextField searchTextField;
    private JLabel searchLabel;
    private JButton searchButton;
    private JPanel searchPanel;
    private JPanel dataPanel;

    public MainWindow(){
        initializeWindow();
    }

    private void initializeWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(720,640);
        this.setContentPane(mainPanel);

        this.setVisible(true);
        this.toFront();
    }

}
