package com.android.alarmnotification180522.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.alarmnotification180522.R;
import com.android.alarmnotification180522.service.AlarmReceiver;
import com.android.alarmnotification180522.utilities.Common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView txtInfoAlarm;
    private Button btnDialog, btnCancel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addEvent();
    }

    private void init() {
        this.txtInfoAlarm = findViewById(R.id.txt_info_alarm);
        this.btnDialog = findViewById(R.id.btn_dialog);
        this.btnCancel = findViewById(R.id.btn_cancel);
    }

    public Intent getMyIntent(Context context, Class<?> cls) {
        if (this.intent == null) {
            this.intent = new Intent(context, cls);
        }
        return this.intent;
    }

    private String FormatDate(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    private void updateUI(Calendar calendar) {
        String dateText = FormatDate("dd/MM/yyyy HH:mm:ss", calendar.getTime());
        this.txtInfoAlarm.setText(dateText);
    }

    private PendingIntent handlePendingIntent(Context context, Intent intent) {
        return PendingIntent.getBroadcast(context, 1, intent, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void starAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent =
                handlePendingIntent(MainActivity.this,
                        getMyIntent(MainActivity.this, AlarmReceiver.class));

        //Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private Calendar handleTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                calendar.set(Calendar.SECOND, 0);

                String dateText = FormatDate("dd/MM/yyyy HH:mm:ss", calendar.getTime());

                Log.d(Common.LOG_TAG, "onTimeSet: " + dateText);

                updateUI(calendar);
                //starAlarm(calendar);
            }
        }, hour, minute, true);

        timePickerDialog.show();

        return calendar;
    }

    private void handleAlarmCancel() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent =
                handlePendingIntent(MainActivity.this,
                        getMyIntent(MainActivity.this, AlarmReceiver.class));

        alarmManager.cancel(pendingIntent);
        this.txtInfoAlarm.setText("Alarm cancel!!!");
    }

    private void handlePressed(AppCompatButton appCompatButton) {
        appCompatButton.setSelected(!appCompatButton.isSelected());
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnMon, btnTue, btnWed, btnThu, btnFri, btnSat, btnSun;

        btnMon = dialog.findViewById(R.id.btn_mon);
        btnTue = dialog.findViewById(R.id.btn_tue);
        btnWed = dialog.findViewById(R.id.btn_wed);
        btnThu = dialog.findViewById(R.id.btn_thu);
        btnFri = dialog.findViewById(R.id.btn_fri);
        btnSat = dialog.findViewById(R.id.btn_sat);
        btnSun = dialog.findViewById(R.id.btn_sun);
        TextView txtTimePicker = dialog.findViewById(R.id.reminder_habit);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnMon);
            }
        });

        btnTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnTue);
            }
        });

        btnWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnWed);
            }
        });

        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnThu);
            }
        });

        btnFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnFri);
            }
        });

        btnSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnSat);
            }
        });

        btnSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnSun);
            }
        });

        txtTimePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                handleTimePickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Save habit", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addEvent() {
        this.btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAlarmCancel();
            }
        });
    }
}