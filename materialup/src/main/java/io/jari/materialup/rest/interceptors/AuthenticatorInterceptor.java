package io.jari.materialup.rest.interceptors;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import io.jari.materialup.R;
import io.jari.materialup.global.UpApplication;

/**
 * Created by rsicarelli on 9/7/15.
 */
public class AuthenticatorInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request authenticatedRequest = request.newBuilder()
                .addHeader("Authorization", UpApplication.getInstance().getString(R.string.api_token))
                .build();

        return chain.proceed(authenticatedRequest);
    }
}
