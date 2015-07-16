package io.jari.materialup.factories;

import android.content.Context;
import android.content.res.Resources;

import io.jari.materialup.R;
import io.jari.materialup.enums.PageTypes;
import io.jari.materialup.ui.fragments.ListingFragment;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class ListingFactory {

    public static ListingFragment getFragForPosition(int position, Context context) {
        Resources res = context.getResources();
        switch (position) {
            case PageTypes.TYPE_ALL:
                return ListingFragment.newInstance(context.getString(R.string.all), res.getColor(R.color.primary));
            case PageTypes.TYPE_CONCEPTS:
                return ListingFragment.newInstance(context.getString(R.string.concepts), res.getColor(R.color.red_600));
            case PageTypes.TYPE_LIVE:
                return ListingFragment.newInstance(context.getString(R.string.live), res.getColor(R.color.teal_600));
            case PageTypes.TYPE_RESOURCES:
                return ListingFragment.newInstance(context.getString(R.string.resources), res.getColor(R.color.yellow_600));
            case PageTypes.TYPE_FREEBIES:
                return ListingFragment.newInstance(context.getString(R.string.freebies), res.getColor(R.color.orange_600));
        }
        return null;
    }

    public static String getTitleForPosition(int position) {
        switch (position) {
            case PageTypes.TYPE_ALL:
                return "All";
            case PageTypes.TYPE_CONCEPTS:
                return "Concepts";
            case PageTypes.TYPE_LIVE:
                return "Live";
            case PageTypes.TYPE_RESOURCES:
                return "Resources";
            case PageTypes.TYPE_FREEBIES:
                return "Freebies";
        }
        return "";
    }
}
