package io.jari.materialup.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import io.jari.materialup.R;
import io.jari.materialup.adapters.DetailAdapter;
import io.jari.materialup.api.API;
import io.jari.materialup.api.Comment;
import io.jari.materialup.api.Item;
import io.jari.materialup.api.ItemDetails;

/**
 * Created by jari on 12/06/15.
 */
public class ItemActivity extends AppCompatActivity {

    CollapsingToolbarLayout toolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);

        toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

        item = (Item)getIntent().getSerializableExtra("item");
        toolbarLayout.setTitle(item.title);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set up recycler
        recyclerView = (RecyclerView)findViewById(R.id.scrollableview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        final BitmapImageViewTarget header = new BitmapImageViewTarget((ImageView) findViewById(R.id.backdrop)) {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                super.onResourceReady(bitmap, glideAnimation);
                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette p) {
                        int color = p.getVibrantColor(R.attr.colorPrimary);
                        recyclerView.setAdapter(new DetailAdapter(new Comment[100], item, ItemActivity.this, color));

                        toolbarLayout.setContentScrimColor(color);
                        toolbarLayout.setStatusBarScrimColor(p.getDarkVibrantColor(getResources().getColor(R.color.primary_dark)));
                    }
                });
            }
        };

        //first set the low quality pic
        Glide.with(this)
                .load(item.imageUrl)
                .asBitmap()
//                .transform(new BlurTransformation(this, Glide.get(this).getBitmapPool(), 10))
                .into(header);
//                .into((ImageView) findViewById(R.id.backdrop));

        //now load hq pic
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ItemDetails itemDetails = API.getItemDetails(item.id);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissLoader();
                            Glide.with(getApplicationContext())
                                    .load(itemDetails.imageUrl)
                                    .crossFade()
                                    .into((ImageView) findViewById(R.id.backdrop));
//                                    .into(header);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void launch(Activity activity, Item item) {
        Intent intent = new Intent(activity, ItemActivity.class);
        intent.putExtra("item", item);
        activity.startActivity(intent);
    }

    public void dismissLoader() {
        final View prog = findViewById(R.id.progressBar);
        prog.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                prog.setVisibility(View.GONE);
            }
        });
    }
}
