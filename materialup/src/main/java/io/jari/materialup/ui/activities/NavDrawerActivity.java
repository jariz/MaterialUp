package io.jari.materialup.ui.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import io.jari.materialup.R;

/**
 * Created by rsicarelli on 6/18/15.
 */
public class NavDrawerActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavView != null) {
            setupDrawerContent();
        }

        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void setupDrawerContent() {
        //TODO something
    }
}
