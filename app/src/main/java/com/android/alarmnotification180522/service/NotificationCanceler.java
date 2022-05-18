package com.android.alarmnotification180522.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.alarmnotification180522.utilities.Common;
import com.android.alarmnotification180522.utilities.MyIntent;
import com.android.alarmnotification180522.utilities.SoundAlarm;

public class NotificationCanceler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Common.LOG_TAG, "onReceive: Cancel notification manager");

        Intent myIntent = MyIntent.getMyIntent();
        SoundAlarm soundAlarm = (SoundAlarm) myIntent.getSerializableExtra(Common.MUSIC);

        if(soundAlarm != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent myIntentReceiver = new Intent(context, AlarmReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, myIntentReceiver, 0);

            Intent intentService = new Intent(context, SoundAlarmService.class);

            alarmManager.cancel(pendingIntent);
            context.stopService(intentService);

            Log.d(Common.LOG_TAG, "onReceive: music");
        } else {
            Log.d(Common.LOG_TAG, "onReceive: not music");
        }
    }
}
