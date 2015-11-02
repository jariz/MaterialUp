package io.jari.materialup.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import io.jari.materialup.BR;
import io.jari.materialup.R;
import io.jari.materialup.databinding.ItemCardBinding;
import io.jari.materialup.models.Item;
import io.jari.materialup.ui.viewmodels.ItemViewModel;


/**
 * Created by jari on 07/06/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Item> dataSet = new ArrayList<>();

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 200;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 200;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void removeAll() {
        for (int i = dataSet.size() - 1; i >= 0; i--) {
            remove(dataSet.get(i));
        }
    }

    public void addItems(List<Item> items) {
        dataSet.addAll(items);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void remove(Item item) {
        int position = dataSet.indexOf(item);
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);

        return new ViewHolder(card);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.itemVM, new ItemViewModel(dataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.score)
        TextView score;
        @Bind(R.id.comments)
        TextView comments;
        @Bind(R.id.views)
        TextView views;
        @Bind(R.id.avatar)
        CircleImageView avatar;
        private ItemCardBinding mBinding;

        public ViewHolder(View v) {
            super(v);
            mBinding = DataBindingUtil.bind(v);
//            v.setOnClickListener(v1 -> ItemActivity.launch(context, ));
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }

        /*public void update(Item item) {
            if (item.getImageUrl() != null && !item.getImageUrl().equals("")) {
                final ImageView image = (ImageView) cardView.findViewById(R.id.image);
                image.setVisibility(View.VISIBLE);
                Log.d("listingadapter", "asking glide to load " + item.getImageUrl());

                DrawableRequestBuilder<String> request = Glide.with(context)
                        .load(item.getImageUrl())
                        .centerCrop();

                if (item.getImageUrl().endsWith(".gif"))
                    request.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image);
                else request.into(image);

            } else cardView.findViewById(R.id.image).setVisibility(View.GONE);

            boolean generateLetter = false;
            try {
                if (item.getMakerAvatar() != null && !item.getMakerAvatar().equals("")) {
                    Glide.with(context)
                            .load(item.getMakerAvatar())
                            .into(avatar);
                } else {
                    generateLetter = true;
                }
            } catch (NullPointerException e) {
                //so there's like this weird bug where twitter doesn't pass a content-type.
                //glide doesn't know what to do and crashes.
                generateLetter = true;
            }

            if (generateLetter) {
                Bitmap bitmap = drawableToBitmap(
                        TextDrawable
                                .builder()
                                .buildRect(
                                        item.getMakerName().substring(0, 1).toUpperCase(),
                                        ColorGenerator.MATERIAL.getColor(item.getMakerName())
                                )
                );

                avatar.setImageDrawable(
                        new BitmapDrawable(
                                context.getResources(),
                                bitmap
                        )
                );
            }

        }*/
    }
}
