package com.epam.life.models;

/**
 * <code>Colony</code> is a bacterial colony.
 */
public class Colony {
    private int columnQty;
    private int rowQty;

    private Bacterium[][] colony;

    public Colony() {
    }

    /**
     * Constructs an empty colony with the specified number of columns and rows.
     *
     * @param columnQty number of columns in this colony
     * @param rowQty    number of rows in this colony
     */
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

    /**
     * Creates the bacterium in this colony at the specified position.
     *
     * @param column column index at which the bacterium is to be created
     * @param row    row index at which the bacterium is to be created
     * @return {@code true} if bacterium is created.
     */
    public boolean createBacterium(int column, int row) {
        if (colony[column][row] == null) {
            colony[column][row] = new Bacterium();
            return true;
        }

        return false;
    }

    /**
     * Creates the bacterium in this colony at specified position with known coordinates.
     *
     * @param column column index at which the bacterium is to be created
     * @param row    row index at which the bacterium is to be created
     * @param x      x-coordinate of the bacterium
     * @param y      y-coordinate of the bacterium
     * @return {@code true} if the bacterium in this colony is created.
     */
    public boolean createBacterium(int column, int row, int x, int y) {
        if (colony[column][row] == null) {
            colony[column][row] = new Bacterium();
            colony[column][row].setX(x);
            colony[column][row].setY(y);
            return true;
        }

        return false;
    }

    /**
     * Removes the bacterium from the specified position in this colony.
     *
     * @param column column index at which the bacterium is to be removed
     * @param row    row index at which the bacterium is to be removed
     */
    public void clearCell(int column, int row) {
        colony[column][row] = null;
    }

    /**
     * Removes all bacteria from this colony.
     */
    public void clear() {
        for (int i = 0; i < columnQty; i++) {
            for (int j = 0; j < rowQty; j++) {
                clearCell(i, j);
            }
        }
    }

    /**
     * Returns {@code true} if this colony contains no bacteria.
     *
     * @return {@code true} if this colony contains no bacteria.
     */
    public boolean isEmpty() {
        int qty = columnQty * rowQty;
        for (int i = 0; i < columnQty; i++) {
            for (int j = 0; j < rowQty; j++) {
                if (colony[i][j] == null) {
                    qty--;
                }
            }
        }

        return qty == 0;
    }

    /**
     * Returns the number of bacterium neighbours at the specified position in this colony.
     *
     * @param column column index of the bacterium in this colony
     * @param row    row index of the bacterium in this colony
     * @return the number of bacterium neighbours at the specified position in this colony.
     */
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