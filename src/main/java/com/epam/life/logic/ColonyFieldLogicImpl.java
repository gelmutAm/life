package com.epam.life.logic;

import com.epam.life.common.CommonMethods;
import com.epam.life.models.Bacterium;
import com.epam.life.models.Colony;
import com.epam.life.models.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ColonyFieldLogicImpl implements ColonyFieldLogic {
    private Colony colony;

    private boolean colonyChanged = false;

    public ColonyFieldLogicImpl() {
    }

    public ColonyFieldLogicImpl(int m, int n) {
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
        //int qty = cellQty / 10;
        for (int i = 0; i < qty; i++) {
            Pair<Integer, Integer> coord = getRandomCoord(maxCoord);
            createBacterium(coord, cellSize);
        }
    }

    public boolean colonyIsEmpty() {
        return colony.isEmpty();
    }

    private List<Pair<Integer, Integer>> getBacteriaToCreate() {
        List<Pair<Integer, Integer>> bacteriaToCreate = new ArrayList<>();
        for (int row = 0; row < colony.getM(); row++) {
            for (int column = 0; column < colony.getN(); column++) {
                Bacterium bacterium = colony.getBacterium(row, column);
                int neighboursQty = colony.getNeighboursQty(row, column);

                if (bacterium == null) {
                    if (neighboursQty == 3) {
                        bacteriaToCreate.add(new Pair<>(row, column));
                    }
                }
            }
        }

        return bacteriaToCreate;
    }

    private List<Pair<Integer, Integer>> getBacteriaToClear() {
        List<Pair<Integer, Integer>> bacteriaToClear = new ArrayList<>();
        for (int row = 0; row < colony.getM(); row++) {
            for (int column = 0; column < colony.getN(); column++) {
                Bacterium bacterium = colony.getBacterium(row, column);
                int neighboursQty = colony.getNeighboursQty(row, column);

                if (bacterium != null) {
                    if (neighboursQty < 2 || neighboursQty > 4) {
                        bacteriaToClear.add(new Pair<>(row, column));
                    }
                }
            }
        }

        return bacteriaToClear;
    }

    public void modifyColony(Pair<Integer, Integer> cellSize) throws ExecutionException, InterruptedException {
        FutureTask<List<Pair<Integer, Integer>>> creation = new FutureTask<>(() -> getBacteriaToCreate());
        FutureTask<List<Pair<Integer, Integer>>> cleaning = new FutureTask<>(() -> getBacteriaToClear());
        Thread creator = new Thread(creation);
        Thread cleaner = new Thread(cleaning);
        CommonMethods.startJoinThread(creator);
        CommonMethods.startJoinThread(cleaner);
        transformColony(creation.get(), cleaning.get(), cellSize);
    }

    private synchronized void transformColony(List<Pair<Integer, Integer>> toCreate
            , List<Pair<Integer, Integer>> toClear
            , Pair<Integer, Integer> cellSize) {
        if (toCreate.size() > 0 || toClear.size() > 0) {
            colonyChanged = true;
        } else {
            colonyChanged = false;
        }

        for (int i = 0; i < toCreate.size(); i++) {
            int row = toCreate.get(i).getKey();
            int column = toCreate.get(i).getValue();
            colony.createBacterium(row, column, row * cellSize.getKey(), column * cellSize.getValue());
        }

        for (int i = 0; i < toClear.size(); i++) {
            colony.clearCell(toClear.get(i).getKey(), toClear.get(i).getValue());
        }
    }

    public boolean isColonyChanged() {
        return colonyChanged;
    }
}
