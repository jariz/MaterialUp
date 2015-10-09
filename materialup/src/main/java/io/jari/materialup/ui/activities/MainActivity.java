package io.jari.materialup.ui.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jari.materialup.R;
import io.jari.materialup.factories.CategoryFactory;
import io.jari.materialup.rest.endpoints.ShowCasesService;
import io.jari.materialup.rest.interceptors.AuthenticatorInterceptor;
import io.jari.materialup.rest.models.Showcase;
import io.jari.materialup.rest.utils.ApiCons;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jari on 07/06/15.
 */
public class MainActivity extends NavDrawerActivity {

    @Bind(R.id.material_view_pager)
    MaterialViewPager materialViewPager;

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activty_main);

        client.interceptors().add(new AuthenticatorInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(ApiCons.API_URL)
                .build();

        ShowCasesService service = retrofit.create(ShowCasesService.class);

        Call<Showcase[]> listShowcases = service.listShowcases();

        listShowcases.enqueue(new Callback<Showcase[]>() {
            @Override
            public void onResponse(Response<Showcase[]> response) {
                Toast.makeText(MainActivity.this,
                        "success",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("up", t.getCause() + " " + t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ButterKnife.bind(this);

        setMenuIcon();

        materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                                                        @Override
                                                        public int getCount() {
                                                            return 5;
                                                        }

                                                        @Override
                                                        public Fragment getItem(int position) {
                                                            return CategoryFactory.getFragForPosition(position, MainActivity.this);
                                                        }

                                                        @Override
                                                        public CharSequence getPageTitle(int position) {
                                                            return CategoryFactory.getTitleForPosition(position);
                                                        }
                                                    }
        );

        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
                                                           @Override
                                                           public HeaderDesign getHeaderDesign(int position) {
                                                               return CategoryFactory.getHeaderDesign(position);
                                                           }
                                                       }
        );

        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
        materialViewPager.getToolbar().setTitle(getString(R.string.app_name));

    }

    public void updatePagerDrawable(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        materialViewPager.setImageDrawable(new BitmapDrawable(getResources(), bitmap), 400);
                    }
                });
    }
}
