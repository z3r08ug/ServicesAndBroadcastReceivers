package dev.uublabs.servicesandbroadcastreceivers.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import dev.uublabs.servicesandbroadcastreceivers.Constants;
import dev.uublabs.servicesandbroadcastreceivers.MainActivity;

public class MyDynamicReceiver extends BroadcastReceiver
{
    TextView textView;
    public MyDynamicReceiver(TextView tv)
    {
        textView = tv;
    }
    MyDynamicReceiver()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();



        switch (action)
        {
            case Constants.ACTION.ACTION1:
            {
                String data = intent.getStringExtra(Constants.KEYS.DATA_MAIN);

                for (int i = 0; i < 5; i++)
                {
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

                textView.setText(data);
//                try
//                {
//                    MainActivity.getInstace().updateTheTextView(data);
//                }
//                catch (Exception e)
//                {
//
//                }
                break;
            }
        }
    }
}
