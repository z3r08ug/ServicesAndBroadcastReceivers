package dev.uublabs.myreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyStaticReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "MyReceiver: " + intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();
    }
}
