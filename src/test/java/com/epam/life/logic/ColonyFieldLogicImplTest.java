package com.epam.life.logic;

import com.epam.life.models.Bacterium;
import com.epam.life.models.Pair;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class ColonyFieldLogicImplTest {
    private final int columnQty = 10;
    private final int rowQty = 10;
    private final ColonyFieldLogicImpl colonyFieldLogic = new ColonyFieldLogicImpl(columnQty, rowQty);
    private final int xCellSize = 28;
    private final int yCellSize = 28;
    private final Pair<Integer, Integer> cellSize = new Pair<>(xCellSize, yCellSize);

    @Test
    public void testCreateBacterium_WithCoordinates_IfDoesNotExist() {
        int xCoord = 56;
        int yCoord = 56;
        Pair<Integer, Integer> coord = new Pair<>(xCoord, yCoord);
        colonyFieldLogic.createBacterium(coord, cellSize);
        Bacterium bacterium = colonyFieldLogic.getBacterium(coord, cellSize);
        assertEquals(xCoord, bacterium.getX());
        assertEquals(yCoord, bacterium.getY());
    }

    @Test
    public void testClearCell() {
        int xCoord = 0;
        int yCoord = 0;
        Pair<Integer, Integer> coord = new Pair<>(xCoord, yCoord);
        colonyFieldLogic.createBacterium(coord, cellSize);
        colonyFieldLogic.clearCell(coord, cellSize);
        assertTrue(colonyFieldLogic.colonyIsEmpty());
    }

    @Test
    public void testClearColony() {
        int xCoord = 0;
        int yCoord = 0;
        Pair<Integer, Integer> coord = new Pair<>(xCoord, yCoord);
        colonyFieldLogic.createBacterium(coord, cellSize);
        colonyFieldLogic.clearColony();
        assertTrue(colonyFieldLogic.colonyIsEmpty());
    }

    @Test
    public void testFillColony() {
        Pair<Integer, Integer> maxCoord = new Pair<>(columnQty * xCellSize, rowQty * yCellSize);
        int cellQty = columnQty * rowQty;
        colonyFieldLogic.fillColony(maxCoord, cellSize, cellQty);
        assertFalse(colonyFieldLogic.colonyIsEmpty());
    }

    @Test
    public void testModifyColony_IfEmptyCellHasThreeNeighbours() throws ExecutionException, InterruptedException {
        int neighbour1Column = 1;
        int neighbour1Row = 0;
        Pair<Integer, Integer> neighbour1Coord = new Pair<>(neighbour1Column * cellSize.getKey(),
                neighbour1Row * cellSize.getValue());
        int neighbour2Column = 1;
        int neighbour2Row = 1;
        Pair<Integer, Integer> neighbour2Coord = new Pair<>(neighbour2Column * cellSize.getKey(),
                neighbour2Row * cellSize.getValue());
        int neighbour3Column = 0;
        int neighbour3Row = 1;
        Pair<Integer, Integer> neighbour3Coord = new Pair<>(neighbour3Column * cellSize.getKey(),
                neighbour3Row * cellSize.getValue());

        colonyFieldLogic.createBacterium(neighbour1Coord, cellSize);
        colonyFieldLogic.createBacterium(neighbour2Coord, cellSize);
        colonyFieldLogic.createBacterium(neighbour3Coord, cellSize);

        colonyFieldLogic.modifyColony(cellSize);

        int bacteriumXCoord = 0;
        int bacteriumYCoord = 0;
        assertNotNull(colonyFieldLogic.getBacterium(new Pair<>(bacteriumXCoord, bacteriumYCoord), cellSize));
    }

    @Test
    public void testModifyColony_IfBacteriumHasLessThanTwoNeighbours() throws ExecutionException, InterruptedException {
        int bacteriumColumn = 0;
        int bacteriumRow = 0;
        Pair<Integer, Integer> bacteriumCoord = new Pair<>(bacteriumColumn * cellSize.getKey(),
                bacteriumRow * cellSize.getValue());
        int neighbour1Column = 1;
        int neighbour1Row = 0;
        Pair<Integer, Integer> neighbour1Coord = new Pair<>(neighbour1Column * cellSize.getKey(),
                neighbour1Row * cellSize.getValue());

        colonyFieldLogic.createBacterium(bacteriumCoord, cellSize);
        colonyFieldLogic.createBacterium(neighbour1Coord, cellSize);

        colonyFieldLogic.modifyColony(cellSize);

        assertNull(colonyFieldLogic.getBacterium(bacteriumCoord, cellSize));
    }

    @Test
    public void testModifyColony_IfBacteriumHasMoreThanFourNeighbours() throws ExecutionException, InterruptedException {
        int bacteriumColumn = 2;
        int bacteriumRow = 0;
        Pair<Integer, Integer> bacteriumCoord = new Pair<>(bacteriumColumn * cellSize.getKey(),
                bacteriumRow * cellSize.getValue());
        int neighbour1Column = 1;
        int neighbour1Row = 0;
        Pair<Integer, Integer> neighbour1Coord = new Pair<>(neighbour1Column * cellSize.getKey(),
                neighbour1Row * cellSize.getValue());
        int neighbour2Column = 1;
        int neighbour2Row = 1;
        Pair<Integer, Integer> neighbour2Coord = new Pair<>(neighbour2Column * cellSize.getKey(),
                neighbour2Row * cellSize.getValue());
        int neighbour3Column = 2;
        int neighbour3Row = 1;
        Pair<Integer, Integer> neighbour3Coord = new Pair<>(neighbour3Column * cellSize.getKey(),
                neighbour3Row * cellSize.getValue());
        int neighbour4Column = 3;
        int neighbour4Row = 1;
        Pair<Integer, Integer> neighbour4Coord = new Pair<>(neighbour4Column * cellSize.getKey(),
                neighbour4Row * cellSize.getValue());
        int neighbour5Column = 3;
        int neighbour5Row = 0;
        Pair<Integer, Integer> neighbour5Coord = new Pair<>(neighbour5Column * cellSize.getKey(),
                neighbour5Row * cellSize.getValue());

        colonyFieldLogic.createBacterium(bacteriumCoord, cellSize);
        colonyFieldLogic.createBacterium(neighbour1Coord, cellSize);
        colonyFieldLogic.createBacterium(neighbour2Coord, cellSize);
        colonyFieldLogic.createBacterium(neighbour3Coord, cellSize);
        colonyFieldLogic.createBacterium(neighbour4Coord, cellSize);
        colonyFieldLogic.createBacterium(neighbour5Coord, cellSize);

        colonyFieldLogic.modifyColony(cellSize);

        assertNull(colonyFieldLogic.getBacterium(bacteriumCoord, cellSize));
    }
}