package dly.gank2.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by 19229 on 2016/12/14.
 */
public class AppUtil {
    private static Context mAppContext;

    /**
     * The method should be call when app create.
     */
    public static void init(@NonNull Context context) {
        mAppContext = context;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static String getCacheDir() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            File cacheFile = mAppContext.getExternalCacheDir();
            if(null != cacheFile) {
                return cacheFile.getPath();
            }
        }
        return mAppContext.getCacheDir().getPath();
    }
}
