package io.jari.materialup.api;

import android.content.Context;

/**
 * Created by Akash on 3/23/15.
 */
public class RetrofitErrorHandler {
    Context application;

    public RetrofitErrorHandler(Context application) {
        this.application = application;
    }

/*    @Override
    public Throwable handleError() {
        int errorResourceString = -1, statusCode = 0;
        if (cause.getResponse() != null)
            statusCode = cause.getResponse().getStatus();
        switch (cause.getKind()) {
            case CONVERSION:
                errorResourceString = R.string.error_data_parsing;
                break;
            case HTTP:
                if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED ||
                        statusCode == HttpURLConnection.HTTP_FORBIDDEN || statusCode == HttpURLConnection.HTTP_MOVED_PERM ||
                        statusCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    errorResourceString = R.string.error_unauthorized;
                    //Handle auth failure event
                } else if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    errorResourceString = R.string.error_404;
                }
                break;
            case NETWORK:
                if (statusCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT)
                    errorResourceString = R.string.error_timeout;
                else
                    errorResourceString = R.string.error_network;
                break;
            case UNEXPECTED:
                errorResourceString = R.string.error_unexpected;
                break;
        }
        if (errorResourceString != -1) {
            return new CustomRetrofitErrorThrowable(application.getString(errorResourceString), cause);
        }
        return cause;
    }*/

}
