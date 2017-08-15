package com.zocki.binder;

import android.app.ActivityManager;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.zocki.baselibrary.logger.LogUtils;

import java.util.List;

/**
 * Created by kaisheng3 on 2017/8/15.
 * 5.0以上
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(this,JobWakeUpService.class));
        builder.setPeriodic(500);
        JobInfo jobInfo = builder.build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);

        LogUtils.e( JobWakeUpService.class.getName() + " 启动 " );

        return Service.START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        // 开启定时服务，定时轮训，检查MessageService是否存在

        // kill --> start
        if(!isServiceWork(this,MessageService.class.getName())) {
            startService(new Intent(this, MessageService.class));
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
