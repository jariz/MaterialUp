package io.jari.materialup.global;

import android.app.Application;


public class UpApplication extends Application {

    private static UpApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
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
