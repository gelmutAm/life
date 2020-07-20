package com.epam.life.view;

import com.epam.life.models.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <code>GameWindow</code> is a main window of the game.
 */
public class GameWindow extends JFrame {
    private static final double WIDTH_MAGNIFICATION_FACTOR = 1.1;
    private static final double HEIGHT_MAGNIFICATION_FACTOR = 120;

    private static GameWindow instance;

    private GameBoard gameBoard;

    private GameWindow() {
        this.gameBoard = new GameBoard();
        Button settingsButton = new Button("Settings");

        settingsButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            instance = null;
            ParametersSelectionDialog.getInstance().setVisible(true);
        });

        gameBoard.add(settingsButton);
        add(gameBoard);

        setMainWindowStyle();
    }

    public static GameWindow getInstance() {
        if (instance == null) {
            instance = new GameWindow();
        }

        return instance;
    }

    private void setMainWindowStyle() {
        setTitle("Life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(getWindowSize().getKey(), getWindowSize().getValue());
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private Pair<Integer, Integer> getWindowSize() {
        Pair<Integer, Integer> size = new Pair<>();
        size.setKey((int) (gameBoard.getColonyFieldSize().getKey() * WIDTH_MAGNIFICATION_FACTOR));
        size.setValue((int) (gameBoard.getColonyFieldSize().getValue() + HEIGHT_MAGNIFICATION_FACTOR));
        return size;
    }
}