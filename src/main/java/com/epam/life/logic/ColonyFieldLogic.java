package com.epam.life.logic;

import com.epam.life.models.Bacterium;
import com.epam.life.models.Pair;

public interface ColonyFieldLogic {

    Bacterium getBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void createBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void clearCell(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void clearColony();

    void fillColony(int maxCoord, Pair<Integer, Integer> cellSize, int cellQty);

    boolean colonyIsEmpty();

    void modifyColony(Pair<Integer, Integer> cellSize);
}
