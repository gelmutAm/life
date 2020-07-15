package com.epam.life.view;

import com.epam.life.common.DependencyResolver;
import com.epam.life.common.GameConfig;
import com.epam.life.logic.ColonyFieldLogic;
import com.epam.life.models.Bacterium;
import com.epam.life.models.Pair;

import javax.swing.*;
import java.awt.*;

import java.util.concurrent.ExecutionException;

public class ColonyField extends JPanel {
    public static final Integer SIZE = 280;
    private static final Integer MIN_COORD = 0;
    private static final Integer MAX_COORD = SIZE;

    private static Pair<Integer, Integer> cellSize;
    private static Integer cellQty;

    private ColonyFieldLogic colonyFieldLogic;

    public ColonyField() {
        colonyFieldLogic = DependencyResolver.getColonyFieldLogic();
        cellSize = new Pair<>(SIZE / GameConfig.getInstance().getColumnQty(), SIZE / GameConfig.getInstance().getRowQty());
        cellQty = GameConfig.getInstance().getColumnQty() * GameConfig.getInstance().getRowQty();
        setColonyFieldStyle();
    }

    private void setColonyFieldStyle() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public void fillColony() {
        if(colonyFieldLogic.colonyIsEmpty()) {
            colonyFieldLogic.fillColony(MAX_COORD, cellSize, cellQty);
        }
    }

    public void clear() {
        colonyFieldLogic.clearColony();
    }

    public void createBacterium(int x, int y) {
        colonyFieldLogic.createBacterium(new Pair<>(x, y), cellSize);
    }

    public boolean bacteriumExists(int x, int y) {
        return colonyFieldLogic.getBacterium(new Pair<>(x, y), cellSize) != null;
    }

    public void clearCell(int x, int y) {
        colonyFieldLogic.clearCell(new Pair<>(x, y), cellSize);
    }

    public void modifyColony() throws ExecutionException, InterruptedException {
        colonyFieldLogic.modifyColony(cellSize);
    }

    public boolean changed() {
        return colonyFieldLogic.isColonyChanged();
    }

    private void drawBacterium(Graphics g, int x, int y, int width, int heigth) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, width, heigth);
    }

    private void drawColony(Graphics g) {
        for (int i = MIN_COORD; i < MAX_COORD; i += cellSize.getKey()) {
            for (int j = MIN_COORD; j < MAX_COORD; j += cellSize.getValue()) {
                Bacterium bacterium = colonyFieldLogic.getBacterium(new Pair<>(i, j), cellSize);
                if (bacterium != null) {
                    drawBacterium(g, bacterium.getX(), bacterium.getY(), cellSize.getKey(), cellSize.getValue());
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawColony(g);
    }
}
