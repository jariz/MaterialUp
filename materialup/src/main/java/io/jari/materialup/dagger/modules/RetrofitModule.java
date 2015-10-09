package io.jari.materialup.dagger.modules;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.jari.materialup.BuildConfig;
import retrofit.MoshiConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gaara on 1/13/15.
 * Make some impeccable shyte
 */
@Module
public class RetrofitModule {
    protected static final String NAMED_SCOPE_AUTHENTICATED = "authenticated";
    protected static final String NAMED_SCOPE_UNAUTHENTICATED = "open";
    private static final String TAG = RetrofitModule.class.getSimpleName();


    @Provides
    @Named(value = NAMED_SCOPE_UNAUTHENTICATED)
    public Interceptor provideRequestInterceptorForUnauth() {
        Log.d(TAG, "Providing openRequestInterceptor");
        return chain -> {
            Request original = chain.request();

            // Customize the request
            Request request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("User-Agent", "Android MaterialUpApp " + BuildConfig.VERSION_CODE)
                    .header("Authorization", "Token token=\"wdq8teaHR-DhjUcm248Bjg\"")
                    .method(original.method(), original.body())
                    .build();

            // Customize or return the response
            return chain.proceed(request);
        };
    }

    @Provides
    @Named(value = NAMED_SCOPE_UNAUTHENTICATED)
    public OkHttpClient provideHttpClientUnAuthenticated(Context context, @Named(value = NAMED_SCOPE_UNAUTHENTICATED) Interceptor requestInterceptor) {
        long connectiontimeout = 10, readtimeout = 10;
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(connectiontimeout, TimeUnit.SECONDS);
        httpClient.setReadTimeout(readtimeout, TimeUnit.SECONDS);

        long maxSize = 250 * 1024 * 1024;

        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache;
        cache = new Cache(cacheDirectory, maxSize);
        httpClient.setCache(cache);
        httpClient.interceptors().add(requestInterceptor);

        Log.d(TAG, "Providing HttpClient");
        return httpClient;
    }

    @Provides
    @Named(value = NAMED_SCOPE_UNAUTHENTICATED)
    public Retrofit provideRestAdapterForUnauthRequests(@Named(value = NAMED_SCOPE_UNAUTHENTICATED) OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://www.materialup.com/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(httpClient)
                .build();
    }

    @Provides
    @Named(value = NAMED_SCOPE_AUTHENTICATED)
    public Interceptor provideRequestInterceptorForAuth() {
        Log.d(TAG, "Providing openRequestInterceptor");
        return chain -> {
            Request original = chain.request();

            // Customize the request
            Request request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("OAuth", "<insert oauth value here>")
                    .header("User-Agent", "Android MaterialUpApp " + BuildConfig.VERSION_CODE)
                    .header("Authorization", "Token token=\"wdq8teaHR-DhjUcm248Bjg\"")
                    .method(original.method(), original.body())
                    .build();

            // Customize or return the response
            return chain.proceed(request);
        };
    }

    @Provides
    @Named(value = NAMED_SCOPE_AUTHENTICATED)
    public Retrofit provideRestAdapterForAuthRequests(@Named(value = NAMED_SCOPE_AUTHENTICATED) OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://www.materialup.com/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(httpClient)
                .build();
    }

}