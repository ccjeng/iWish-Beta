package com.ccjeng.iwish.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ccjeng.iwish.R;
import com.ccjeng.iwish.controller.Speaker;
import com.ccjeng.iwish.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.coordinatorlayout) public CoordinatorLayout coordinatorlayout;
    @Bind(R.id.aac) Button btnAAC;
    @Bind(R.id.frequency) Button btnFrequency;
    @Bind(R.id.daily) Button btnDaily;

    private final int CHECK_CODE = 0x1;
    private Speaker speaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initComponents();

        checkTTS();

    }

    @OnClick(R.id.aac)
    public void gotoCategoryActivity(View view) {
        if (speaker != null) {
            if (speaker.isAllowed()) {
                speaker.speak(getString(R.string.aac));
            }
        }
        startActivity(new Intent(MainActivity.this, CategoryActivity.class));
    }
    @OnClick(R.id.daily)
    public void gotoDailyActivity(View view) {
        if (speaker != null) {
            if (speaker.isAllowed()) {
                speaker.speak(getString(R.string.daily));
            }
        }
        startActivity(new Intent(MainActivity.this, DailyActivity.class));
    }

    @OnClick(R.id.frequency)
    public void gotoFrequencyActivity(View view) {
        if (speaker != null) {
            if (speaker.isAllowed()) {
                speaker.speak(getString(R.string.frequency));
            }
        }
        startActivity(new Intent(MainActivity.this, FrequencyActivity.class));
    }

    private void initComponents() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
        }

        btnAAC.setTextSize(getFontSize());
        btnDaily.setTextSize(getFontSize());
        btnFrequency.setTextSize(getFontSize());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speaker != null)
            speaker.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                speaker = new Speaker(this);
                speaker.allow(true);
            } else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }
    }

}
