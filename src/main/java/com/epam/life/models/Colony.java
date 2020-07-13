package com.epam.life.models;

public class Colony {
    private static Integer m;
    private static Integer n;

    private Bacterium[][] colony;

    public Colony() {
    }

    public Colony(int m, int n) {
        colony = new Bacterium[m][n];
        this.m = m;
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public Bacterium getBacterium(int row, int column) {
        return colony[row][column];
    }

    public boolean createBacterium(int row, int column) {
        if (colony[row][column] == null) {
            colony[row][column] = new Bacterium();
            return true;
        }

        return false;
    }

    public boolean createBacterium(int row, int column, int x, int y) {
        if (colony[row][column] == null) {
            colony[row][column] = new Bacterium();
            colony[row][column].setX(x);
            colony[row][column].setY(y);
            return true;
        }

        return false;
    }

    public void clearCell(int row, int column) {
        colony[row][column] = null;
    }

    public void clear() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                clearCell(i, j);
            }
        }
    }

    public boolean isEmpty() {
        int qty = m * n;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
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

    public int getNeighboursQty(int row, int column) {
        int topBound = row;
        int bottomBound = row;
        int leftBound = column;
        int rightBound = column;

        if (topBound - 1 > -1) {
            topBound -= 1;
        }
        if (bottomBound + 2 <= m) {
            bottomBound += 2;
        } else {
            bottomBound += 1;
        }
        if (leftBound - 1 > -1) {
            leftBound -= 1;
        }
        if (rightBound + 2 <= n) {
            rightBound += 2;
        } else {
            rightBound += 1;
        }

        int qty = 0;
        for (int i = topBound; i < bottomBound; i++) {
            for (int j = leftBound; j < rightBound; j++) {
                if (!(i == row && j == column) && colony[i][j] != null) {
                    qty++;
                }
            }
        }

        return qty;
    }
}
