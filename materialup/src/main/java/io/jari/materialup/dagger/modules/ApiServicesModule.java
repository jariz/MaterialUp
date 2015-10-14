package io.jari.materialup.dagger.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.jari.materialup.api.PostsService;
import retrofit.Retrofit;

/**
 * Created by gaara on 1/14/15.
 * Make some impeccable shyte
 */


@Module
public class ApiServicesModule {

    @Provides
    public PostsService provideProfileService(@Named(value = RetrofitModule.NAMED_SCOPE_UNAUTHENTICATED) Retrofit restAdapter) {
        return restAdapter.create(PostsService.class);
    }

}
