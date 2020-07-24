package com.epam.life.view;

import com.epam.life.common.GameConfig;
import com.epam.life.models.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutionException;

/**
 * <code>GameBoard</code>
 */
public class GameBoard extends JPanel {
    private static final int SPACE_BETWEEN_COMPONENTS = 5;

    private final ColonyField colonyField;

    private Button startButton;
    private Button stopButton;
    private Button clearButton;

    private volatile boolean isRunning = false;

    /**
     * Constructs a game board.
     */
    public GameBoard() {
        colonyField = new ColonyField();
        add(getComponentsTogether(colonyField, getButtonsPane()));
        setMouseListener(colonyField);

        startButton.addActionListener((ActionEvent e) -> {
            disableButtons();
            colonyField.fillColony();
            colonyField.repaint();
            startManagerThread();
        });

        stopButton.addActionListener((ActionEvent e) -> {
            enableButtons();
        });

        clearButton.addActionListener((ActionEvent e) -> {
            colonyField.clear();
            colonyField.repaint();
        });
    }

    private JPanel getButtonsPane() {
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));

        startButton = new Button("Start");
        stopButton = new Button("Stop");
        clearButton = new Button("Clear");

        buttonsPane.add(startButton);
        buttonsPane.add(stopButton);
        buttonsPane.add(clearButton);

        return buttonsPane;
    }

    private JPanel getComponentsTogether(JPanel... panels) {
        JPanel resultPane = new JPanel();
        resultPane.setLayout(new BoxLayout(resultPane, BoxLayout.PAGE_AXIS));
        for (JPanel item : panels) {
            resultPane.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));
            resultPane.add(item);
        }

        return resultPane;
    }

    private void disableButtons() {
        isRunning = true;
        startButton.setLabel("Running");
        startButton.setEnabled(false);
        clearButton.setEnabled(false);
    }

    private void enableButtons() {
        isRunning = false;
        startButton.setLabel("Start");
        startButton.setEnabled(true);
        clearButton.setEnabled(true);
    }

    private void setMouseListener(ColonyField colonyField) {
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
    }

    private void startManagerThread() {
        Thread manager = new Thread(() -> {
            for (int i = 0; i < GameConfig.getInstance().getIterationQty() && isRunning; i++) {
                try {
                    colonyField.modifyColony();
                } catch (ExecutionException | InterruptedException exception) {
                    exception.printStackTrace();
                }

                colonyField.repaint();

                if (!colonyField.changed()) {
                    enableButtons();
                }

                try {
                    //to draw each iteration
                    Thread.sleep(1_000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }

            enableButtons();
        });

        manager.start();
    }

    public Pair<Integer, Integer> getColonyFieldSize() {
        return colonyField.getFieldSize();
    }
}