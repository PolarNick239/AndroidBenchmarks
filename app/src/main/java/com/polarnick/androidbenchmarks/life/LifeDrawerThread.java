package com.polarnick.androidbenchmarks.life;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.polarnick.androidbenchmarks.life.updaters.Updater;
import com.polarnick.androidbenchmarks.life.updaters.SimpleUpdater;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Polyarniy Nikolay, 02.12.16
 */

public class LifeDrawerThread implements Runnable {

    private static final int DEFAULT_STATES_NUMBER = 15;

    private final SurfaceHolder surfaceHolder;
    private final List<Updater> updaters;
    private volatile int curUpdater;

    private volatile boolean running = true;

    private final int n;
    private final int[] colorsPalette;

    private Bitmap img = null;

    public LifeDrawerThread(SurfaceHolder surfaceHolder) {
        this(surfaceHolder, DEFAULT_STATES_NUMBER);
    }

    public LifeDrawerThread(SurfaceHolder surfaceHolder, int n) {
        this.surfaceHolder = surfaceHolder;

        this.n = n;
        this.colorsPalette = generateColors(n);
        this.updaters = Arrays.<Updater>asList(new SimpleUpdater());
        this.curUpdater = 0;
    }

    private static int[] generateColors(int n) {
        int[] colors = new int[n];

        Random r = new Random(239);
        for (int i = 0; i < n; ++i) {
            // TODO* генерировать симпатичную палитру :)
            // Например тут можно подглядеть: http://stackoverflow.com/questions/43044/algorithm-to-randomly-generate-an-aesthetically-pleasing-color-palette
            colors[i] = color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }
        return colors;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas(null);
            Updater updater = updaters.get(curUpdater);

            if (canvas == null) {
                stop();
                break;
            }

            if (img == null || img.getWidth() != canvas.getWidth() || img.getHeight() != canvas.getHeight()) {
                img = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            }
            if (img.getWidth() != updater.getWidth() || img.getHeight() != updater.getHeight()) {
                for (Updater u : updaters) {
                    u.cleanup();
                }
                updater.setup(img.getWidth(), img.getHeight(), n);
            }

            int[] state = updater.next(); // 1.3 TODO сохранить время вычисления функции updater.next() в переменную passed (System.currentTimeMillis())
            long passed = ...;

            draw(state, colorsPalette, img);

            canvas.drawBitmap(img, 0f, 0f, null);

            float megapixelsPerSec = canvas.getHeight() * canvas.getWidth() * 1000.0f / 1000_000.0f / passed;
            String message = passed + " ms, " + String.format(java.util.Locale.US, "%.2f", megapixelsPerSec) + " MP/s";
            message += " - " + updater.getName();
            drawText(message, canvas);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawText(String message, Canvas canvas) {
        float textSize = 30f;

        Paint fillPaint = new Paint();
        fillPaint.setTextSize(textSize);
        fillPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(Color.BLACK);
        fillPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(message, 20, 40, fillPaint);

        Paint strokePaint = new Paint();
        strokePaint.setTextSize(textSize);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1f);
        canvas.drawText(message, 20, 40, strokePaint);
    }

    public void stop() {
        running = false;
    }

    public void nextUpdater() {
        // 2.4 TODO сделать так, чтобы начал использоваться другой метод вычисления + надо как-то добавить в перечень алгоритмов - MultithreadedUpdater
        // curUpdater = ...;
    }

    private static int color(int r, int g, int b) {
        return color(r, g, b, 255);
    }

    private static int color(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private native void draw(int[] states, int[] colorsPalette, Bitmap img);

}
