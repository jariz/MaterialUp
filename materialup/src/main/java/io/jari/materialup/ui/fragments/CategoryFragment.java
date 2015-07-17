package io.jari.materialup.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jari.materialup.R;
import io.jari.materialup.adapters.ListingAdapter;
import io.jari.materialup.connection.UpRequests;
import io.jari.materialup.exeptions.ItemException;
import io.jari.materialup.exeptions.ItemImageException;
import io.jari.materialup.interfaces.ItemCallback;
import io.jari.materialup.interfaces.ItemImageCallBack;
import io.jari.materialup.models.Item;
import io.jari.materialup.responses.ItemResponse;
import io.jari.materialup.ui.activities.MainActivity;
import io.jari.materialup.utils.StringUtils;

/**
 * Created by jari on 01/06/15.
 */
public class CategoryFragment extends BaseFragment implements ItemCallback, ItemImageCallBack {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private List<Item> items;
    private String path;
    private String mQueryParameter;

    public static CategoryFragment newInstance(String path) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("path", path);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.listing, container, false);

        init();

        return mRootView;
    }

    private void init() {
        if (getArguments() != null) {
            path = getArguments().getString("path");
            if (getArguments().getBoolean("popular")) {
                mQueryParameter = "popular";
            }
        }

        if (!StringUtils.isEmpty(path)) {
            UpRequests.getItemDetails(path, mQueryParameter, this);
        }

        ButterKnife.bind(this, mRootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public void dismissLoader() {
        progressBar.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemSuccess(ItemResponse response) {
        items = response.getItemList();
        ListingAdapter listingAdapter = new ListingAdapter(items, getActivity());
        RecyclerViewMaterialAdapter recyclerViewMaterialAdapter = new RecyclerViewMaterialAdapter(listingAdapter);
        recyclerView.setAdapter(recyclerViewMaterialAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);

        getRandomCover();

        dismissLoader();
    }

    private void getRandomCover() {
        Item item = items.get(new Random().nextInt(items.size()));
        UpRequests.getItemImage(item.getId(), this);
    }

    @Override
    public void onItemError(ItemException error) {
        Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemImageSuccess(String imageUrl) {
        ((MainActivity) mActivity).updatePagerDrawable(imageUrl);
    }

    @Override
    public void onItemImageError(ItemImageException error) {
        Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
