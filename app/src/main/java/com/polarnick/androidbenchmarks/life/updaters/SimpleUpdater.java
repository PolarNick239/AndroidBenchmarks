package com.polarnick.androidbenchmarks.life.updaters;

import java.util.Random;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public class SimpleUpdater implements Updater {

    private int[][] state = null;
    private int[][] nextState = null;
    private int n;
    private int width;
    private int height;

    @Override
    public void setup(int width, int height, int n) {
        this.width = width;
        this.height = height;
        this.n = n;
        this.state = new int[height][width];
        this.nextState = new int[height][width];

        Random r = new Random(239);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                state[y][x] = r.nextInt(n);
            }
        }
    }

    @Override
    public int[][] next() {
        update(state, nextState, width, height, n);
        swapBuffers();
        return state;
    }

    private void swapBuffers() {
        int[][] tmp = state;
        state = nextState;
        nextState = tmp;
    }

    private static void update(int[][] cur, int[][] next, int width, int height, int n) {
        int dx[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int dy[] = {-1, -1, -1, 0, 1, 1, 1, 0};
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                boolean succeeded = false;

                for (int i = 0; i < dx.length; ++i) {
                    if (x + dx[i] < 0 || x + dx[i] >= width || y + dy[i] < 0 || y + dy[i] >= height) {
                        continue;
                    }
                    if (cur[y + dy[i]][x + dx[i]] == (cur[y][x] + 1) % n) {
                        succeeded = true;
                    }
                }

                if (succeeded) {
                    next[y][x] = (cur[y][x] + 1) % n;
                } else {
                    next[y][x] = cur[y][x];
                }
            }
        }
    }

}
