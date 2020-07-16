package com.epam.life.common;

public class GameConfig {
    public static GameConfig instance;

    private Integer columnQty;
    private Integer rowQty;
    private Integer iterationQty;

    private GameConfig() {
    }

    private GameConfig(int columnQty, int rowQty, int iterationQty) {
        this.columnQty = columnQty;
        this.rowQty = rowQty;
        this.iterationQty = iterationQty;
    }

    public static void createInstance(int columnQty, int rowQty, int iterationQty) {
        if (instance == null) {
            instance = new GameConfig(columnQty, rowQty, iterationQty);
        }
    }

    public static GameConfig getInstance() {
        return instance;
    }

    public Integer getColumnQty() {
        return columnQty;
    }

    public Integer getRowQty() {
        return rowQty;
    }

    public Integer getIterationQty() {
        return iterationQty;
    }
}