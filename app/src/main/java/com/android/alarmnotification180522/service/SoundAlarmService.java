package com.android.alarmnotification180522.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.alarmnotification180522.R;
import com.android.alarmnotification180522.utilities.Common;
import com.android.alarmnotification180522.utilities.MyIntent;
import com.android.alarmnotification180522.utilities.Notification;
import com.android.alarmnotification180522.utilities.SoundAlarm;

public class SoundAlarmService extends Service {
    private SoundAlarm soundAlarm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Common.LOG_TAG, "onStartCommand: Sound alarm");

        Intent myIntent = MyIntent.getMyIntent(SoundAlarmService.this, NotificationCanceler.class);
        this.soundAlarm = new SoundAlarm();

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(SoundAlarmService.this, 1, myIntent, 0);

        Notification notification = new Notification(SoundAlarmService.this);
        NotificationCompat.Builder notifyBuilder = notification.getNotificationBuilder();

        notifyBuilder.addAction(R.drawable.ic_launcher_foreground, "Button", pendingIntent);

        notification.getNotificationManager().notify(1, notifyBuilder.build());

        this.soundAlarm.playMusic(SoundAlarmService.this, R.raw.alarm);
        myIntent.putExtra(Common.MUSIC, this.soundAlarm);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.soundAlarm.stopMusic();

        Log.d(Common.LOG_TAG, "onDestroy: stop music");
    }
}
