package io.jari.materialup.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jari.materialup.R;
import io.jari.materialup.adapters.ListingAdapter;
import io.jari.materialup.api.API;
import io.jari.materialup.models.Item;
import io.jari.materialup.models.ItemDetails;
import io.jari.materialup.connection.UpRequests;
import io.jari.materialup.exeptions.ItemException;
import io.jari.materialup.exeptions.ItemImageException;
import io.jari.materialup.interfaces.ItemCallback;
import io.jari.materialup.interfaces.ItemImageCallBack;
import io.jari.materialup.responses.ItemResponse;
import io.jari.materialup.utils.StringUtils;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * Created by jari on 01/06/15.
 */
public class ListingFragment extends BaseFragment implements ItemCallback, ItemImageCallBack {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private List<Item> items;
    private String path;
    private int color;
    private boolean uninitialized = true;

    private Drawable headerDrawable;
    private boolean active;
    private String mQueryParameter;
    private MaterialViewPager mMaterialViewPager;
    private ItemDetails headerDetails;

    public static ListingFragment newInstance(String path, boolean popular, int color) {
        ListingFragment listingFragment = new ListingFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("path", path);
        bundle.putBoolean("popular", popular);
        bundle.putInt("color", color);
        listingFragment.setArguments(bundle);
        return listingFragment;
    }

    public static ListingFragment newInstance(String path, int color) {
        ListingFragment listingFragment = new ListingFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("path", path);
        bundle.putInt("color", color);
        listingFragment.setArguments(bundle);
        return listingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            path = arguments.getString("path");
            if (arguments.getBoolean("popular")) {
                mQueryParameter = "popular";
            }
            color = arguments.getInt("color");
        }

        View view = inflater.inflate(R.layout.listing, container, false);

        ButterKnife.bind(this, view);

        //set up recycler
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    public void setInactive(final MaterialViewPager pager) {
        active = false;
    }

    public void setActive(final MaterialViewPager pager) {
        this.mMaterialViewPager = pager;
        active = true;

        //fallback to primary color if color passed equals to 0
        if (color == 0) {
            color = getResources().getColor(R.color.primary);
        }

        pager.setColor(color, 400);

        if (uninitialized) {
            uninitialized = false;

            if (!StringUtils.isEmpty(path)) {
                UpRequests.getItemDetails(path, mQueryParameter, this);
            }
        }

        if (headerDrawable != null) {
            pager.setImageDrawable(headerDrawable, 400);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //get random item
                    Item item = items.get(new Random().nextInt(items.size()));

                    if (headerDetails == null)
                        headerDetails = API.getItemDetails(item.getId());

                    if (headerDetails.imageUrl == null) {
                        //no suitable image found, remove the image from drawer, leaving just the color
                        pager.setImageDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)), 400);
                    }

                    //is this still the

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int color = ListingFragment.this.color;
                            color = (120 << 24) | (color & 0x00ffffff);

                            Log.i("LF.setActive", "No cached header, Downloading new header " + headerDetails.imageUrl);

                            Glide.with(getActivity())
                                    .load(headerDetails.imageUrl)
                                    .asBitmap()
                                    .transform(new ColorFilterTransformation(Glide.get(getActivity()).getBitmapPool(), color))
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                            Log.i("LF.setActive", "Glide is done. Setting drawable");
                                            headerDrawable = new BitmapDrawable(getResources(), bitmap);
                                            pager.setImageDrawable(headerDrawable, 400);
                                        }
                                    });

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

        if (active) {
            setActive((MaterialViewPager) getActivity().findViewById(R.id.material_view_pager));
        }

        dismissLoader();
    }

    @Override
    public void onItemError(ItemException error) {
        Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemImageSuccess(String imageUrl) {

    }

    @Override
    public void onItemImageError(ItemImageException error) {

    }
}
