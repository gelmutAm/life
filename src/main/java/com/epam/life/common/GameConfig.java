package com.epam.life.common;

/**
 * <code>GameConfig</code> stores game settings.
 */
public class GameConfig {
    public static GameConfig instance;

    private int columnQty;
    private int rowQty;
    private int iterationQty;

    private GameConfig() {
    }

    private GameConfig(int columnQty, int rowQty, int iterationQty) {
        this.columnQty = columnQty;
        this.rowQty = rowQty;
        this.iterationQty = iterationQty;
    }

    /**
     * Creates {@code GameConfig} with the specified parameters.
     *
     * @param columnQty    number of columns in colony
     * @param rowQty       number of rows in colony
     * @param iterationQty number of game iterations
     */
    public static void createInstance(int columnQty, int rowQty, int iterationQty) {
        if (instance == null) {
            instance = new GameConfig(columnQty, rowQty, iterationQty);
        }
    }

    public static GameConfig getInstance() {
        return instance;
    }

    public static void setInstanceToNull() {
        instance = null;
    }

    public int getColumnQty() {
        return columnQty;
    }

    public int getRowQty() {
        return rowQty;
    }

    public int getIterationQty() {
        return iterationQty;
    }
}