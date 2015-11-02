package io.jari.materialup.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jari.materialup.R;
import io.jari.materialup.adapters.CommentAdapter;
import io.jari.materialup.models.Comment;
import io.jari.materialup.models.Item;
import io.jari.materialup.utils.ParseUtils;

/**
 * Created by jari on 12/06/15.
 */
public class ItemActivity extends BaseActivity {

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private Item item;
    private CommentAdapter commentAdapter;

    public static void launch(Activity activity, Item item) {
        Intent intent = new Intent(activity, ItemActivity.class);
        intent.putExtra("item", ParseUtils.convertModelToJson(item));
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);

        ButterKnife.bind(this);

        if (getIntent().hasExtra("item")) {
            try {
                item = ParseUtils.convertJsonToModel(getIntent().getStringExtra("item"), Item.class);
            } catch (IOException e) {
                finish();
            }
        } else
            finish();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(item.getName());

        //set up recycler
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        commentAdapter = new CommentAdapter(new Comment[0], item, ItemActivity.this);
        recyclerView.setAdapter(commentAdapter);

        final BitmapImageViewTarget header = new BitmapImageViewTarget((ImageView) findViewById(R.id.backdrop)) {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                super.onResourceReady(bitmap, glideAnimation);
                Palette.generateAsync(bitmap, p -> {

                    int color = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        color = p.getVibrantColor(getColor(R.color.primary));
                    } else {
                        color = p.getVibrantColor(getResources().getColor(R.color.primary, getTheme()));
                    }
                    commentAdapter.setColor(color);

                    toolbarLayout.setContentScrimColor(color);
                    toolbarLayout.setStatusBarScrimColor(p.getDarkVibrantColor(getResources().getColor(R.color.dark)));
                });
            }
        };

        //first set the low quality pic
//        Glide.with(this)
//                .load(item.imageUrl)
//                .asBitmap()
//                .transform(new BlurTransformation(this, Glide.get(this).getBitmapPool(), 10))
//                .into(header);
//                .into((ImageView) findViewById(R.id.backdrop));

        //now load hq pic
/*        new Thread(() -> {
            try {
                final ItemDetails itemDetails = API.getItemDetails(item.getId());

                runOnUiThread(() -> {
                    dismissLoader();
                    Glide.with(getApplicationContext())
                            .load(itemDetails.imageUrl)
//                                    .crossFade()
//                                    .into((ImageView) findViewById(R.id.backdrop));
                            .asBitmap()
                            .into(header);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();*/

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Comment[] comments;
//                try {
//                     comments = API.getComments(item.id);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                    return;
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
//        }).start();
    }

    public void dismissLoader() {
        progressBar.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
