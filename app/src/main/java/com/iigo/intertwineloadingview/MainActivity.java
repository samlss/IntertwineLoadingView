package com.iigo.intertwineloadingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.iigo.library.IntertwineLoadingView;

public class MainActivity extends AppCompatActivity {
    private IntertwineLoadingView intertwineLoadingView1;
    private IntertwineLoadingView intertwineLoadingView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intertwineLoadingView1 = findViewById(R.id.ilv_loading1);
        intertwineLoadingView2 = findViewById(R.id.ilv_loading2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        intertwineLoadingView1.release();
        intertwineLoadingView2.release();
    }

    public void onStart(View view) {
        intertwineLoadingView1.start();
        intertwineLoadingView2.start();
    }

    public void onStop(View view) {
        intertwineLoadingView1.stop();
        intertwineLoadingView2.stop();
    }

    public void onResume(View view) {
        intertwineLoadingView1.resume();
        intertwineLoadingView2.resume();
    }

    public void onPause(View view) {
        intertwineLoadingView1.pause();
        intertwineLoadingView2.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //to test change the size of view.
//        ViewGroup.LayoutParams layoutParams = intertwineLoadingView1.getLayoutParams();
//        layoutParams.width = 400;
//        layoutParams.height = 400;
//        intertwineLoadingView1.setLayoutParams(layoutParams);

//        intertwineLoadingView1.setDuration(1000);
//        intertwineLoadingView1.setFirstBallColor(Color.RED);
//        intertwineLoadingView1.setSecondBallColor(Color.YELLOW);
        return super.onKeyDown(keyCode, event);
    }
}

