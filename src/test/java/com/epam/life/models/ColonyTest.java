package com.epam.life.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColonyTest {
    private final int columnQty = 10;
    private final int rowQty = 10;
    private final Colony colony = new Colony(columnQty, rowQty);
    private final int column = 0;
    private final int row = 0;
    private final int x = 0;
    private final int y = 0;

    @Test
    public void testCreateBacterium2args_notExist_true() {
        assertTrue(colony.createBacterium(column, row));
    }

    @Test
    public void testCreateBacterium2args_exists_false() {
        colony.createBacterium(column, row);
        assertFalse(colony.createBacterium(column, row));
    }

    @Test
    public void testCreateBacterium4args_notExist_true() {
        assertTrue(colony.createBacterium(column, row, x, y));
    }

    @Test
    public void testCreateBacterium4args_exists_false() {
        colony.createBacterium(column, row, x, y);
        assertFalse(colony.createBacterium(column, row, x, y));
    }

    @Test
    public void testClearCell() {
        colony.createBacterium(column, row);
        colony.clearCell(column, row);
        assertNull(colony.getBacterium(column, row));
    }

    @Test
    public void testClear() {
        colony.createBacterium(column, row);
        colony.clear();
        for (int i = 0; i < colony.getColumnQty(); i++) {
            for (int j = 0; j < colony.getRowQty(); j++) {
                assertNull(colony.getBacterium(i, j));
            }
        }
    }

    @Test
    public void testIsEmpty() {
        assertTrue(colony.isEmpty());
        colony.createBacterium(column, row);
        assertFalse(colony.isEmpty());
    }

    @Test
    public void testGetNeighboursQty() {
        fail();
    }
}