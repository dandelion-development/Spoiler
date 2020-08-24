package com.dandelion.udemy.spoiler.common;
// [Imports]
import android.app.Application;
import android.content.Context;

//[Global Application]: gets application context in order to get access from overall application
/**
 * Global Application > common application context resource
 * - In order to preserve resources a common application context access class has made
 * - This way memory resources are preserved by avoiding instance application context multiple times
 * - Is a Singleton Pattern practice that allows create one instance of application context and keep memory
 * - In order to user this pattern is needed specify this behaviour in application Manifest
 * - Manifest integration results by adding [android:name=".common.GlobalApp"] parameter in [application] tag
 */
public class GlobalApp extends Application {
    // (Vars)
    private static GlobalApp instance;

    // [Methods]: public methods
    public static GlobalApp getInstance() {return instance;}
    public static Context getContext() {return instance;}

    // [Methods]: overridable methods
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
