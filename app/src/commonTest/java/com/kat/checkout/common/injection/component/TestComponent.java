package com.kat.checkout.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.kat.checkout.common.injection.module.ApplicationTestModule;
import com.kat.checkout.injection.component.AppComponent;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}
