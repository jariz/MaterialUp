package io.jari.materialup.api;

import java.util.List;

import io.jari.materialup.models.Item;
import io.jari.materialup.models.ItemDetails;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Akash.
 */
public interface PostsService {
    @GET("v1/posts")
    Call<List<Item>> getAllPosts(@Query("days_ago") int daysBefore);

    @GET("v1/posts/{post_id}")
    Call<ItemDetails> getPostDetails(@Path("post_id") int postId);

}
