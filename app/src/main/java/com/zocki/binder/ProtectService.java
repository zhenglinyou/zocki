package com.zocki.binder;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zocki.ProtectConnection;
import com.zocki.baselibrary.logger.LogUtils;

/**
 * Created by kaisheng3 on 2017/8/15.
 * 第二个进程
 */
public class ProtectService extends Service {

    private final int ProtectServiceID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 提高线程优先级
        startForeground(ProtectServiceID, new Notification());

        // 建立连接
        bindService(new Intent(this,MessageService.class),mServiceConnection, Context.BIND_IMPORTANT);

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
            LogUtils.e("ProtectService 建立连接");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(ProtectService.this, MessageService.class));
            // 建立连接
            bindService(new Intent(ProtectService.this,MessageService.class),mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
