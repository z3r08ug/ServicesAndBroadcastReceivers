package dev.uublabs.servicesandbroadcastreceivers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.uublabs.servicesandbroadcastreceivers.receivers.MyDynamicReceiver;
import dev.uublabs.servicesandbroadcastreceivers.services.MyBoundService;
import dev.uublabs.servicesandbroadcastreceivers.services.MyIntentService;
import dev.uublabs.servicesandbroadcastreceivers.services.MyStartedService;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.btnStartStartedService)
    Button btnStartStartedService;
    @BindView(R.id.btnStopStartedService)
    Button btnStopStartedService;
    @BindView(R.id.btnIntentService)
    Button btnIntentService;
    @BindView(R.id.btnBindService)
    Button btnBindService;
    @BindView(R.id.tvBindServiceData)
    TextView tvBindServiceData;
    @BindView(R.id.btnUnbindService)
    Button btnUnbindService;
    @BindView(R.id.btnGetBoundData)
    Button btnGetBoundData;
    @BindView(R.id.etSendData)
    EditText etSendData;
    @BindView(R.id.btnSendBroadcast)
    Button btnSendBroadcast;
    @BindView(R.id.tvBRData)
    TextView tvBRData;
    private MyDynamicReceiver myDynamicReceiver;
    private static MainActivity ins;
    private MyBoundService myBoundService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ins = this;

        myDynamicReceiver = new MyDynamicReceiver(tvBRData);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION.ACTION1);
        registerReceiver(myDynamicReceiver, intentFilter);
    }

    @OnClick({R.id.btnStartStartedService, R.id.btnStopStartedService, R.id.btnIntentService, R.id.btnBindService, R.id.btnUnbindService, R.id.btnSendBroadcast, R.id.btnGetBoundData})
    public void onViewClicked(View view)
    {
        Intent startedIntent = new Intent(this, MyStartedService.class);

        Intent boundIntent = new Intent(this, MyBoundService.class);
        switch (view.getId())
        {
            case R.id.btnStartStartedService:
                startService(startedIntent);
                break;
            case R.id.btnStopStartedService:
                stopService(startedIntent);
                break;
            case R.id.btnIntentService:
                Intent intent = new Intent(this, MyIntentService.class);
                startService(intent);
            case R.id.btnBindService:
                bindService(boundIntent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindService:
                if (isBound)
                    unbindService(serviceConnection);
                onServiceUnbind();
                break;
            case R.id.btnSendBroadcast:
                Intent brIntent = new Intent("sendToOtherApp");
                brIntent.putExtra("data", etSendData.getText().toString());
                //sends broadcast within application only
//                LocalBroadcastManager.getInstance(this).sendBroadcast(brIntent);
                sendBroadcast(brIntent);
                break;
            case R.id.btnGetBoundData:
                if (isBound)
                    tvBindServiceData.setText(myBoundService.getData());
                else
                    tvBindServiceData.setText("Default Data");
                break;
        }
    }

    private boolean isBound;
    ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            isBound = true;
            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) service;
            myBoundService = myBinder.getService();
            myBoundService.initData();
            Toast.makeText(MainActivity.this, myBoundService.getData(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    };

    private void onServiceUnbind()
    {
        isBound = false;
        Toast.makeText(MainActivity.this, "Unbounded", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(myDynamicReceiver);
    }

    public static MainActivity  getInstace()
    {
        return ins;
    }

    public void updateTheTextView(final String t)
    {
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                EditText etBroadcast = findViewById(R.id.etSendData);
                etBroadcast.setText(t);
            }
        });
    }
}
