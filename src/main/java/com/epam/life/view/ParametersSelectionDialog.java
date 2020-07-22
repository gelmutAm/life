package com.epam.life.view;

import com.epam.life.common.DependencyResolver;
import com.epam.life.common.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * <code>ParametersSelectionDialog</code> is a dialog window for a game settings.
 */
public class ParametersSelectionDialog extends JFrame {
    private static ParametersSelectionDialog instance;

    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    private static final int TEXT_FIELD_SIZE = 3;
    private static final int SPACE_BETWEEN_COMPONENTS = 10;
    private static final String MESSAGE = "All fields must be filled correctly.";

    private static final int MIN_VALUE = 2;
    private static final int MAX_VALUE = 279;

    private Button okButton;
    private JTextField columnsTextField;
    private JTextField rowsTextField;
    private JTextField iterationsTextField;

    private int columnQty = 2;
    private int rowQty = 2;
    private int iterationQty = 2;

    private ParametersSelectionDialog() {
        add(getPsdPane());

        setKeyListener(columnsTextField);
        setKeyListener(rowsTextField);
        setKeyListener(iterationsTextField);

        okButton.addActionListener((ActionEvent e) -> {
            if (allTextFieldsAreFilledCorrectly()) {
                columnQty = Integer.parseInt(columnsTextField.getText());
                rowQty = Integer.parseInt(rowsTextField.getText());
                iterationQty = Integer.parseInt(iterationsTextField.getText());
                DependencyResolver.setColonyFieldLogicToNull();
                GameConfig.setInstanceToNull();
                GameConfig.createInstance(columnQty, rowQty, iterationQty);
                GameWindow.getInstance();
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, MESSAGE);
            }
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
        columnsTextField = getJTextField();
        rowsTextField = getJTextField();
        iterationsTextField = getJTextField();
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

    private JTextField getJTextField() {
        JTextField tf = new JTextField();
        tf.setColumns(TEXT_FIELD_SIZE);
        tf.setText(String.valueOf(MIN_VALUE));

        return tf;
    }

    private boolean allTextFieldsAreFilledCorrectly() {
        String columns = columnsTextField.getText();
        String rows = rowsTextField.getText();
        String iterations = iterationsTextField.getText();
        if (columns.isEmpty() || rows.isEmpty() || iterations.isEmpty()) {
            return false;
        } else {
            return isNumberInCorrectRange(Integer.parseInt(columns)) && isNumberInCorrectRange(Integer.parseInt(rows));
        }
    }

    private boolean isNumberInCorrectRange(Integer number) {
        return number >= MIN_VALUE && number <= MAX_VALUE;
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

    private void setKeyListener(JTextField tf) {
        tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (!Character.isDigit(key)
                        && key != KeyEvent.VK_BACK_SPACE
                        && key != KeyEvent.VK_ENTER
                        && key != KeyEvent.VK_DELETE) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}