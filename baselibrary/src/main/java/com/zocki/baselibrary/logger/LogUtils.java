package com.zocki.baselibrary.logger;

import android.util.Log;

import java.lang.reflect.Array;

import static com.zocki.baselibrary.AppConfig.ADB;

/**
 * Created by kaisheng3 on 2017/7/20.
 */

public class LogUtils {

    /** 日志输出级别V */
    public static final int LEVEL_VERBOSE = 1;
    /** 日志输出级别D */
    public static final int LEVEL_DEBUG = 2;
    /** 日志输出级别I */
    public static final int LEVEL_INFO = 3;
    /** 日志输出级别W */
    public static final int LEVEL_WARN = 4;
    /** 日志输出级别E */
    public static final int LEVEL_ERROR = 5;
    /** 日志输出时的TAG */
    private static String mTag = "LOG_TAG";
    /** 是否允许输出log */
    private static int mDebuggable = LEVEL_ERROR;

    public static final String TAG = " ##ZOCKI_TAG## ";

    private static String ObjectsToString(Object... obj ) {
        if( obj == null ) return " null ";
        StringBuilder builder = new StringBuilder();
        for (Object o : obj ) {
            if( o instanceof Throwable) {
                Throwable t = (Throwable) o;
                builder.append( t.getMessage() );
            }  else {
                if( o != null ) builder.append( o.toString() );
            }
        }
        return builder.toString();
    }

    public static void d(Object msg) {
        if (mDebuggable >= LEVEL_DEBUG && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.d(mTag, element.toString() );
            Log.d(mTag, ObjectsToString(msg));
        }
    }

    public static void d(String tag, Object msg) {
        if (mDebuggable >= LEVEL_DEBUG && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.d(mTag, element.toString() );
            Log.d( tag, ObjectsToString( msg ) ) ;
        }
    }

    public static void i(String msg) {
        if (mDebuggable >= LEVEL_INFO && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.i(mTag, element.toString() );
            Log.i(mTag, msg);
        }
    }

    public static void i(String tag, Object... msg) {
        if (mDebuggable >= LEVEL_INFO && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.i(mTag, element.toString() );
            Log.i(tag, ObjectsToString( msg) );
        }
    }

    public static void w(Object msg) {
        if (mDebuggable >= LEVEL_WARN && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.w(mTag, element.toString() );
            Log.w(mTag, ObjectsToString( msg ) );
        }
    }

    public static void w(String tag, Object... msg) {
        if (mDebuggable >= LEVEL_WARN && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.w(mTag, element.toString() );
            Log.w(tag, ObjectsToString(msg));
        }
    }

    public static void e( Object msg) {
        if (mDebuggable >= LEVEL_ERROR && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.e(mTag, element.toString() );
            String tt = ObjectsToString( msg );
            if( tt.length() > 4000 ) {
                for(int i = 0;i < tt.length();i += 4000){
                    if( i + 4000 < tt.length()) {
                        if( element != null ) Log.e(mTag, element.toString() );
                        Log.e(mTag, "\n" + tt.substring(i, i + 4000));
                    } else {
                        if( element != null ) Log.e(mTag, element.toString() );
                        Log.e(mTag, "\n" + tt.substring(i, tt.length()));
                    }
                }
            } else {
                Log.e(mTag, ObjectsToString( msg) );
            }
        }
    }

    public static void e(String tag, Object... msg) {
        if (mDebuggable >= LEVEL_ERROR && ADB) {
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.e(mTag, element.toString() );
            String tt = ObjectsToString( msg );
            if( tt.length() > 400 ) {
                for(int i = 0;i < tt.length();i += 400){
                    if( i + 400 < tt.length())
                        Log.e( tag + i,tt.substring(i, i + 400));
                    else
                        Log.e( tag + i,tt.substring(i, tt.length()));
                }
            } else {
                Log.e(tag, ObjectsToString( msg) );
            }
        }
    }

    public static void printArray( Object... param ) {
        if ( ADB ) {
            StringBuilder builder = new StringBuilder();
            if (param != null && param.length > 0) {
                for (int i = 0; i < param.length; i++) {
                    if (param[i].getClass().isArray())
                        builder.append(ArrayToString(param[i]));
                    else
                        builder.append(param[i].toString());
                }
            }
            StackTraceElement element = getTargetStackTraceElement();
            if( element != null ) Log.e(mTag, element.toString() );
            Log.e(mTag, builder.toString());
        }
    }

    private static String ArrayToString(Object o ) {
        StringBuilder builder = new StringBuilder();
        if( o.getClass().isArray() ) {
            builder.append( " [ " );
            int len = Array.getLength( o );
            for( int i = 0; i < len; i++ ) {
                builder.append( Array.get(o,i).toString() + ( i+1 == len ? "" : " , " ) );
            }
            builder.append(" ] ") ;
        }
        return builder.toString();
    }

    private static StackTraceElement getTargetStackTraceElement() {
        // stack tx
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for( StackTraceElement a : stackTrace ) {
            boolean isLog = a.getClassName().equals( LogUtils.class.getName() );
            if( shouldTrace && !isLog ) {
                targetStackTrace = a;
                break;
            }
            shouldTrace = isLog;
        }
        return targetStackTrace;
    }
}
