package io.jari.materialup.connection;

import java.util.List;

import io.jari.materialup.api.PostsService;
import io.jari.materialup.global.UpApplication;
import io.jari.materialup.interfaces.ItemCallback;
import io.jari.materialup.models.Item;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class UpRequests {

/*    public static void getItemDetails(String path, String sort,int pageNum, final ItemCallback callback) {
        //TODO: find a way to integrate pageNum parameter into the query parameters of the request
        // whenever pagination is supported
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("http").authority("materialup.com")
                .appendPath("posts")
                .appendPath("c")
                .appendPath(path)
                .appendQueryParameter("page",String.valueOf(pageNum));

        if (!StringUtils.isEmpty(sort)) {
            uriBuilder.appendQueryParameter("sort", sort);
        }
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, uriBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ItemResponse itemsResponse = ParseUtils.parseItems(response);
                        callback.onItemSuccess(itemsResponse);
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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onItemImageError(new ItemImageException(UpApplication.getInstance().getString(R.string.error_connection)));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");

                return params;
            }
        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest);

    }*/

    public static void getPosts(int daysBack, ItemCallback callback) {
        //TODO instead of passing callback as a variable, return a function that can be invoked on the callback
        PostsService service = UpApplication.getInstance().getAPIComponent().getPostsService();
        service.getAllPosts(daysBack).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Response<List<Item>> response, Retrofit retrofit) {
                if (response.isSuccess())
                    callback.onItemSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
