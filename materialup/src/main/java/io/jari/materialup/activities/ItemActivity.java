package io.jari.materialup.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import io.jari.materialup.R;
import io.jari.materialup.api.Item;

/**
 * Created by jari on 12/06/15.
 */
public class ItemActivity extends AppCompatActivity {

    Target lqLoader = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ((ImageView)findViewById(R.id.header)).setImageDrawable(new BitmapDrawable(getResources(), bitmap));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);

        toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingBar);

        Item item = (Item)getIntent().getSerializableExtra("item");

        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        toolbarLayout.setTitle(item.title);

        //set up recycler
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        //first set the low quality pic
        Picasso.with(this).setLoggingEnabled(true);
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
