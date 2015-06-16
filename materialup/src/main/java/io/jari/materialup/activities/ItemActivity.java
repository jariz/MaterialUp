package io.jari.materialup.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import io.jari.materialup.R;
import io.jari.materialup.adapters.DetailAdapter;
import io.jari.materialup.api.Comment;
import io.jari.materialup.api.Item;

/**
 * Created by jari on 12/06/15.
 */
public class ItemActivity extends AppCompatActivity {

    Target lqLoader = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ((ImageView)findViewById(R.id.backdrop)).setImageDrawable(new BitmapDrawable(getResources(), bitmap));

            Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette p) {
                    int color = p.getVibrantColor(R.attr.colorPrimary);
                    recyclerView.setAdapter(new DetailAdapter(new Comment[100], item, ItemActivity.this, color));
                    findViewById(R.id.titlebox).setBackgroundColor(color);

//                    if(swatch != null) {
//                        ((TextView)findViewById(R.id.title)).setTextColor(swatch.getTitleTextColor());
//                        ((TextView)findViewById(R.id.details)).setTextColor(swatch.getTitleTextColor());
//                    }
                }
            });
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
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

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setContentScrimColor(0x00FFFFFF); //hack to disable fullbleed

        ((TextView)findViewById(R.id.title)).setText(item.title);
        ((TextView)findViewById(R.id.details)).setText("by " + item.makerName);
//        collapsingToolbar.setTitle(item.title);

        //set up recycler
        recyclerView = (RecyclerView)findViewById(R.id.scrollableview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        //first set the low quality pic
        Picasso.with(this)
                .load(item.imageUrl)
                .into(lqLoader);
    }

    public static void launch(Activity activity, Item item) {
        Intent intent = new Intent(activity, ItemActivity.class);
        intent.putExtra("item", item);
        activity.startActivity(intent);
    }
}
