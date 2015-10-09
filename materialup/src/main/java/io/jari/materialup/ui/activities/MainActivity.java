package io.jari.materialup.ui.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.florent37.materialviewpager.MaterialViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jari.materialup.R;
import io.jari.materialup.factories.CategoryFactory;

/**
 * Created by jari on 07/06/15.
 */
public class MainActivity extends NavDrawerActivity {

    @Bind(R.id.material_view_pager)
    MaterialViewPager materialViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activty_main);

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
        });

        materialViewPager.setMaterialViewPagerListener(CategoryFactory::getHeaderDesign);

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
