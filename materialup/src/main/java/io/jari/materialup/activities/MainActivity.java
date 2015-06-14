package io.jari.materialup.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import io.jari.materialup.R;
import io.jari.materialup.fragments.ListingFragment;

/**
 * Created by jari on 07/06/15.
 */
public class MainActivity extends AppCompatActivity {
    private MaterialViewPager materialViewPager;
    private Drawer materialDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activty_main);

        materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

//        materialViewPager.getToolbar().setTitle("MaterialUp");

        materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            int oldPosition = -1;

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                if(position == oldPosition)
                    return;
                oldPosition = position;

                ListingFragment listingFragment = (ListingFragment)object;
                listingFragment.setActive(materialViewPager);

                for (int i = 0; i < (getCount() - 1); i++) {
                    ListingFragment fragment = (ListingFragment)getItem(i);
                    if(!fragment.equals(listingFragment)) fragment.setInactive(materialViewPager);
                }
            }

            @Override
            public Fragment getItem(int position) {

                switch(position) {
                    case 0:
                        return ListingFragment.newInstance("posts/c/all", false, getResources().getColor(R.color.primary));
                    case 1:
                        return ListingFragment.newInstance("posts/c/all", true, getResources().getColor(R.color.red_600));
                }
                return null;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Latest";
                    case 1:
                        return "Popular";
                }
                return "";
            }
        });

        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());

//        setSupportActionBar(materialViewPager.getToolbar());
//        materialViewPager.getViewPager().setCurrentItem(0);

        buildDrawer();

    }

    protected void buildDrawer() {
        materialDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(materialViewPager.getToolbar())
                .withActionBarDrawerToggle(true)
//                .addDrawerItems(
//                        new PrimaryDrawerItem().withName("All"),
//                        new PrimaryDrawerItem().withName("Concepts"),
//                        new PrimaryDrawerItem().withName("Live"),
//                        new PrimaryDrawerItem().withName("Resources"),
//                        new PrimaryDrawerItem().withName("Freebies")
//                )
//                .withTranslucentStatusBar(false)
//                .withTranslucentStatusBarProgrammatically(false)
//                .withTranslucentStatusBarShadow(false)
//                .withOnDrawerItemSelectedListener(new Drawer.OnDrawerItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
//                        Log.w("BRUH", "TRIGGERS BRUH");
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                })
                .build();

    }
}
