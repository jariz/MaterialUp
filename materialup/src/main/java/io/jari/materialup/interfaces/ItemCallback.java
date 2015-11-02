package io.jari.materialup.interfaces;

import java.util.List;

import io.jari.materialup.exeptions.ItemException;
import io.jari.materialup.models.Item;

/**
 * Created by rsicarelli on 7/15/15.
 */
public interface ItemCallback {

    void onItemSuccess(List<Item> response);
    void onItemError(ItemException error);
}
