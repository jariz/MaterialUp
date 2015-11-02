package io.jari.materialup.ui.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import io.jari.materialup.models.Item;

/**
 * Created by Akash.
 */
public class ItemViewModel extends BaseObservable {
    private final Item item;

    public ItemViewModel(Item item) {
        this.item = item;
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl.endsWith(".gif")) {
                //TODO when the gif gets loaded in centercrop mode, its get skewed
                Glide.with(view.getContext())
                        .load(imageUrl)
                        .asGif()
                        .fitCenter()
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(view);
            } else {
                Glide.with(view.getContext())
                        .load(imageUrl)
                        .crossFade()
                        .centerCrop()
                        .into(view);
            }
        }
    }

    public Item getItem() {
        return item;
    }
}
