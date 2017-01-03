package dly.gank2;

import android.app.Application;

import dly.gank2.dagger.AppComponent;
import dly.gank2.dagger.DaggerAppComponent;
import dly.gank2.dagger.module.AppModule;
import dly.gank2.utils.AppUtil;

/**
 * Created by 19229 on 2016/12/14.
 */
public class MyApplication extends Application {

    private static MyApplication mContext;
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.init(this);
        mContext = this;

        initInjector();
    }

    private void initInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }


    public static MyApplication getApplication() {
        return mContext;
    }

}
