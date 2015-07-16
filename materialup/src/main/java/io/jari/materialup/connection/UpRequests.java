package io.jari.materialup.connection;

import android.net.Uri;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import io.jari.materialup.R;
import io.jari.materialup.exeptions.ItemException;
import io.jari.materialup.exeptions.ItemImageException;
import io.jari.materialup.global.UpApplication;
import io.jari.materialup.interfaces.ItemCallback;
import io.jari.materialup.interfaces.ItemImageCallBack;
import io.jari.materialup.responses.ItemResponse;
import io.jari.materialup.utils.ParseUtils;
import io.jari.materialup.utils.StringUtils;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class UpRequests {

    public static void getItemDetails(String path, String sort, final ItemCallback callback) {

        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("http").authority("materialup.com")
                .appendPath("posts")
                .appendPath("c")
                .appendPath(path);

        if (!StringUtils.isEmpty(sort)) {
            uriBuilder.appendQueryParameter("sort", sort);
        }
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, uriBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ItemResponse itensResponse = ParseUtils.parseItens(response);
                        callback.onItemSuccess(itensResponse);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onItemError(new ItemException(UpApplication.getInstance().getString(R.string.error_connection)));
            }
        });

        VolleySingleton.getInstance().addToRequestQueue(stringRequest);

    }

    public static void getItemImage(String id, final ItemImageCallBack callback) {

        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("http").authority("materialup.com")
                .appendPath("posts")
                .appendPath(id);

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, uriBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String imageUrl = ParseUtils.parseImageUrl(response);
                            callback.onItemImageSuccess(imageUrl);
                        } catch (JSONException e) {
                            callback.onItemImageError(new ItemImageException(UpApplication.getInstance().getString(R.string.error_connection)));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onItemImageError(new ItemImageException(UpApplication.getInstance().getString(R.string.error_connection)));
            }
        });

        VolleySingleton.getInstance().addToRequestQueue(stringRequest);

    }

}
