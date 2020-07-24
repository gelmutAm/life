package com.epam.life.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColonyTest {
    private final int columnQty = 10;
    private final int rowQty = 10;
    private final Colony colony = new Colony(columnQty, rowQty);
    private final int column = 0;
    private final int row = 0;

    @Test
    public void testCreateBacterium_IfExists() {
        colony.createBacterium(column, row);
        assertFalse(colony.createBacterium(column, row));
    }

    @Test
    public void testCreateBacterium_WithCoordinates_IfExists() {
        int x = 0;
        int y = 0;
        int newX = 10;
        int newY = 10;
        colony.createBacterium(column, row, x, y);
        Bacterium bacterium = colony.getBacterium(column, row);
        colony.createBacterium(column, row, newX, newY);
        Bacterium newBacterium = colony.getBacterium(column, row);
        assertEquals(newBacterium.getX(), bacterium.getX());
        assertEquals(newBacterium.getY(), bacterium.getY());
    }

    @Test
    public void testClearCell() {
        colony.createBacterium(column, row);
        colony.clearCell(column, row);
        assertNull(colony.getBacterium(column, row));
    }

    @Test
    public void testClearColony() {
        colony.createBacterium(column, row);
        colony.clear();
        for (int i = 0; i < colony.getColumnQty(); i++) {
            for (int j = 0; j < colony.getRowQty(); j++) {
                assertNull(colony.getBacterium(i, j));
            }
        }
    }

    @Test
    public void testIsEmpty_IfAnyBacteriaExist() {
        colony.createBacterium(column, row);
        assertFalse(colony.isEmpty());
    }

    @Test
    public void testNeighboursQty_IfBacteriumLocatedAtLeftUpperCorner() {
        colony.createBacterium(column, row);
        int neighbourColumn = 1;
        int neighbourRow = 0;
        colony.createBacterium(neighbourColumn, neighbourRow);
        int neighboursQty = 1;
        assertEquals(neighboursQty, colony.getNeighboursQty(column, row));
    }

    @Test
    public void testNeighboursQty_IfBacteriumLocatedAtLeftLowerCorner() {
        int column = 0;
        int row = colony.getRowQty() - 1;
        colony.createBacterium(column, row);
        int neighbour1Column = 1;
        int neighbour1Row = colony.getRowQty() - 2;
        colony.createBacterium(neighbour1Column, neighbour1Row);
        int neighbour2Column = 1;
        int neighbour2Row = colony.getRowQty() - 1;
        colony.createBacterium(neighbour2Column, neighbour2Row);
        int neighboursQty = 2;
        assertEquals(neighboursQty, colony.getNeighboursQty(column, row));
    }

    @Test
    public void testNeighboursQty_IfBacteriumLocatedAtRightUpperCorner() {
        int column = colony.getColumnQty() - 1;
        int row = 0;
        colony.createBacterium(column, row);
        int neighbourColumn = colony.getColumnQty() - 2;
        int neighbourRow = 1;
        colony.createBacterium(neighbourColumn, neighbourRow);
        int neighboursQty = 1;
        assertEquals(neighboursQty, colony.getNeighboursQty(column, row));
    }

    @Test
    public void testNeighboursQty_IfBacteriumLocatedAtRightLowerCorner() {
        int column = colony.getColumnQty() - 1;
        int row = colony.getRowQty() - 1;
        colony.createBacterium(column, row);
        int neighbourColumn = colony.getColumnQty() - 2;
        int neighbourRow = colony.getRowQty() - 1;
        colony.createBacterium(neighbourColumn, neighbourRow);
        int neighboursQty = 1;
        assertEquals(neighboursQty, colony.getNeighboursQty(column, row));
    }

    @Test
    public void testNeighboursQty_IfBacteriumLocatedAtCenter() {
        int column = colony.getColumnQty() / 2;
        int row = colony.getRowQty() / 2;
        colony.createBacterium(column, row);
        int neighboursQty = 0;
        assertEquals(neighboursQty, colony.getNeighboursQty(column, row));
    }
}