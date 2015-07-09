package io.jari.materialup.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.github.florent37.materialviewpager.MaterialViewPager;
import io.jari.materialup.R;
import io.jari.materialup.ui.fragments.ListingFragment;

/**
 * Created by jari on 07/06/15.
 */
public class MainActivity extends NavDrawerActivity {

    private MaterialViewPager materialViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activty_main);

        setMenuIcon();

        materialViewPager = (MaterialViewPager) findViewById(R.id.material_view_pager);

        materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            int oldPosition = -1;

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                if (position == oldPosition)
                    return;
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

                switch (position) {
                    case 0:
                        return ListingFragment.newInstance("posts/c/all", false, getResources().getColor(R.color.primary));
                    case 1:
                        return ListingFragment.newInstance("posts/c/concepts", false, getResources().getColor(R.color.red_600));
                    case 2:
                        return ListingFragment.newInstance("posts/c/live", false, getResources().getColor(R.color.teal_600));
                    case 3:
                        return ListingFragment.newInstance("posts/c/resources", false, getResources().getColor(R.color.yellow_600));
                    case 4:
                        return ListingFragment.newInstance("posts/c/freebies", false, getResources().getColor(R.color.orange_600));
                }
                return null;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "All";
                    case 1:
                        return "Concepts";
                    case 2:
                        return "Live";
                    case 3:
                        return "Resources";
                    case 4:
                        return "Freebies";
                }
                return "";
            }
        });

        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
        materialViewPager.getToolbar().setTitle("MaterialUp");

    }
}
