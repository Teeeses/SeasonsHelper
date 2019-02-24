package com.explead.seasonhelper.common.beans;

/**
 * Created by develop on 20.01.2017.
 */

public class Level {

    public static int SUMMER = 0, WINTER = 1;
    private int mode;

    private int level;

    public Level(int mode, int level) {
        this.mode = mode;
        this.level = level;
    }

    public int getMode() {
        return mode;
    }

    public int getLevel() {
        return level;
    }
}
