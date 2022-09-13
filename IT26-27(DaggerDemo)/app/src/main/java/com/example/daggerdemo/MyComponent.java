package com.example.daggerdemo;

import dagger.Component;

@Component(modules = MyModule.class)
public interface MyComponent {
    //將注入至MainActivity
    void inject(MainActivity activity);;
}
