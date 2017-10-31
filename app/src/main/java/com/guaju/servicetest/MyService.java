package com.guaju.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private int  count=0;

    public MyService() {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 while(true){
                 SystemClock.sleep(1000);
                  count++;
                 }
             }
         }).start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        MyStub myStub = new MyStub();
        Log.e(TAG, "onBind: 服务绑定了" );
        return myStub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: 服务创建了" );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: 服务开启了" );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: 解绑了" );
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: 服务终止了");
    }
//   class MyBinder extends Binder{
//
//        public void sayHello(){
//            Log.e(TAG, "sayHello: 万圣节快乐~~~" );
//        }
//        public int getCount(){
//            return count;
//        }
//
//   }
    //因为要做进程间通信，不需要继承binder类
    class MyStub extends IMyAidlInterface.Stub{

    @Override
    public void xiaoming(String xiaohong) throws RemoteException {

        Log.e(TAG, "xiaoming:"+xiaohong+" 你要好好上课" );

    }
}


}
