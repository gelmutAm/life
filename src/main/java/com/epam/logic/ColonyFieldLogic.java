package com.epam.logic;

import com.epam.models.Bacterium;
import com.epam.models.Colony;
import com.epam.models.Pair;

import java.util.Random;

public class ColonyFieldLogic implements ColonyFieldLogicInterface{
    private Colony colony;

    public ColonyFieldLogic() {
    }

    public ColonyFieldLogic(int m, int n) {
        colony = new Colony(m, n);
    }

    private Pair<Integer, Integer> getColonyIndex(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        coord.setKey(coord.getKey() / cellSize.getKey());
        coord.setValue(coord.getValue() / cellSize.getValue());
        return coord;
    }

    public Bacterium getBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        Pair<Integer, Integer> colonyIndex = getColonyIndex(coord, cellSize);
        return colony.getBacterium(colonyIndex.getKey(), colonyIndex.getValue());
    }

    public void createBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        Pair<Integer, Integer> colonyIndex = getColonyIndex(coord, cellSize);
        int row = colonyIndex.getKey();
        int column = colonyIndex.getValue();

        if (colony.createBacterium(colonyIndex.getKey(), colonyIndex.getValue())) {
            Bacterium bacterium = colony.getBacterium(row, column);
            bacterium.setX(row * cellSize.getKey());
            bacterium.setY(column * cellSize.getValue());
        }
    }

    public void clearCell(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        Pair<Integer, Integer> colonyIndex = getColonyIndex(coord, cellSize);
        colony.clearCell(colonyIndex.getKey(), colonyIndex.getValue());
    }

    public void clearColony() {
        colony.clear();
    }

    private Pair<Integer, Integer> getRandomCoord(int maxCoord) {
        Pair<Integer, Integer> coord = new Pair<>();
        Random r = new Random();
        coord.setKey(r.nextInt(maxCoord));
        coord.setValue(r.nextInt(maxCoord));

        return coord;
    }

    public void fillColony(int maxCoord, Pair<Integer, Integer> cellSize, int cellQty) {
        Random r = new Random();
        int qty = r.nextInt(cellQty);
        for (int i = 0; i < qty; i++) {
            Pair<Integer, Integer> coord = getRandomCoord(maxCoord);
            createBacterium(coord, cellSize);
        }
    }

    public boolean colonyIsEmpty() {
        return colony.isEmpty();
    }
}
