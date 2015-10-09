package io.jari.materialup.global;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;
import io.jari.materialup.BuildConfig;


public class UpApplication extends Application {

    private static UpApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this,
                new Crashlytics.Builder().core(
                        new CrashlyticsCore.Builder().disabled(
                                !BuildConfig.USE_CRASHLYTICS)
                                .build())
                        .build()
        );

        Fabric.with(this, new Crashlytics());
        mInstance = this;
    }

    /**
     * Singleton pattern
     *
     * @return Current application instance
     */
    public static synchronized UpApplication getInstance() {
        return mInstance;
    }


}
