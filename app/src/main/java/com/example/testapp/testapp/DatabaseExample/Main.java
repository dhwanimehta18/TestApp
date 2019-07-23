package com.example.testapp.testapp.DatabaseExample.api;

import android.app.Application;

import com.library.api.ApiConfig;
import com.library.di.component.AppComponent;
import com.library.di.component.DaggerAppComponent;
import com.library.di.module.AppModule;
import com.library.di.module.NetworkModule;
import com.library.helper.chat.ChatSDK;
import com.library.helper.chat.config.ChatConfig;
import com.library.util.common.Consts;
import com.library.util.common.CrashReportingTree;

import timber.log.Timber;

import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private static Main sInstance;
    private AppComponent mAppComponent;

    public static Main getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //if app is in debug mode, planting simple logging tree
        //otherwise planting tree for logging errors to crash reporting tools
        if (Consts.IS_DEBUGGABLE) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        //injecting dependencies
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        mAppComponent.inject(this);

        ChatConfig chatConfig = new ChatConfig.Builder()
                .baseSocketUrl(ApiConfig.BASE_SOCKET_URL)
                .useLocalDb(false)
                .encryptDatabase(false)
                .timeoutAfter(ApiConfig.Timeouts.SOCKET_CONNECT, TimeUnit.SECONDS)
                .autoReconnect(true)
                .attemptsToReconnect(1)
                .delayBetweenReconnects(ApiConfig.Timeouts.SOCKET_RECONNECT_DELAY, TimeUnit.SECONDS)
                .build();

        ChatSDK.getInstance().init(chatConfig);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
