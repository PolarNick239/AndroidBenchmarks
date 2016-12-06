package com.polarnick.androidbenchmarks.life.updaters;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public class MultithreadedUpdater extends Updater {

    private int[] state = null;
    private int[] nextState = null;

    static final int nthreads = Runtime.getRuntime().availableProcessors() + 1; // узнаем число доступных ядер процессоров
    static final ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1); // создаем потоки для вычислений

    @Override
    public String getName() {
        return "x" + nthreads + " Multithreaded";
    }

    @Override
    public void setup(int width, int height, int n) {
        super.setup(width, height, n);

        this.state = new int[height * width];
        this.nextState = new int[height * width];

        Random r = new Random(239);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                state[y * width + x] = r.nextInt(n);
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
    public int[] next() {
        update();
        swapBuffers();
        return state;
    }

    private void swapBuffers() {
        int[] tmp = state;
        state = nextState;
        nextState = tmp;
    }

    private void update() {
        int nthreads = Runtime.getRuntime().availableProcessors() + 1;
        final CountDownLatch latch = new CountDownLatch(nthreads); // счетчик выполненной работы

        for (int ithread = 0; ithread < nthreads; ++ithread) {
            executors.execute(new Runnable() {
                @Override
                public void run() {

                    throw new UnsupportedOperationException();

                    // 2.2 TODO заменить однопоточные вычисления многопоточными - считая в каждом потоке ровно его часть работы
                    // update(state, nextState, width, height, n, ...);

                    // latch.countDown(); // уменьшаем счетчик - т.е. этот поток уже свою работу посчитал
                }
            });
        }

        // теперь нужно дождаться выполнения всех частей работы:
        // latch.await(); // 2.3 TODO прокинуть ошибку вверх
    }

    private static void update(int[] cur, int[] next, int width, int height, int n) {

        throw new UnsupportedOperationException();

        // 2.1 TODO адаптировать эту функцию так, чтобы вычислительную работу можно было распараллелить (передавать через аргументы область работы)

        // скопировать сюда код из SimpleUpdater.update(...)
    }

}