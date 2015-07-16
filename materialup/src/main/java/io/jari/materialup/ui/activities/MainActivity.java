package io.jari.materialup.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jari.materialup.R;
import io.jari.materialup.factories.ListingFactory;
import io.jari.materialup.ui.fragments.ListingFragment;

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
            int oldPosition = -1;

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                if (position == oldPosition) {
                    return;
                }
                oldPosition = position;

                ListingFragment listingFragment = (ListingFragment) object;
                listingFragment.setActive(materialViewPager);

                for (int i = 0; i < (getCount() - 1); i++) {
                    ListingFragment fragment = (ListingFragment) getItem(i);
                    if (fragment != null && !fragment.equals(listingFragment)) {
                        fragment.setInactive(materialViewPager);
                    }
                }
            }

            @Override
            public Fragment getItem(int position) {
                return ListingFactory.getFragForPosition(position, MainActivity.this);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return ListingFactory.getTitleForPosition(position);
            }
        });

        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
        materialViewPager.getToolbar().setTitle(getString(R.string.app_name));

    }

}
