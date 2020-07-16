package com.epam.life.view;

import com.epam.life.common.GameConfig;
import com.epam.life.models.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutionException;

public class GameBoard extends JPanel {
    private static final int SPACE_BETWEEN_COMPONENTS = 10;
    private static final double WIDTH_INCREASE = 1.7;
    private static final double HEIGHT_INCREASE = 1.3;

    private ColonyField colonyField;

    private Button startBtn;
    private Button stopBtn;
    private Button clearBtn;

    private volatile boolean isRunning = false;

    public GameBoard() {
        ParametersSelectionDialog parametersSelectionDialog = new ParametersSelectionDialog();
        if(parametersSelectionDialog.getIsPressed()) {
            colonyField = new ColonyField();
            add(getComponentsTogether(colonyField, getButtonsPane()), BorderLayout.CENTER);

            colonyField.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!isRunning) {
                        int x = e.getX();
                        int y = e.getY();
                        if (!colonyField.bacteriumExists(x, y)) {
                            colonyField.createBacterium(x, y);
                        } else {
                            colonyField.clearCell(x, y);
                        }

                        colonyField.repaint();
                    }
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
                disableBtns();
                colonyField.fillColony();
                colonyField.repaint();

                Thread manager = new Thread(() -> {
                    for (int i = 0; i < GameConfig.getInstance().getIterationQty() && isRunning; i++) {
                        try {
                            colonyField.modifyColony();
                        } catch (ExecutionException | InterruptedException exception) {
                            exception.printStackTrace();
                        }

                        colonyField.repaint();

                        if (!colonyField.changed()) {
                            enableBtns();
                        }

                        try {
                            //to draw each iteration
                            Thread.sleep(1_000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }

                    enableBtns();
                });

                manager.start();
            });

            stopBtn.addActionListener((ActionEvent e) -> {
                enableBtns();
            });

            clearBtn.addActionListener((ActionEvent e) -> {
                colonyField.clear();
                colonyField.repaint();
            });
        } else {
            System.exit(0);
        }
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
        panel.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));
        panel.add(buttonsPane);

        return panel;
    }

    private void disableBtns() {
        isRunning = true;
        startBtn.setLabel("Running");
        startBtn.setEnabled(false);
        clearBtn.setEnabled(false);
    }

    private void enableBtns() {
        isRunning = false;
        startBtn.setLabel("Start");
        startBtn.setEnabled(true);
        clearBtn.setEnabled(true);
    }

    public Pair<Integer, Integer> getWindowSize() {
        int width = (int) (colonyField.getFieldSize().getKey() * WIDTH_INCREASE);
        int height = (int) (colonyField.getFieldSize().getValue() * HEIGHT_INCREASE);
        return new Pair<>(width, height);
    }
}