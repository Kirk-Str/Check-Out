package com.kat.checkout.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.kat.checkout.data.DataManager;
import com.kat.checkout.injection.ApplicationContext;
import com.kat.checkout.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();
}
