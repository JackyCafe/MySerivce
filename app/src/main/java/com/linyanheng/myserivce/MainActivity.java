package com.linyanheng.myserivce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnStart,btnStop;
    private MyReciver reciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        resetLayout(false);
        registerMyReciver();
    }

    public void onStartClick(View view) {
        Intent it = new Intent(this,MainService.class);
        startService(it);
        resetLayout(true);

    }

    public void onStopClick(View view) {
        Intent it = new Intent(this,MainService.class);
        stopService(it);
        resetLayout(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    class MyReciver  extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showToast("Service Start");
        }


    }

    private void registerMyReciver() {
        IntentFilter filter = new IntentFilter(MainService.ACTION_SERVICE_START);
        reciver = new MyReciver();
        registerReceiver(reciver,filter);

    }


    private void showToast(String s) {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();


    }



    private void resetLayout(boolean isActive) {
        if (isActive)
        {
            btnStart.setVisibility(View.GONE);
            btnStop.setVisibility(View.VISIBLE);
        }else{
            btnStop.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);
        }
    }

    private void findViews() {
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
    }
}
