package com.gamechange.assignment.dagger.component;

import com.gamechange.assignment.dagger.module.SharedPrefModule;
import com.gamechange.assignment.ui.home.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPrefModule.class})
public interface MyComponent {
    void inject(MainActivity activity);
}