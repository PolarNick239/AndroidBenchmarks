package com.polarnick.androidbenchmarks.life.updaters;

import java.util.Random;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public class TunedUpdater extends Updater {

    private int[][] state = null;
    private int[][] nextState = null;

    @Override
    public String getName() {
        return "Tuned";
    }

    @Override
    public void setup(int width, int height, int n) {
        super.setup(width, height, n);
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
    public void cleanup() {
        this.width = 0;
        this.height = 0;
        this.n = 0;
        this.state = null;
        this.nextState = null;
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

        // Tunes:
        // 1. Remove branching (corner cases to separate loops)
        // 2. Calculate next state once (not for every neighbour)
        // 3. Break on first succeeding
        for (int y = 1; y < height - 1; ++y) {
            for (int x = 1; x < width - 1; ++x) {
                boolean succeeded = false;

                int toSucceed = (cur[y][x] + 1) % n;
                for (int i = 0; i < dx.length; ++i) {
                    if (cur[y + dy[i]][x + dx[i]] == toSucceed) {
                        succeeded = true;
                        break;
                    }
                }

                if (succeeded) {
                    next[y][x] = toSucceed;
                } else {
                    next[y][x] = cur[y][x];
                }
            }
        }

        // Corner cases
        for (int y : new int[]{0, height - 1}) {
            for (int x : new int[]{0, width - 1}) {
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
