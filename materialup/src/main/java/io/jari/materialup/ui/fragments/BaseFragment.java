package io.jari.materialup.ui.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import io.jari.materialup.ui.activities.BaseActivity;

public class BaseFragment extends Fragment {

    public View mRootView;

    public BaseActivity mActivity;

    public BaseFragment() {
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (BaseActivity) context;
        super.onAttach(context);
    }

}

