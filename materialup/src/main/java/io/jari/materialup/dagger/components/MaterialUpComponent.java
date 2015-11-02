package io.jari.materialup.dagger.components;

import android.content.Context;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Component;
import io.jari.materialup.dagger.modules.AndroidModule;


/**
 * Created by Akash.
 */
@Singleton
@Component(
        modules = {
                AndroidModule.class
        }
)
public interface MaterialUpComponent {
    Moshi moshi();

    Context context();
}
