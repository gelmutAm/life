package com.epam.life.common;

public class GameConfig {
    public static GameConfig instance;

    private Integer m;
    private Integer n;
    private Integer t;

    private GameConfig() {
    }

    private GameConfig(int m, int n, int t) {
        this.m = m;
        this.n = n;
        this.t = t;
    }

    public static void createInstance(int m, int n, int t) {
        if (instance == null) {
            instance = new GameConfig(m, n, t);
        }
    }

    public static GameConfig getInstance() {
        return instance;
    }

    public static Integer getM() {
        return instance.m;
    }

    public static Integer getN() {
        return instance.n;
    }

    public static Integer getT() {
        return instance.t;
    }
}
