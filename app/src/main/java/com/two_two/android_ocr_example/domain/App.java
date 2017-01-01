package com.two_two.android_ocr_example.domain;

import android.app.Application;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class App extends Application {
    private static App sInstance;

    public App() {
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }
}
