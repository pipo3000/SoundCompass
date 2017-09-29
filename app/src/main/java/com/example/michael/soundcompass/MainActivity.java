package com.example.michael.soundcompass;

import android.content.pm.ActivityInfo;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView redArrow, blueArrow;
    private Button b;
    public TextView text1;
    private GCCphat gcc;

    private boolean running = false;
    private float angle = 0f;
    private final int px = 0, py = 400;
    private final double T = .1f;
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.button);
        text1 = (TextView) findViewById(R.id.text1);
        redArrow = (ImageView) findViewById(R.id.imageView);
        blueArrow = (ImageView) findViewById(R.id.imageView2);
        redArrow.setScaleType(ImageView.ScaleType.MATRIX);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        //gccbtn = (RadioButton) findViewById(R.id.GCC);
        //aedbtn = (RadioButton) findViewById(R.id.AED);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    if (type != -1) {
                        running = true;
                        ((TextView) v).setText("Deactivate Compass");
                        startCompass();
                    }
                } else {
                    running = false;
                    ((TextView) v).setText("Activate Compass");
                    stopCompass();
                }
            }
        });
    }

    private void startCompass(){
        try {
            if (gcc != null) {
                gcc.cancel(true);
                gcc = null;
            }
            gcc = new GCCphat(redArrow, blueArrow, text1, T, type);
            gcc.execute();
            //changeAngle(10f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopCompass() {
        gcc.cancel(true);
        gcc = null;
    }

    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.GCC:
                if (checked) {
                    type = 1;
                    if (gcc != null)
                         gcc.setMode(type);
                } else {
                    type = -1;
                }
                break;
            case R.id.AED:
                if (checked) {
                    type  = 2;
                    if (gcc != null)
                        gcc.setMode(type);
                } else {
                    type = -1;
                }
                break;
            case R.id.prop:
                if (checked) {
                    type  = 3;
                    if (gcc != null)
                        gcc.setMode(type);
                } else {
                    type = -1;
                }
                break;
        }
    }
}