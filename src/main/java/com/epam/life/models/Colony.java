package com.epam.life.models;

public class Colony {
    private Integer columnQty;
    private Integer rowQty;

    private Bacterium[][] colony;

    public Colony() {
    }

    public Colony(int columnQty, int rowQty) {
        colony = new Bacterium[columnQty][rowQty];
        this.columnQty = columnQty;
        this.rowQty = rowQty;
    }

    public int getColumnQty() {
        return columnQty;
    }

    public int getRowQty() {
        return rowQty;
    }

    public Bacterium getBacterium(int column, int row) {
        return colony[column][row];
    }

    public boolean createBacterium(int column, int row) {
        if (colony[column][row] == null) {
            colony[column][row] = new Bacterium();
            return true;
        }

        return false;
    }

    public boolean createBacterium(int column, int row, int x, int y) {
        if (colony[column][row] == null) {
            colony[column][row] = new Bacterium();
            colony[column][row].setX(x);
            colony[column][row].setY(y);
            return true;
        }

        return false;
    }

    public void clearCell(int column, int row) {
        colony[column][row] = null;
    }

    public void clear() {
        for (int i = 0; i < columnQty; i++) {
            for (int j = 0; j < rowQty; j++) {
                clearCell(i, j);
            }
        }
    }

    public boolean isEmpty() {
        int qty = columnQty * rowQty;
        for (int i = 0; i < columnQty; i++) {
            for (int j = 0; j < rowQty; j++) {
                if(colony[i][j] == null) {
                    qty--;
                }
            }
        }

        if(qty == 0) {
            return true;
        }

        return false;
    }

    public int getNeighboursQty(int column, int row) {
        int topBound = row;
        int bottomBound = row;
        int leftBound = column;
        int rightBound = column;

        if (topBound - 1 > -1) {
            topBound -= 1;
        }
        if (bottomBound + 2 <= rowQty) {
            bottomBound += 2;
        } else {
            bottomBound += 1;
        }
        if (leftBound - 1 > -1) {
            leftBound -= 1;
        }
        if (rightBound + 2 <= columnQty) {
            rightBound += 2;
        } else {
            rightBound += 1;
        }

        int qty = 0;
        for (int i = leftBound; i < rightBound; i++) {
            for (int j = topBound; j < bottomBound; j++) {
                if (!(i == column && j == row) && colony[i][j] != null) {
                    qty++;
                }
            }
        }

        return qty;
    }
}