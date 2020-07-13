package com.epam.life.view;

import com.epam.life.common.CommonMethods;
import com.epam.life.common.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class ParametersSelectionDialog extends JDialog {
    private Button okBtn;
    JComboBox<Integer> mComboBox;
    JComboBox<Integer> nComboBox;
    JComboBox<Integer> tComboBox;

    Integer m = 2;
    Integer n = 2;
    Integer t = 2;

    private boolean isPressed = false;

    public ParametersSelectionDialog() {
        add(getPsdPane());

        mComboBox.addActionListener((ActionEvent e) -> m  = (Integer) mComboBox.getSelectedItem());
        nComboBox.addActionListener((ActionEvent e) -> n = (Integer) nComboBox.getSelectedItem());
        tComboBox.addActionListener((ActionEvent e) -> t = (Integer) tComboBox.getSelectedItem());

        okBtn.addActionListener((ActionEvent e) -> {
            isPressed = true;
            GameConfig.createInstance(m, n, t);
            this.dispose();
        });

        setPsdStyle();
    }

    private void setPsdStyle() {
        setModal(true);
        setTitle("Select parameters");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JPanel getPsdPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        okBtn = new Button("Ok");
        Vector<Integer> divisors = CommonMethods.getDivisors(ColonyField.SIZE);
        mComboBox = new JComboBox<>(divisors);
        nComboBox = new JComboBox<>(divisors);
        tComboBox = new JComboBox<>(divisors);
        Label mLable = new Label("M:");
        Label nLable = new Label("N:");
        Label tLable = new Label("T:");

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.PAGE_AXIS));
        comboBoxPanel.add(getComboBoxPane(mLable, mComboBox));
        comboBoxPanel.add(getComboBoxPane(nLable, nComboBox));
        comboBoxPanel.add(getComboBoxPane(tLable, tComboBox));

        panel.add(comboBoxPanel);

        JPanel okPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okPanel.add(okBtn);
        panel.add(okPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel getComboBoxPane(Label label, JComboBox<? extends Object> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(comboBox);

        return panel;
    }

    public boolean getIsPressed() {
        return isPressed;
    }
}
