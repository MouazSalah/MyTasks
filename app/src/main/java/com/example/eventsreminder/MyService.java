package com.example.eventsreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.eventsreminder.RoomClasses.NoteAdapter;

public class MyService extends Service
{
   Calendar calendar = Calendar.getInstance();
   int hour, minute, day, month, year;

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d("on bind" , "onBin method");
        Log.d("size bind view" , String.valueOf(NoteAdapter.notesList.size()));
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                getCurrentTime();
                Log.d("listSize= " , String.valueOf(MainActivity.noteList.size()));

                for (int i=0 ; i < MainActivity.noteList.size(); i++)
                {
                    Log.d("current hour: ", String.valueOf(hour) + " note hour: "  + String.valueOf(MainActivity.noteList.get(0).getHour()));
                    Log.d("current minute: ", String.valueOf(minute) + " note minute: "  + String.valueOf(MainActivity.noteList.get(0).getMinute()));
                    Log.d("current day: ", String.valueOf(day) + " note day: "  + String.valueOf(MainActivity.noteList.get(0).getDay()));
                    Log.d("current month: ", String.valueOf(month) + " note month: "  + String.valueOf(MainActivity.noteList.get(0).getMonth()));
                    Log.d("current year: ", String.valueOf(year) + " note year: "  + String.valueOf(MainActivity.noteList.get(0).getYear()));


                    if (hour == NoteAdapter.notesList.get(i).getHour() && minute == NoteAdapter.notesList.get(i).getMinute()
                            && day == NoteAdapter.notesList.get(i).getDay() && month == NoteAdapter.notesList.get(i).getMonth())
                    {

                        Log.d("my service", "get alarm now");
                        CreateNotification();

                    }
                }
            }
        };

        timer.schedule(timerTask, 0, 10000);

        return super.onStartCommand(intent, flags, startId);
    }

    private void getCurrentTime()
    {
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = (calendar.get(Calendar.MONTH)) + 1;
        year = calendar.get(Calendar.YEAR);
    }



    public void CreateNotification()
    {
        Log.d("notification" , "done");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Task Reminder")
                .setContentText("one").setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(soundUri).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        notificationManager.notify(0, noti);
    }


}
