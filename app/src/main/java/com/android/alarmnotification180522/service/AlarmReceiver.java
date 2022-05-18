package com.android.alarmnotification180522.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.alarmnotification180522.R;
import com.android.alarmnotification180522.utilities.Common;
import com.android.alarmnotification180522.utilities.Notification;
import com.android.alarmnotification180522.utilities.SoundAlarm;

public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, SoundAlarmService.class);
        context.startService(myIntent);
    }
}
