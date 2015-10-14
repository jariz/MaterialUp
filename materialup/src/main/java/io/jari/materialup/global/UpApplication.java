package io.jari.materialup.global;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.jari.materialup.dagger.components.ApiServicesComponent;
import io.jari.materialup.dagger.components.DaggerApiServicesComponent;
import io.jari.materialup.dagger.components.DaggerMaterialUpComponent;
import io.jari.materialup.dagger.components.MaterialUpComponent;
import io.jari.materialup.dagger.modules.AndroidModule;


public class UpApplication extends Application {

    private static UpApplication mInstance;
    private MaterialUpComponent mComponent;
    private ApiServicesComponent mApiComponent;

    /**
     * Singleton pattern
     *
     * @return Current application instance
     */
    public static synchronized UpApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        setupDaggerGraph();
    }

    private void setupDaggerGraph() {
        mComponent = DaggerMaterialUpComponent.builder().androidModule(new AndroidModule(this)).build();
        mApiComponent = DaggerApiServicesComponent.builder().materialUpComponent(mComponent).build();
    }

    public MaterialUpComponent getDaggerComponent() {
        return mComponent;
    }

    public ApiServicesComponent getAPIComponent() {
        return mApiComponent;
    }

}
