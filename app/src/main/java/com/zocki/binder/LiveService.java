package com.zocki.binder;

import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;

import com.zocki.baselibrary.logger.LogUtils;

/**
 * Created by kaisheng3 on 2017/8/16.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class LiveService extends NotificationListenerService{

    public LiveService() {
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        LogUtils.e( "onNotificationPosted" );
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        LogUtils.e( "onNotificationRemoved" );
    }
}
