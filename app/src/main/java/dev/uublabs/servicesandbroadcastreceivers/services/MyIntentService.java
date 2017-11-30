package dev.uublabs.servicesandbroadcastreceivers.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import dev.uublabs.servicesandbroadcastreceivers.Constants;

public class MyIntentService extends IntentService
{

    private static final String TAG = MyIntentService.class.getSimpleName() + "_TAG";

    public MyIntentService()
    {
        super("MyIntentService");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d(TAG, "onHandleIntent: ");
        Intent intent1 = new Intent();
        intent1.setAction(Constants.ACTION.ACTION1);
        intent1.putExtra(Constants.KEYS.DATA_MAIN, "Data");
        sendBroadcast(intent1);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
