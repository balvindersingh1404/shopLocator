package com.balvinder.shopLocator.util;

import android.app.Application;

import com.balvinder.shopLocator.modules.AppModule;
import com.balvinder.shopLocator.modules.NetModule;

public class MyApplication extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://maps.googleapis.com/maps/api/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
