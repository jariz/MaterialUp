package io.jari.materialup.interfaces;

import io.jari.materialup.exeptions.ItemException;
import io.jari.materialup.responses.ItemResponse;

/**
 * Created by rsicarelli on 7/15/15.
 */
public interface ItemCallback {

    void onItemSuccess(ItemResponse response);
    void onItemError(ItemException error);
}
