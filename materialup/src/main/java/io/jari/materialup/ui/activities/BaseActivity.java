package io.jari.materialup.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.jari.materialup.R;

/**
 * Created by sicarelli on 6/18/15.
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar mActionBarToolbar;

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        getToolbar();

    }

    public Toolbar getToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        return mActionBarToolbar;
    }

    public void setMenuIcon() {
        if (mActionBarToolbar != null) {
            mActionBarToolbar.setNavigationIcon(R.drawable.ic_menu);
        }
    }

    public void setBackIcon() {
        if (mActionBarToolbar != null) {
            mActionBarToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
            mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

}
