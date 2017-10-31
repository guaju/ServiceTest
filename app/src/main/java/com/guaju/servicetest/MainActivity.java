package com.guaju.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Button bt_startservice;
    private Button bt_bindservice;
    private Button bt_unbindservice;
    private Button bt_stopservice;
    private ServiceConnection serviceConnection;
    //-1表示未连接，0表示已连接
    private int isconnected=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_startservice = findViewById(R.id.button);
        bt_bindservice = findViewById(R.id.button2);
        bt_unbindservice = findViewById(R.id.button3);
        bt_stopservice = findViewById(R.id.button4);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e(TAG, "onServiceConnected: ok" );
                IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
                try {
                    iMyAidlInterface.xiaoming("小红红");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


//                MyService.MyBinder myBinder = (MyService.MyBinder) (iBinder);
//                myBinder.sayHello();
//                int count = myBinder.getCount();
//                Log.e(TAG, "count="+count );

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e(TAG, "onServiceDisconnected: ");
            }
        };

        bt_startservice.setOnClickListener(this);
        bt_bindservice.setOnClickListener(this);
        bt_unbindservice.setOnClickListener(this);
        bt_stopservice.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                mstartService();
                break;
            case R.id.button2:
                mBindService();
                break;
            case R.id.button3:
                mUnBindService();
                break;
            case R.id.button4:
                mstopService();
                break;
            default:
                break;
        }
    }

    private void mBindService() {
        Intent intent= new Intent(this,MyService.class);
        //BIND_AUTO_CREATE: 意思就是绑定的时候如果没有开启服务就会开启
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        isconnected=0;
    }
    private void mUnBindService() {
        Intent intent= new Intent(this,MyService.class);
        if (-1!=isconnected)  {
        unbindService(serviceConnection);
        isconnected=-1;
        }
    }

    //开启service
    private void mstartService() {
       Intent intent= new Intent(this,MyService.class);
       startService(intent);
    }
    //停止service
    private void mstopService() {
       Intent intent= new Intent(this,MyService.class);
       stopService(intent);
    }
}
