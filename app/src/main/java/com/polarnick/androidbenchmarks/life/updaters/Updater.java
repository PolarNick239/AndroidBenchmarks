package com.polarnick.androidbenchmarks.life.updaters;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public interface Updater {

    void setup(int width, int height, int n);
    int[][] next();

}
