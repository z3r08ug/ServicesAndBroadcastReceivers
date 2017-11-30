package dev.uublabs.servicesandbroadcastreceivers.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyBoundService extends Service
{

    private String data;

    public MyBoundService()
    {

    }
    IBinder iBinder = new MyBinder();

    public class MyBinder extends Binder
    {
        public  MyBoundService getService()
        {
            return MyBoundService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        return iBinder;
    }

    public void initData()
    {
        data = "Data from the server";
    }

    public String getData()
    {
        return data;
    }
}
