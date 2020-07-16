package com.epam.life.logic;

import com.epam.life.models.Bacterium;
import com.epam.life.models.Pair;

import java.util.concurrent.ExecutionException;

public interface ColonyFieldLogic {

    Bacterium getBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void createBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void clearCell(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    void clearColony();

    void fillColony(Pair<Integer, Integer> maxCoord, Pair<Integer, Integer> cellSize, int cellQty);

    boolean colonyIsEmpty();

    void modifyColony(Pair<Integer, Integer> cellSize) throws ExecutionException, InterruptedException;

    boolean isColonyChanged();
}