package com.epam.life.logic;

import com.epam.life.models.Bacterium;
import com.epam.life.models.Colony;
import com.epam.life.models.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Implementation of the {@code ColonyFieldLogic} interface.
 */
public class ColonyFieldLogicImpl implements ColonyFieldLogic {
    private static final int NEIGHBOURS_QTY_TO_CREATE = 3;
    private static final int MIN_NEIGHBOURS_QTY_TO_CLEAR = 2;
    private static final int MAX_NEIGHBOURS_QTY_TO_CLEAR = 4;

    private Colony colony;

    private boolean colonyChanged = false;

    /**
     * Constructs an empty colony field logic instance.
     */
    public ColonyFieldLogicImpl() {
    }

    /**
     * Constructs an empty colony field logic instance with an empty colony.
     *
     * @param columnQty number of columns in this colony
     * @param rowQty    number of rows in this colony
     */
    public ColonyFieldLogicImpl(int columnQty, int rowQty) {
        colony = new Colony(columnQty, rowQty);
    }

    private Pair<Integer, Integer> getColonyIndex(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        Pair<Integer, Integer> colonyIndex = new Pair<>(coord.getKey(), coord.getValue());
        colonyIndex.setKey(coord.getKey() / cellSize.getKey());
        colonyIndex.setValue(coord.getValue() / cellSize.getValue());
        return colonyIndex;
    }

    @Override
    public Bacterium getBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        Pair<Integer, Integer> colonyIndex = getColonyIndex(coord, cellSize);
        return colony.getBacterium(colonyIndex.getKey(), colonyIndex.getValue());
    }

    @Override
    public void createBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        Pair<Integer, Integer> colonyIndex = getColonyIndex(coord, cellSize);
        int column = colonyIndex.getKey();
        int row = colonyIndex.getValue();

        if (colony.createBacterium(colonyIndex.getKey(), colonyIndex.getValue())) {
            Bacterium bacterium = colony.getBacterium(column, row);
            bacterium.setX(column * cellSize.getKey());
            bacterium.setY(row * cellSize.getValue());
        }
    }

    @Override
    public void clearCell(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize) {
        Pair<Integer, Integer> colonyIndex = getColonyIndex(coord, cellSize);
        colony.clearCell(colonyIndex.getKey(), colonyIndex.getValue());
    }

    @Override
    public void clearColony() {
        colony.clear();
    }

    private Pair<Integer, Integer> getRandomCoord(Pair<Integer, Integer> maxCoord) {
        Pair<Integer, Integer> coord = new Pair<>();
        Random r = new Random();
        coord.setKey(r.nextInt(maxCoord.getKey()));
        coord.setValue(r.nextInt(maxCoord.getValue()));

        return coord;
    }

    @Override
    public void fillColony(Pair<Integer, Integer> maxCoord, Pair<Integer, Integer> cellSize, int cellQty) {
        Random r = new Random();
        int qty = r.nextInt(cellQty);
        for (int i = 0; i < qty; i++) {
            Pair<Integer, Integer> coord = getRandomCoord(maxCoord);
            createBacterium(coord, cellSize);
        }
    }


    @Override
    public boolean colonyIsEmpty() {
        return colony.isEmpty();
    }

    private List<Pair<Integer, Integer>> getBacteriaToCreate() {
        List<Pair<Integer, Integer>> bacteriaToCreate = new ArrayList<>();
        for (int column = 0; column < colony.getColumnQty(); column++) {
            for (int row = 0; row < colony.getRowQty(); row++) {
                Bacterium bacterium = colony.getBacterium(column, row);
                int neighboursQty = colony.getNeighboursQty(column, row);

                if (bacterium == null) {
                    if (neighboursQty == NEIGHBOURS_QTY_TO_CREATE) {
                        bacteriaToCreate.add(new Pair<>(column, row));
                    }
                }
            }
        }

        return bacteriaToCreate;
    }

    private List<Pair<Integer, Integer>> getBacteriaToClear() {
        List<Pair<Integer, Integer>> bacteriaToClear = new ArrayList<>();
        for (int column = 0; column < colony.getColumnQty(); column++) {
            for (int row = 0; row < colony.getRowQty(); row++) {
                Bacterium bacterium = colony.getBacterium(column, row);
                int neighboursQty = colony.getNeighboursQty(column, row);

                if (bacterium != null) {
                    if (neighboursQty < MIN_NEIGHBOURS_QTY_TO_CLEAR || neighboursQty > MAX_NEIGHBOURS_QTY_TO_CLEAR) {
                        bacteriaToClear.add(new Pair<>(column, row));
                    }
                }
            }
        }

        return bacteriaToClear;
    }

    @Override
    public synchronized void modifyColony(Pair<Integer, Integer> cellSize) throws ExecutionException, InterruptedException {
        FutureTask<List<Pair<Integer, Integer>>> creation = new FutureTask<>(this::getBacteriaToCreate);
        FutureTask<List<Pair<Integer, Integer>>> cleaning = new FutureTask<>(this::getBacteriaToClear);
        Thread creator = new Thread(creation);
        Thread cleaner = new Thread(cleaning);
        creator.start();
        cleaner.start();
        transformColony(creation.get(), cleaning.get(), cellSize);
    }

    private void transformColony(List<Pair<Integer, Integer>> toCreate
            , List<Pair<Integer, Integer>> toClear
            , Pair<Integer, Integer> cellSize) {
        colonyChanged = toCreate.size() > 0 || toClear.size() > 0;

        for (Pair<Integer, Integer> integerIntegerPair : toCreate) {
            int column = integerIntegerPair.getKey();
            int row = integerIntegerPair.getValue();
            colony.createBacterium(column, row, column * cellSize.getKey(), row * cellSize.getValue());
        }
        for (Pair<Integer, Integer> integerIntegerPair : toClear) {
            colony.clearCell(integerIntegerPair.getKey(), integerIntegerPair.getValue());
        }
    }

    @Override
    public boolean isColonyChanged() {
        return colonyChanged;
    }
}