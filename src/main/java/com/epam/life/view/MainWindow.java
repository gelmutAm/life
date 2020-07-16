package com.epam.life.view;

import javax.swing.*;

public class MainWindow {
    private static MainWindow instance;

    private GameBoard gameBoard;

    private MainWindow() {
        this.gameBoard = new GameBoard();
        getMainWindowStyle().add(gameBoard);
    }

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }

        return instance;
    }

    private JFrame getMainWindowStyle() {
        JFrame window = new JFrame();
        window.setTitle("Life");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(gameBoard.getWindowSize().getKey(), gameBoard.getWindowSize().getValue());
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);

        return window;
    }

    public static void main(String[] args) {
        MainWindow.getInstance();
    }
}