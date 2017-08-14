package com.zocki.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.zocki.UserAidl;

/**
 * Created by kaisheng3 on 2017/8/14.
 */

public class MessageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TestAidl();
    }

    private class TestAidl extends UserAidl.Stub {

        @Override
        public String getUserName() throws RemoteException {
            return "zhengzhaozhao";
        }

        @Override
        public String getUserPwd() throws RemoteException {
            return "123456789";
        }
    }

}
