package com.kat.checkout.injection.component;

import dagger.Subcomponent;
import com.kat.checkout.features.detail.DetailActivity;
import com.kat.checkout.features.main.MainActivity;
import com.kat.checkout.injection.PerActivity;
import com.kat.checkout.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);
}
