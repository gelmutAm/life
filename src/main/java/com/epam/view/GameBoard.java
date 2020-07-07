package com.epam.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

        colonyField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (!colonyField.bacteriumExists(x, y)) {
                    colonyField.createBacterium(x, y);
                } else {
                    colonyField.clearCell(x, y);
                }

                colonyField.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

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

