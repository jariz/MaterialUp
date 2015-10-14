package io.jari.materialup.factories;

import android.content.Context;

import com.github.florent37.materialviewpager.header.HeaderDesign;

import io.jari.materialup.R;
import io.jari.materialup.enums.PageTypes;
import io.jari.materialup.ui.fragments.CategoryFragment;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class CategoryFactory {

    public static CategoryFragment getFragForPosition(int position, Context context) {
        switch (position) {
            case PageTypes.TYPE_ALL:
                return CategoryFragment.newInstance(context.getString(R.string.all));
            case PageTypes.TYPE_CONCEPTS:
                return CategoryFragment.newInstance(context.getString(R.string.concepts));
            case PageTypes.TYPE_LIVE:
                return CategoryFragment.newInstance(context.getString(R.string.live));
            case PageTypes.TYPE_RESOURCES:
                return CategoryFragment.newInstance(context.getString(R.string.resources));
            case PageTypes.TYPE_FREEBIES:
                return CategoryFragment.newInstance(context.getString(R.string.freebies));
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

    public static HeaderDesign getHeaderDesign(int position) {
        switch (position) {

            case PageTypes.TYPE_ALL:
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.primary,
                        null);
            case PageTypes.TYPE_CONCEPTS:
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.red_600,
                        null);
            case PageTypes.TYPE_LIVE:
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.teal_600,
                        null);
            case PageTypes.TYPE_RESOURCES:
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.yellow_600,
                        null);
            case PageTypes.TYPE_FREEBIES:
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.orange_600,
                        null);
        }

        return null;
    }
}
