package com.polarnick.androidbenchmarks.life;

import android.view.SurfaceHolder;

/**
 * Polyarniy Nikolay, 02.12.16
 */

public class LifeSurfaceHolder implements SurfaceHolder.Callback {

    LifeDrawerThread drawer;

    public LifeSurfaceHolder() {
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        drawer = new LifeDrawerThread(surfaceHolder);
        Thread thread = new Thread(drawer);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        drawer.stop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }
}
