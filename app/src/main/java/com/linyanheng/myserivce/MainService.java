package com.linyanheng.myserivce;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

/**
 * Created by linyanheng on 2018/3/13.
 */

public class MainService extends Service {

    private final static  int NOTIFICATION_ID = 0;
    public final static String ACTION_SERVICE_START = "myJacky";

    private PowerManager.WakeLock wakeLock;
    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"myWake");
        wakeLock.acquire();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
          super.onStartCommand(intent, flags, startId);
          sendBroadcast(new Intent(ACTION_SERVICE_START));
          showNotification("Service Start");
          return  START_STICKY;

    }

    @Override
    public void onDestroy() {
        showNotification("Service Stop");
        wakeLock.release();
        super.onDestroy();
    }

    private void showNotification(String s) {
        Intent it = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,it,0);
        Notification.Builder builder = new Notification.Builder(this);
        Notification notification = builder.setTicker(s)
                .setContentTitle(s).setContentText(s)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true).setContentIntent(pendingIntent).build();
        notificationManager.notify(NOTIFICATION_ID,notification);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
