package com.zocki.binder;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zocki.ProtectConnection;
import com.zocki.baselibrary.logger.LogUtils;

/**
 * Created by kaisheng3 on 2017/8/14.
 */
public class MessageService extends Service {

    private int MessageServiceID = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            private int count = 0;
            @Override
            public void run() {
                while (true) {
                    Log.e( "MessageService ", "MessageService --- " + count ++ );
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 1.提高线程优先级
        startForeground(MessageServiceID, new Notification());

        // 2.绑定服务
        bindService(new Intent(this,ProtectService.class),mServiceConnection,Service.BIND_IMPORTANT);

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProtectConnection.Stub(){};
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("MessageService 建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            unbindService(mServiceConnection);
            // 断开连接，重新启动
            startService(new Intent(MessageService.this, ProtectService.class));
            // 2.绑定服务
            bindService(new Intent(MessageService.this,ProtectService.class),mServiceConnection,Service.BIND_IMPORTANT);
        }
    };
}
