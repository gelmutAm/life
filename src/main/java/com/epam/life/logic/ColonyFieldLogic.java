package com.epam.life.logic;

import com.epam.life.models.Bacterium;
import com.epam.life.models.Pair;

import java.util.concurrent.ExecutionException;

public interface ColonyFieldLogic {

    /**
     * Returns the bacterium at the specified position in this colony.
     * @param coord coordinate of the bacterium to return
     * @param cellSize size of colony field cell
     * @return the bacterium at the specified position in this colony.
     */
    Bacterium getBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    /**
     * Inserts the bacterium at the specified position in this colony.
     * @param coord coordinate at which the bacterium is to be inserted
     * @param cellSize size of colony field cell
     */
    void createBacterium(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    /**
     * Removes the bacterium at the specified position in this colony.
     * @param coord coordinate of the bacterium to be removed
     * @param cellSize size of colony field cell
     */
    void clearCell(Pair<Integer, Integer> coord, Pair<Integer, Integer> cellSize);

    /**
     * Removes all bacteria from this colony.
     */
    void clearColony();

    /**
     * Fills this colony with random coordinates bacteria.
     * @param maxCoord max coordinate of the colony field
     * @param cellSize size of colony field cell
     * @param cellQty number of colony field cells
     */
    void fillColony(Pair<Integer, Integer> maxCoord, Pair<Integer, Integer> cellSize, int cellQty);

    /**
     * Returns {@code true} if this colony contains no bacteria.
     *
     * @return {@code true} if this colony contains no bacteria.
     */
    boolean colonyIsEmpty();

    /**
     * Modifies this colony.
     * @param cellSize size of colony field cell
     * @throws ExecutionException
     * @throws InterruptedException
     */
    void modifyColony(Pair<Integer, Integer> cellSize) throws ExecutionException, InterruptedException;

    /**
     * Returns {@code true} if this colony has changed.
     *
     * @return {@code true} if this colony has changed.
     */
    boolean isColonyChanged();
}