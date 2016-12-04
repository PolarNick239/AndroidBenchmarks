package com.polarnick.androidbenchmarks;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.polarnick.androidbenchmarks.life.LifeSurfaceHolder;

public class MainActivity extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LifeSurfaceHolder surfaceHolder = new LifeSurfaceHolder();

        SurfaceView surface = (SurfaceView) findViewById(R.id.svMain);
        surface.setWillNotDraw(false);
        surface.getHolder().addCallback(surfaceHolder);
        surface.setOnClickListener(surfaceHolder);
    }

}
