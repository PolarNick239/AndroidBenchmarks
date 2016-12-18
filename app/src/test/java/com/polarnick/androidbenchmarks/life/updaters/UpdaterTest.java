package com.polarnick.androidbenchmarks.life.updaters;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Polyarniy Nikolay, 18.12.16
 */
public abstract class UpdaterTest {

    private final int n = 15;

    public abstract Updater newUpdater();

    public void checkCase(int width, int height, int n, int[] before, int[] after) throws Exception {
        Updater updater = newUpdater();

        updater.setup(width, height, n);
        updater.setState(before);

        int[] found = updater.next();

        for (int i = 0; i < width * height; ++i) {
            if (found[i] != after[i]) {
                System.err.println("Результат отличается от ожидаемого на позиции " + i + " (x=" + (i % width) + ", y=" + (i / width) + "):");
                System.err.println("Ожидалось состояние номер " + after[i] + ", но оказалось состояние номер " + found[i] + "!");

                assertArrayEquals(found, after);
            }
        }
    }

    @Test
    public void checkCase1() throws Exception {
        int[] before = new int[]{
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
        };

        int[] after = new int[]{
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
        };

        checkCase(4, 3, n, before, after);
    }

    @Test
    public void checkCase2() throws Exception {
        int[] before = new int[]{
                0, 0, 0, 1,
                0, 0, 0, 0,
                0, 0, 0, 0,
        };

        int[] after = new int[]{
                0, 0, 1, 1,
                0, 0, 1, 1,
                0, 0, 0, 0,
        };

        checkCase(4, 3, n, before, after);
    }

    @Test
    public void checkCase3() throws Exception {
        int[] before = new int[]{
                1, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
        };

        int[] after = new int[]{
                1, 1, 0, 0,
                1, 1, 0, 0,
                0, 0, 0, 0,
        };

        checkCase(4, 3, n, before, after);
    }

    @Test
    public void checkCase4() throws Exception {
        int[] before = new int[]{
                0, 0, 0, 0,
                0, 0, 0, 0,
                1, 0, 0, 0,
        };

        int[] after = new int[]{
                0, 0, 0, 0,
                1, 1, 0, 0,
                1, 1, 0, 0,
        };

        checkCase(4, 3, n, before, after);
    }

    @Test
    public void checkCase5() throws Exception {
        int[] before = new int[]{
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 1,
        };

        int[] after = new int[]{
                0, 0, 0, 0,
                0, 0, 1, 1,
                0, 0, 1, 1,
        };

        checkCase(4, 3, n, before, after);
    }

    @Test
    public void checkCase6() throws Exception {
        int[] before = new int[]{
                0, 0, 0, 0,
                0, 0, 0, n-1,
                0, 0, 0, 0,
        };

        int[] after = new int[]{
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
        };

        checkCase(4, 3, n, before, after);
    }

}
