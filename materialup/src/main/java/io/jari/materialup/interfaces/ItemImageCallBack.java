package io.jari.materialup.interfaces;

import io.jari.materialup.exeptions.ItemImageException;

/**
 * Created by rsicarelli on 7/16/15.
 */
public interface ItemImageCallBack {

    void onItemImageSuccess(String imageUrl);
    void onItemImageError(ItemImageException error);
}
