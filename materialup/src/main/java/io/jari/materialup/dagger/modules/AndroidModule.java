package io.jari.materialup.dagger.modules;


import android.content.Context;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.jari.materialup.global.UpApplication;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module
public class AndroidModule {

    private static final String TAG = AndroidModule.class.toString();
    private final UpApplication application;

    public AndroidModule(UpApplication application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder().build();
    }

/*    @Provides
    @Singleton
    public ORMService provideORMService() {
        Log.d(TAG, "Providing ORM");
        return new ORMService(application);
    }

    @Provides
    @Singleton
    public UserAuthenticationHandler provideUserAuthenticationHandler() {
        Log.d(TAG, "Providing authHandler");
        return new UserAuthenticationHandler();
    }

    @Provides
    @Singleton
    public synchronized Tracker provideTracker() {
        Log.d(TAG, "Providing gaTracker");
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(application);
        analytics.setDryRun(BuildConfig.DEBUG);
        Tracker t = analytics.newTracker(R.xml.analytics);
        t.enableAdvertisingIdCollection(true);
        t.enableExceptionReporting(!BuildConfig.DEBUG);
        return t;
    }*/

}

