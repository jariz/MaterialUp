package io.jari.materialup.global;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class UpApplication extends Application {

    private static UpApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
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
