package io.jari.materialup.rest.endpoints;

import io.jari.materialup.rest.models.Showcase;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by rsicarelli on 9/7/15.
 */
public interface ShowCasesService {
    @GET("posts")
    Call<Showcase[]> listShowcases();
}
