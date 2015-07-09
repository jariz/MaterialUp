package io.jari.materialup.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import io.jari.materialup.R;
import io.jari.materialup.adapters.ListingAdapter;
import io.jari.materialup.api.API;
import io.jari.materialup.api.Item;
import io.jari.materialup.api.ItemDetails;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

import java.util.Random;

/**
 * Created by jari on 01/06/15.
 */
public class ListingFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListingAdapter listingAdapter;
    private View view;
    private Item[] items;
    private String path;
    private int color;
    private boolean popular;
    private boolean unitialized = true;

    public static ListingFragment newInstance(String path, boolean popular, int color) {
        ListingFragment listingFragment = new ListingFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("path", path);
        bundle.putBoolean("popular", popular);
        bundle.putInt("color", color);
        listingFragment.setArguments(bundle);
        return listingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.path = arguments.getString("path");
            this.popular = arguments.getBoolean("popular");
            this.color = arguments.getInt("color");
        }

        view = inflater.inflate(R.layout.listing, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        //set up recycler
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    Drawable headerDrawable;
    ItemDetails headerDetails;
    MaterialViewPager pager;
    boolean active;

    public void setInactive(final MaterialViewPager pager) {
        active = false;
    }

    public void setActive(final MaterialViewPager pager) {
        active = true;
        this.pager = pager;

        //fallback to primary color if color passed equals to 0
        if (color == 0) color = getResources().getColor(R.color.primary);

        pager.setColor(color, 400);
        Log.i("LF.setActive", "Color set to " + color);
//        pager.setImageDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)), 400);

        //check if we've been active before. if not, start loading data.
        Log.d("LF.setActive", "unitialized = " + unitialized);
        if (unitialized) {
            unitialized = false;

            //load initial data (without refresh)
            loadData(false);
        }

        if (items == null) {
            Log.i("LF.setActive", "No items set, not downloading header and waiting for loadData to call me.");
            return;
        }

        if (headerDrawable != null) {
            Log.i("LF.setActive", "Setting cached header");
            pager.setImageDrawable(headerDrawable, 400);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //get random item
                    Item item = items[new Random().nextInt(items.length)];

                    if(headerDetails == null)
                        headerDetails = API.getItemDetails(item.id);

                    if(headerDetails.imageUrl == null) {
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

    public void loadData(final boolean refresh) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    items = API.getListing(path + (popular ? "?sort=popular" : ""), getActivity(), 1);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //set up recycler content
                            listingAdapter = new ListingAdapter(new Item[0], getActivity());
                            RecyclerViewMaterialAdapter recyclerViewMaterialAdapter = new RecyclerViewMaterialAdapter(listingAdapter);
                            recyclerView.setAdapter(recyclerViewMaterialAdapter);
                            MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);

                            if (active && !refresh) {
                                //retrigger setActive now that the list is loaded
                                ListingFragment.this.setActive((MaterialViewPager) getActivity().findViewById(R.id.material_view_pager));
                            }

                            if (refresh) {
                                swipeRefreshLayout.setRefreshing(false);
                                listingAdapter.removeAll();
                            }

                            listingAdapter.addItems(items);
                        }
                    });

                } catch (Exception e) {
//                    errorSnack(e);
                    e.printStackTrace();
                } finally {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (refresh)
                                    swipeRefreshLayout.setRefreshing(false);
                                else dismissLoader();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void dismissLoader() {
        final View prog = view.findViewById(R.id.progressBar);
        prog.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                prog.setVisibility(View.GONE);
            }
        });
    }
}
