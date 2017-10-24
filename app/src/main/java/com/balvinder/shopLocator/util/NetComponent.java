package com.balvinder.shopLocator.util;

import com.balvinder.shopLocator.activity.MainActivity;
import com.balvinder.shopLocator.activity.MapsActivity;
import com.balvinder.shopLocator.activity.ShopDetailActivity;
import com.balvinder.shopLocator.modules.AppModule;
import com.balvinder.shopLocator.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity mainActivity);
    void inject(ShopDetailActivity shopDetailActivity);
    void inject(MapsActivity mapsActivity);

}
