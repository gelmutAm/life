package com.epam.life.view;

import javax.swing.*;
import java.awt.*;

public class WarningDialog extends JDialog {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;
    private static final String MESSAGE = "Only digits are accepted. ";

    public WarningDialog() {
    }

    public WarningDialog(int minValue, int maxValue) {
        add(getPane(minValue, maxValue));
        setStyle();
    }

    private void setStyle() {
        setModal(true);
        setTitle("Warning");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JPanel getPane(int minValue, int maxValue) {
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());

        Font font = new Font("Verdana", Font.PLAIN, 12);
        String str = "Min value = " + minValue + ". " + "Max value = " + maxValue + ".";
        JLabel label = new JLabel(MESSAGE + str);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);

        pane.add(label);

        return pane;
    }
}