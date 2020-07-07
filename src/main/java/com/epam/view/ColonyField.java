package com.epam.view;

import com.epam.common.DependencyResolver;
import com.epam.logic.ColonyFieldLogicInterface;
import com.epam.models.Bacterium;
import com.epam.models.Pair;

import javax.swing.*;
import java.awt.*;

public class ColonyField extends JPanel {
    private static final Integer SIZE = 280;
    private static final Integer MIN_COORD = 0;
    private static final Integer MAX_COORD = SIZE;

    private static Pair<Integer, Integer> cellSize;
    private static Integer cellQty;

    private ColonyFieldLogicInterface colonyFieldLogic;

    public ColonyField() {
    }

    public ColonyField(int m, int n) {
        colonyFieldLogic = DependencyResolver.getColonyFieldLogic(m, n);
        cellSize = new Pair<>(SIZE / m, SIZE / n);
        cellQty = SIZE / (m * n);
        setColonyFieldStyle();
    }

    private void setColonyFieldStyle() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public void start() {
        colonyFieldLogic.fillColony(MAX_COORD, cellSize, cellQty);
    }

    public void clear() {
        colonyFieldLogic.clearColony();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = MIN_COORD; i < MAX_COORD; i += cellSize.getKey()) {
            for (int j = MIN_COORD; j < MAX_COORD; j += cellSize.getValue()) {
                Bacterium bacterium = colonyFieldLogic.getBacterium(new Pair<Integer, Integer>(i, j), cellSize);
                if (bacterium != null) {
                    g.setColor(Color.BLUE);
                    g.fillOval(bacterium.getX(), bacterium.getY(), cellSize.getKey(), cellSize.getValue());
                }
            }
        }
    }
}
