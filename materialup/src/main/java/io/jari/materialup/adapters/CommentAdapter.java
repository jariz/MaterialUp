package io.jari.materialup.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import io.jari.materialup.R;
import io.jari.materialup.models.Comment;
import io.jari.materialup.models.Item;

/**
 * Created by jari on 14/06/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    public static final int TYPE_DETAILS = 100;

    private ArrayList<Comment> dataSet;
    private Activity context;
    private Item item;
    private int color;

    public CommentAdapter(Comment[] dataSet, Item details, Activity context) {
        this.dataSet = new ArrayList<Comment>(Arrays.asList(dataSet));
        this.context = context;
        this.item = details;
    }

    public void removeAll() {
        for (int i = dataSet.size()-1; i >= 0; i--) {
            remove(dataSet.get(i));
        }
    }

    public void add(Comment comment) {
        dataSet.add(comment);
        notifyItemInserted((dataSet.size() - 1) + 1);
    }

    public void addItems(Comment[] comments) {
        for(Comment comment: comments) {
            add(comment);
        }
    }

    public void remove(Comment comment) {
        int position = dataSet.indexOf(comment);
        dataSet.remove(position);
        notifyItemRemoved(position + 1);
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resource;
        switch(viewType) {
            case TYPE_DETAILS:
                resource = R.layout.details;
                break;
            default:
                resource = R.layout.comment;
                break;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);

        if(viewType == TYPE_DETAILS) view.setBackgroundColor(color);

        return new ViewHolder(view, viewType, context);
    }

    public void setColor(int color) {
        this.color = color;
        this.notifyItemChanged(0);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position > 0)
            holder.update(dataSet.get(position - 1));
        else {
            holder.update(item);
            holder.setColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        switch(position) {
            case 0:
                return TYPE_DETAILS;
            default:
                return super.getItemViewType(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public Activity context;
        public Item item;
        public int viewType;

        public ViewHolder(View v, int ViewType, final Activity context) {
            super(v);
            view = v;
            this.viewType = ViewType;
            this.context = context;
        }

        public void setColor(int color) {
            view.setBackgroundColor(color);
        }

        public void update(Object object) {
            /*Item item;
            Comment comment;

            switch(viewType) {
                case TYPE_DETAILS:
                    item = (Item)object;

                    ((TextView)view.findViewById(R.id.comments)).setText(item.getComments());
                    ((TextView)view.findViewById(R.id.views)).setText(item.getViews());
                    ((TextView)view.findViewById(R.id.score)).setText(item.getScore());

                    break;
                default:
                    //comment
                    comment = (Comment)object;

                    break;
            }*/
        }
    }
}