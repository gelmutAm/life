package com.epam.life.view;

import com.epam.life.common.GameConfig;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.text.ParseException;

public class ParametersSelectionDialog extends JDialog {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    private static final int TEXT_FIELD_SIZE = 3;
    private static final int SPACE_BETWEEN_COMPONENTS = 10;

    private static final int MIN_VALUE = 2;
    private static final int MAX_VALUE = 279;

    private Button okBtn;
    private JFormattedTextField columnsTf;
    private JFormattedTextField rowsTf;
    private JFormattedTextField iterationsTf;

    private int columnQty = 2;
    private int rowQty = 2;
    private int iterationQty = 2;

    private boolean isPressed = false;

    public ParametersSelectionDialog() {
        add(getPsdPane());
        new WarningDialog(MIN_VALUE, MAX_VALUE);

        columnsTf.addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (columnsTf.isEditValid()) {
                columnQty = getTextFieldValue(columnsTf);
            }
        });

        rowsTf.addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (rowsTf.isEditValid()) {
                rowQty = getTextFieldValue(rowsTf);
            }
        });

        iterationsTf.addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (iterationsTf.isEditValid()) {
                iterationQty = getTextFieldValue(iterationsTf);
            }
        });



        okBtn.addActionListener((ActionEvent e) -> {
            isPressed = true;
            GameConfig.createInstance(columnQty, rowQty, iterationQty);
            this.dispose();
        });

        setPsdStyle();
    }

    private void setPsdStyle() {
        setModal(true);
        setTitle("Select parameters");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JPanel getPsdPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        okBtn = new Button("Ok");
        columnsTf = getJfTextField();
        rowsTf = getJfTextField();
        iterationsTf = getJfTextField();
        Label columnsLable = new Label("Columns:");
        Label rowsLable = new Label("Rows:");
        Label iterationsLable = new Label("Iterations:");

        JPanel labelsPanel = getVerticalLayoutPanel(columnsLable, rowsLable, iterationsLable);
        JPanel textFieldsPanel = getVerticalLayoutPanel(columnsTf, rowsTf, iterationsTf);
        JPanel labelsFieldsPanel = getHorizontalLayoutPanel(labelsPanel, textFieldsPanel);

        panel.add(labelsFieldsPanel);

        JPanel okPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okPanel.add(okBtn);

        panel.add(okPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JFormattedTextField getJfTextField() {
        NumberFormat numFormat = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(numFormat);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(MIN_VALUE);
        formatter.setMaximum(MAX_VALUE);
        formatter.setCommitsOnValidEdit(true);
        JFormattedTextField ftf = new JFormattedTextField(formatter);
        ftf.setColumns(TEXT_FIELD_SIZE);
        ftf.setValue(columnQty);

        return ftf;
    }

    private JPanel getVerticalLayoutPanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        for (Component item : components) {
           panel.add(item);
           panel.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));
        }

        return panel;
    }

    private JPanel getHorizontalLayoutPanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (Component item : components) {
            panel.add(item);
        }

        return panel;
    }

    private int getTextFieldValue(JFormattedTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (ParseException e) {
        }

        return (int) ftf.getValue();
    }

    public boolean getIsPressed() {
        return isPressed;
    }
}