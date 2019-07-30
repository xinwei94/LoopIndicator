package com.xinwei.loopindicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.xinwei.loopindicator.view.VaryImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private VaryImageView mGobletVaryImageView;//酒杯变换
    private SeekBar mGobletSeekBar;

    private VaryImageView mBatteryVaryImageView;//电池变换
    private SeekBar mBatterySeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //酒杯相关
        mGobletVaryImageView = (VaryImageView) findViewById(R.id.vary_imageview_goblet);
        mGobletVaryImageView.setImageRes(R.mipmap.ic_goblet_full, R.mipmap.ic_goblet_null);

        mGobletSeekBar = (SeekBar) findViewById(R.id.seek_bar_goblet);
        mGobletSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mGobletVaryImageView.setPosition((progress - 10) / 10f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //电池相关
        mBatteryVaryImageView = (VaryImageView) findViewById(R.id.vary_imageview_battery);
        mBatteryVaryImageView.setImageRes(R.mipmap.ic_battery_full, R.mipmap.ic_battery_null);

        mBatterySeekBar = (SeekBar) findViewById(R.id.seek_bar_battery);
        mBatterySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBatteryVaryImageView.setPosition((progress - 10) / 10f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
