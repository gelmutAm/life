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
    private static final int WIDTH = 280;
    private static final int HEIGHT = 280;

    private Pair<Integer, Integer> size = new Pair<>(WIDTH, HEIGHT);
    private int minCoord = 0;
    private Pair<Integer, Integer> maxCoord;

    private static Pair<Integer, Integer> cellSize;
    private static Integer cellQty;

    private ColonyFieldLogic colonyFieldLogic;

    public ColonyField() {
        colonyFieldLogic = DependencyResolver.getColonyFieldLogic();
        int columnQty = GameConfig.getInstance().getColumnQty();
        int rowQty = GameConfig.getInstance().getRowQty();
        cellQty = columnQty * rowQty;
        cellSize = new Pair<>(size.getKey() / columnQty, size.getValue() / rowQty);
        setAppropriateSize(columnQty, rowQty);
        maxCoord = new Pair<>(size.getKey(), size.getValue());
        setColonyFieldStyle();
    }

    private void setAppropriateSize(int columnQty, int rowQty) {
        if (size.getKey() % columnQty != 0 || size.getValue() % rowQty != 0) {
            if (cellSize.getKey() * columnQty < size.getKey() || cellSize.getValue() * rowQty < size.getValue()) {
                cellSize.setKey(cellSize.getKey() + 1);
                cellSize.setValue(cellSize.getValue() + 1);
                size.setKey(cellSize.getKey() * columnQty);
                size.setValue(cellSize.getValue() * rowQty);
            }
        }
    }

    private void setColonyFieldStyle() {
        setPreferredSize(new Dimension(size.getKey(), size.getValue()));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public Pair<Integer, Integer> getFieldSize() {
        return size;
    }

    public void fillColony() {
        if(colonyFieldLogic.colonyIsEmpty()) {
            colonyFieldLogic.fillColony(maxCoord, cellSize, cellQty);
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
        for (int i = minCoord; i < maxCoord.getKey(); i += cellSize.getKey()) {
            for (int j = minCoord; j < maxCoord.getValue(); j += cellSize.getValue()) {
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