package com.epam.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameBoard extends JPanel {
    private ColonyField colonyField;
    private Integer m = 5;
    private Integer n = 10;
    private Integer t = 0;

    private Button startBtn;
    private Button stopBtn;
    private Button clearBtn;

    public GameBoard() {
        colonyField = new ColonyField(m, n);
        add(getComponentsTogether(colonyField, getButtonsPane()), BorderLayout.CENTER);

        startBtn.addActionListener((ActionEvent e) -> {
            colonyField.start();
            colonyField.repaint();
        });

        stopBtn.addActionListener((ActionEvent e) -> {

        });

        clearBtn.addActionListener((ActionEvent e) -> {
            colonyField.clear();
            colonyField.repaint();
        });
    }

    private JPanel getButtonsPane() {
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));

        startBtn = new Button("Start");
        stopBtn = new Button("Stop");
        clearBtn = new Button("Clear");

        buttonsPane.add(startBtn);
        buttonsPane.add(stopBtn);
        buttonsPane.add(clearBtn);

        return buttonsPane;
    }

    private JPanel getComponentsTogether(JPanel colonyField, JPanel buttonsPane) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(colonyField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(buttonsPane);

        return panel;
    }
}

