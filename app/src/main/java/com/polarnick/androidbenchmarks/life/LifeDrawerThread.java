package com.polarnick.androidbenchmarks.life;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Polyarniy Nikolay, 02.12.16
 */

public class LifeDrawerThread implements Runnable {

    private static final int DEFAULT_STATES_NUMBER = 15;

    private final SurfaceHolder surfaceHolder;

    private volatile boolean running = true;

    private final int n;
    private final int[] colorsPalette;

    private Bitmap img = null;
    private int[][] state = null;
    private int[][] nextState = null;

    public LifeDrawerThread(SurfaceHolder surfaceHolder) {
        this(surfaceHolder, DEFAULT_STATES_NUMBER);
    }

    public LifeDrawerThread(SurfaceHolder surfaceHolder, int n) {
        this.surfaceHolder = surfaceHolder;

        this.n = n;
        this.colorsPalette = generateColors(n);
    }

    private static int[] generateColors(int n) {
        int[] colors = new int[n];

        Random r = new Random(239);
        for (int i = 0; i < n; ++i) {
            colors[i] = color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }
        return colors;
    }

    private void initBuffers(int width, int height) {
        img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        state = new int[height][width];
        nextState = new int[height][width];

        Random r = new Random(239);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                state[y][x] = r.nextInt(n);
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas(null);

            if (canvas == null) {
                stop();
                break;
            }

            if (img == null || img.getWidth() != canvas.getWidth() || img.getHeight() != canvas.getHeight()) {
                initBuffers(canvas.getWidth(), canvas.getHeight());
            }

            long from = System.currentTimeMillis();

            update(state, nextState, n);
            swapBuffers();
            draw(state, colorsPalette, img);

            long to = System.currentTimeMillis();
            long passed = Math.max(1, to - from);

            canvas.drawBitmap(img, 0f, 0f, null);

            float megapixelsPerSec = canvas.getHeight() * canvas.getWidth() * 1000.0f / 1000_000.0f / passed;
            String message = passed + " ms, " + String.format(java.util.Locale.US, "%.2f", megapixelsPerSec) + " MP/s";
            drawText(message, canvas);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawText(String message, Canvas canvas) {
        float textSize = 30f;

        Paint fillPaint = new Paint();
        fillPaint.setTextSize(textSize);
        fillPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(Color.WHITE);
        fillPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(message, 20, 40, fillPaint);

        Paint strokePaint = new Paint();
        strokePaint.setTextSize(textSize);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1f);
        canvas.drawText(message, 20, 40, strokePaint);
    }

    public void stop() {
        running = false;
    }

    private static int color(int r, int g, int b) {
        return color(r, g, b, 255);
    }

    private static int color(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private static void update(int[][] cur, int[][] next, int n) {
        int height = cur.length;
        int width = cur[0].length;

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

    private void swapBuffers() {
        int[][] tmp = state;
        state = nextState;
        nextState = tmp;
    }

    private void draw(int[][] states, int[] colorsPalette, Bitmap img) {
        for (int y = 0; y < img.getHeight(); ++y) {
            for (int x = 0; x < img.getWidth(); ++x) {
                img.setPixel(x, y, colorsPalette[states[y][x]]);
            }
        }
    }

}
