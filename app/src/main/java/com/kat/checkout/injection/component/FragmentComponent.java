package com.kat.checkout.injection.component;

import dagger.Subcomponent;
import com.kat.checkout.injection.PerFragment;
import com.kat.checkout.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}
