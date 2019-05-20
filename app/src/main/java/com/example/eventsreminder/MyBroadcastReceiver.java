package com.example.eventsreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("start" , "broadcast");
        Intent serviceIntent = new Intent(context,MyBroadcastReceiver.class);
        context.startService(serviceIntent);

    }
}
