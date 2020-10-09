package Presentation;

import javax.swing.*;

public class MainWindow extends JFrame {
    private JPanel mainPanel;

    public MainWindow(){
        super();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.toFront();
    }


}
