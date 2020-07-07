package com.epam.logic;

import com.epam.models.Bacterium;
import com.epam.models.Pair;

public interface ColonyFieldLogicInterface {

    Bacterium getBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void createBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void clearCell(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void clearColony();

    void fillColony(int maxCoord, Pair<Integer, Integer> cellSize, int cellQty);

    boolean colonyIsEmpty();
}
