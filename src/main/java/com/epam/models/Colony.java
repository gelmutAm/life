package com.epam.models;

public class Colony {
    private Bacterium[][] colony;
    private static Integer m;
    private static Integer n;

    public Colony() {
    }

    public Colony(int m, int n) {
        colony = new Bacterium[m][n];
        this.m = m;
        this.n = n;
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
}
