package com.epam.life.view;

import com.epam.life.common.DependencyResolver;
import com.epam.life.common.GameConfig;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * <code>ParametersSelectionDialog</code> is a dialog window for game settings.
 */
public class ParametersSelectionDialog extends JFrame {
    private static ParametersSelectionDialog instance;

    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    private static final int TEXT_FIELD_SIZE = 3;
    private static final int SPACE_BETWEEN_COMPONENTS = 10;

    private static final int MIN_VALUE = 2;
    private static final int MAX_VALUE = 279;

    private Button okButton;
    private JFormattedTextField columnsTextField;
    private JFormattedTextField rowsTextField;
    private JFormattedTextField iterationsTextField;

    private int columnQty = 2;
    private int rowQty = 2;
    private int iterationQty = 2;

    private ParametersSelectionDialog() {
        add(getPsdPane());

        columnsTextField.addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (columnsTextField.isEditValid()) {
                columnQty = getTextFieldValue(columnsTextField);
            }
        });

        rowsTextField.addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (rowsTextField.isEditValid()) {
                rowQty = getTextFieldValue(rowsTextField);
            }
        });

        iterationsTextField.addPropertyChangeListener((PropertyChangeEvent e) -> {
            if (iterationsTextField.isEditValid()) {
                iterationQty = getTextFieldValue(iterationsTextField);
            }
        });

        okButton.addActionListener((ActionEvent e) -> {
            DependencyResolver.setColonyFieldLogicToNull();
            GameConfig.setInstanceToNull();
            GameConfig.createInstance(columnQty, rowQty, iterationQty);
            GameWindow.getInstance();
            setVisible(false);
        });

        setPsdStyle();
    }

    public static ParametersSelectionDialog getInstance() {
        if (instance == null) {
            instance = new ParametersSelectionDialog();
        }

        return instance;
    }

    private void setPsdStyle() {
        setTitle("Settings");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JPanel getPsdPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        okButton = new Button("Ok");
        columnsTextField = getJfTextField();
        rowsTextField = getJfTextField();
        iterationsTextField = getJfTextField();
        Label columnsLable = new Label("Columns:");
        Label rowsLable = new Label("Rows:");
        Label iterationsLable = new Label("Iterations:");

        JPanel labelsPanel = getVerticalLayoutPanel(columnsLable, rowsLable, iterationsLable);
        JPanel textFieldsPanel = getVerticalLayoutPanel(columnsTextField, rowsTextField, iterationsTextField);
        JPanel labelsFieldsPanel = getHorizontalLayoutPanel(labelsPanel, textFieldsPanel);

        panel.add(labelsFieldsPanel);

        JPanel okPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okPanel.add(okButton);

        panel.add(okPanel, BorderLayout.SOUTH);

        return panel;
    }

    private NumberFormatter getFormatter() {
        NumberFormat numFormat = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(numFormat);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(MIN_VALUE);
        formatter.setMaximum(MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        return formatter;
    }

    private JFormattedTextField getJfTextField() {
        JFormattedTextField ftf = new JFormattedTextField(getFormatter());
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
            e.printStackTrace();
        }

        return (int) ftf.getValue();
    }
}